package coding.githubapi.consumer.service.impl;

import coding.githubapi.consumer.config.GitAPIConfig;
import coding.githubapi.consumer.constants.CommonConstants;
import coding.githubapi.consumer.dto.GitRepoBasicDetailsDTO;
import coding.githubapi.consumer.service.GithubApiConsumerService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class GithubApiConsumerServiceImpl implements GithubApiConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GitAPIConfig gitAPIConfig;

    @Autowired
    private Cache<Object, Object> caffeineCache;

    @Override
    public GitRepoBasicDetailsDTO doReadOnlyOperation(String owner, String repoName, String authToken, String operation) {
        String cacheKey = owner+repoName+authToken+operation;
        Object cachedValue = caffeineCache.getIfPresent(cacheKey);
        if(Objects.nonNull(cachedValue)){
            log.info("Value retrieved from cache");
            return (GitRepoBasicDetailsDTO) cachedValue;
        }
        GitRepoBasicDetailsDTO gitRepoBasicDetails = (GitRepoBasicDetailsDTO) caffeineCache.get(cacheKey, key ->getGitRepoBasicDetails(owner, repoName, authToken));
        invalidateCacheIfTokenExpirationTimeShortLived(cacheKey, gitRepoBasicDetails.getGitHubTknExpirationTime());
        return gitRepoBasicDetails;
    }

    private GitRepoBasicDetailsDTO getGitRepoBasicDetails(String owner, String repoName, String authToken) {
        String url = gitAPIConfig.getBaseUrl() + gitAPIConfig.getReadBasicRepoDetailsUrl();
        log.info("Git Hub Endpoint : {}",url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        headers.set("Accept","application/vnd.github+json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<GitRepoBasicDetailsDTO> restResponse =
                restTemplate.exchange(url, HttpMethod.GET, entity, GitRepoBasicDetailsDTO.class, owner, repoName);
        log.info("Response received from github : {}",restResponse.getBody());
        GitRepoBasicDetailsDTO detailsDTO = restResponse.getBody();
        getTokenExpirationTimeIfApplicable(restResponse, detailsDTO);
        return detailsDTO;
    }

    private void getTokenExpirationTimeIfApplicable(ResponseEntity<GitRepoBasicDetailsDTO> restResponse, GitRepoBasicDetailsDTO detailsDTO) {
        List<String> expirationValueList = restResponse.getHeaders().get("github-authentication-token-expiration");
        if(Objects.isNull(expirationValueList) || expirationValueList.isEmpty()){
            log.info("For this operation auth header is not needed it seems, hence no need to check for expiration time");
        }else{
            detailsDTO.setGitHubTknExpirationTime(restResponse.getHeaders().get("github-authentication-token-expiration").getFirst());
        }
    }

    private void invalidateCacheIfTokenExpirationTimeShortLived(Object key, String gitHubTknExpirationTime){
        if(StringUtils.hasText(gitHubTknExpirationTime) && !isGithubTokenValidUntilCacheTimeout(gitHubTknExpirationTime)){
            caffeineCache.invalidate(key);
            log.info("Cache invalidated as the github token lifespan is shorter than cache lifespan");
        }
    }

    private boolean isGithubTokenValidUntilCacheTimeout(String githubTokenExpirationTime){
        LocalDateTime cacheExpirationTime = LocalDateTime.now().plusHours(CommonConstants.CACHE_TIMEOUT);
        LocalDateTime githubTknExpirationTime = LocalDateTime.parse(githubTokenExpirationTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        return cacheExpirationTime.isBefore(githubTknExpirationTime);
    }
}
