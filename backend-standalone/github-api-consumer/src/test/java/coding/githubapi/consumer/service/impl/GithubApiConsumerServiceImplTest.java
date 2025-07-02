package coding.githubapi.consumer.service.impl;

import coding.githubapi.consumer.config.GitAPIConfig;
import coding.githubapi.consumer.constants.CommonConstants;
import coding.githubapi.consumer.dto.BaseDTO;
import coding.githubapi.consumer.dto.GitRepoBasicDetailsDTO;
import coding.githubapi.consumer.model.GitRestAPIProperties;
import coding.githubapi.consumer.util.GitRestAPIHelper;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GithubApiConsumerServiceImplTest {

    @InjectMocks
    GithubApiConsumerServiceImpl githubApiConsumerService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GitRestAPIHelper helper;

    @Mock
    private Cache<Object, Object> caffeineCache;

    void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_valueAvailableInCache_then_returnCacheValue(){
        GitRestAPIProperties restAPIProperties = GitRestAPIProperties.builder()
                .owner("dummyOwner").repoName("dummyRepo").authToken("123").operation(CommonConstants.BASIC_REPO_DETAILS_LOOKUP)
                .aClass(GitRepoBasicDetailsDTO.class)
                .build();
        Mockito.when(helper.computeCacheKey(Mockito.eq(restAPIProperties))).thenReturn("dummy");
        Mockito.when(caffeineCache.getIfPresent(Mockito.eq("dummy"))).thenReturn(BaseDTO.builder().data(
                GitRepoBasicDetailsDTO.builder().cloneUrl("dummyURL").build()).build());
        GitRepoBasicDetailsDTO response = (GitRepoBasicDetailsDTO) githubApiConsumerService.process(restAPIProperties);
        Assertions.assertEquals("dummyURL",response.getCloneUrl());
    }

    @Test
    public void when_valueNotAvailableInCache_then_ReturnComputedResponse(){
        GitRestAPIProperties restAPIProperties = GitRestAPIProperties.builder()
                .owner("dummyOwner").repoName("dummyRepo").authToken("123").operation(CommonConstants.BASIC_REPO_DETAILS_LOOKUP)
                .aClass(GitRepoBasicDetailsDTO.class)
                .build();
        Mockito.when(helper.computeCacheKey(Mockito.eq(restAPIProperties))).thenReturn("dummy");
        Mockito.when(caffeineCache.getIfPresent(Mockito.eq("dummy"))).thenReturn(null);
        Mockito.when(caffeineCache.get(Mockito.eq("dummy"),Mockito.any())).thenReturn(BaseDTO.builder().data(
                GitRepoBasicDetailsDTO.builder().cloneUrl("dummyCloneURL").build()).build());
        GitRepoBasicDetailsDTO response = (GitRepoBasicDetailsDTO) githubApiConsumerService.process(restAPIProperties);
        Assertions.assertEquals("dummyCloneURL",response.getCloneUrl());
    }

    @Test
    public void when_validRequest_then_consumerEndpointAndReturnBaseResponse(){
        GitRestAPIProperties restAPIProperties = GitRestAPIProperties.builder()
                .owner("dummyOwner").repoName("dummyRepo").authToken("123").operation(CommonConstants.BASIC_REPO_DETAILS_LOOKUP)
                .aClass(GitRepoBasicDetailsDTO.class)
                .build();
        String stubbedUrl = "http://localhost:8080/dummy";
        Mockito.when(helper.getEndpoint(Mockito.eq(restAPIProperties.getOperation()))).thenReturn(stubbedUrl);
        Mockito.when(restTemplate.exchange(Mockito.eq(stubbedUrl),Mockito.eq(HttpMethod.GET),Mockito.any(HttpEntity.class),Mockito.eq(restAPIProperties.getAClass()),
                Mockito.any(Map.class)
        )).thenReturn(ResponseEntity.ok(GitRepoBasicDetailsDTO.builder().cloneUrl("dummyRestCloneURL").build()));
        BaseDTO baseDTO = githubApiConsumerService.consumeFromGitAPI(restAPIProperties);
        System.out.println(baseDTO);
        Assertions.assertEquals("dummyRestCloneURL",((GitRepoBasicDetailsDTO) baseDTO.getData()).getCloneUrl());
    }

    @Test
    public void when_invalidRequest_then_consumerEndpointThrowsException(){
        GitRestAPIProperties restAPIProperties = GitRestAPIProperties.builder()
                .owner("dummyOwner").repoName("dummyRepo").authToken("123").operation(CommonConstants.BASIC_REPO_DETAILS_LOOKUP)
                .aClass(GitRepoBasicDetailsDTO.class)
                .build();
        String stubbedUrl = "http://localhost:8080/dummy";
        Mockito.when(helper.getEndpoint(Mockito.eq(restAPIProperties.getOperation()))).thenReturn(stubbedUrl);
        Mockito.when(restTemplate.exchange(Mockito.eq(stubbedUrl),Mockito.eq(HttpMethod.GET),Mockito.any(HttpEntity.class),Mockito.eq(restAPIProperties.getAClass()),
                Mockito.any(Map.class)
        )).thenThrow(new RestClientException("Invalid User"));
        Assertions.assertThrows(RestClientException.class, () ->githubApiConsumerService.consumeFromGitAPI(restAPIProperties));
    }


}