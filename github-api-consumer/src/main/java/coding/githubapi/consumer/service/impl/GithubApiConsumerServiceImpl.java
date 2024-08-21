package coding.githubapi.consumer.service.impl;

import coding.githubapi.consumer.dto.BaseDTO;
import coding.githubapi.consumer.model.GitRestAPIProperties;
import coding.githubapi.consumer.service.GithubApiConsumerService;
import coding.githubapi.consumer.util.GitRestAPIHelper;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class GithubApiConsumerServiceImpl implements GithubApiConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GitRestAPIHelper helper;

    @Autowired
    private Cache<Object, Object> caffeineCache;

    @Override
    public Object process(GitRestAPIProperties props) {
        String cacheKey = helper.computeCacheKey(props);
        BaseDTO cachedValue = (BaseDTO) caffeineCache.getIfPresent(cacheKey);
        if (Objects.nonNull(cachedValue)) {
            log.info("Value retrieved from cache");
            return cachedValue.getData();
        }
        log.info("Cache hit missed, will be retrieved from Rest call and will be cached conditionally");
        BaseDTO responseDTO = (BaseDTO) caffeineCache.get(cacheKey, key -> consumeFromGitAPI(props));
        helper.invalidateCacheIfTokenExpirationTimeShortLived(cacheKey, responseDTO.getGitHubTknExpirationTime());
        return responseDTO.getData();
    }

    //Support's GET Method of Git Rest API.
    public BaseDTO consumeFromGitAPI(GitRestAPIProperties props) {
        String url = helper.getEndpoint(props.getOperation());
        log.info("Git Hub Endpoint : {} for operation : {}", url, props.getOperation());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", props.getAuthToken());
        headers.set("Accept", "application/vnd.github+json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Object> restResponse =
                restTemplate.exchange(url, HttpMethod.GET, entity, props.getAClass(), props.getQueryParams());
        // If want to log response, need to mask confidential fields as some API needs PAT.
        BaseDTO detailsDTO = BaseDTO.builder()
                .data(restResponse.getBody())
                .build();
        helper.updateTokenExpirationTimeIfApplicable(restResponse, detailsDTO);
        return detailsDTO;
    }


}
