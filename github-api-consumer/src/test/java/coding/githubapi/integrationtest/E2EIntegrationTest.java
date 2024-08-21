package coding.githubapi.integrationtest;

import coding.githubapi.consumer.GithubApiConsumerApplication;
import coding.githubapi.consumer.controller.GithubApiConsumerController;
import coding.githubapi.consumer.dto.GitRepoBasicDetailsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(properties = "spring.config.name=test", webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes = {GithubApiConsumerApplication.class})
public class E2EIntegrationTest {

    @Value("${localtest.github.token}")
    private String githubToken;

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void when_validRequestData_then_returnValidResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", githubToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<GitRepoBasicDetailsDTO> restResponse =
                restTemplate.exchange("http://localhost:8003/repositories/{owner}/{repoName}",
                        HttpMethod.GET, entity, GitRepoBasicDetailsDTO.class, "Sheikabdullah-maj","tech-stuff");
        Assertions.assertEquals(HttpStatus.OK, restResponse.getStatusCode());
        GitRepoBasicDetailsDTO repoBasicDetailsDTO = restResponse.getBody();
        //other values will be null as the serialization property name is different, for testing can use the raw key name as it is with another DTO.
        Assertions.assertTrue(!repoBasicDetailsDTO.getDescription().isBlank());
    }

    @Test
    public void when_invalidRepoName_then_returnErrorResponseWith404InMessage() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", githubToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Assertions.assertThrows(RestClientException.class , () ->
                restTemplate.exchange("http://localhost:8003/repositories/{owner}/{repoName}",
                        HttpMethod.GET, entity, GitRepoBasicDetailsDTO.class, "Sheikabdullah-maj","tech-st"));

    }

}
