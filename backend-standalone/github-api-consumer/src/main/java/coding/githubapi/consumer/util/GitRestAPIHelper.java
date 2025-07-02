package coding.githubapi.consumer.util;

import coding.githubapi.consumer.config.GitAPIConfig;
import coding.githubapi.consumer.constants.CommonConstants;
import coding.githubapi.consumer.dto.BaseDTO;
import coding.githubapi.consumer.model.GitRestAPIProperties;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class GitRestAPIHelper {

    @Autowired
    private Cache<Object, Object> caffeineCache;

    @Autowired
    private GitAPIConfig gitAPIConfig;

    public void updateTokenExpirationTimeIfApplicable(ResponseEntity<Object> restResponse, BaseDTO baseDTO) {
        List<String> expirationValueList = restResponse.getHeaders().get("github-authentication-token-expiration");
        if (Objects.isNull(expirationValueList) || expirationValueList.isEmpty()) {
            log.info("For this operation auth header is not needed,hence can be cached without any condition");
        } else {
            baseDTO.setGitHubTknExpirationTime(restResponse.getHeaders().get("github-authentication-token-expiration").getFirst());
        }
    }

    public void invalidateCacheIfTokenExpirationTimeShortLived(Object key, String gitHubTknExpirationTime) {
        if (StringUtils.hasText(gitHubTknExpirationTime) && !isGithubTokenValidUntilCacheTimeout(gitHubTknExpirationTime)) {
            caffeineCache.invalidate(key);
            log.info("Cache invalidated as the github token lifespan is shorter than cache lifespan");
        }
    }

    public boolean isGithubTokenValidUntilCacheTimeout(String githubTokenExpirationTime) {
        LocalDateTime cacheExpirationTime = LocalDateTime.now().plusHours(CommonConstants.CACHE_TIMEOUT);
        LocalDateTime githubTknExpirationTime = LocalDateTime.parse(githubTokenExpirationTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        return cacheExpirationTime.isBefore(githubTknExpirationTime);
    }

    public String getEndpoint(String operation) {

        String endpoint = "";

        switch (operation) {
            case CommonConstants.BASIC_REPO_DETAILS_LOOKUP:
                endpoint = gitAPIConfig.getBaseUrl() + gitAPIConfig.getReadBasicRepoDetailsUrl();
                break;
            // can add the endpoints for other operations like user details, fork option,..
        }
        return endpoint;
    }

    public String computeCacheKey(GitRestAPIProperties properties){
        String cacheKey = "";

        switch (properties.getOperation()) {
            case CommonConstants.BASIC_REPO_DETAILS_LOOKUP:
                cacheKey = properties.getOwner() + properties.getRepoName() + properties.getAuthToken() + properties.getOperation();
                break;
            // can add the cache keys for other operations like user details, fork option,..
        }
        return cacheKey;
    }
}
