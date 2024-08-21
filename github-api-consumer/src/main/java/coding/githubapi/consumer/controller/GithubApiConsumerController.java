package coding.githubapi.consumer.controller;

import coding.githubapi.consumer.constants.CommonConstants;
import coding.githubapi.consumer.dto.GitRepoBasicDetailsDTO;
import coding.githubapi.consumer.model.GitRestAPIProperties;
import coding.githubapi.consumer.service.GithubApiConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GithubApiConsumerController {

    @Autowired
    private GithubApiConsumerService githubApiConsumerService;

    @GetMapping("/repositories/{owner}/{repoName}")
    public ResponseEntity<Object> getRepoBasicDetails(@PathVariable String owner,
                                              @PathVariable String repoName,
                                              @RequestHeader(name = "Authorization",defaultValue = "") String authToken
                                                      ){
        log.info("Request received to get basic repository details, owner : {}, repositoryName : {}", owner, repoName);
        GitRestAPIProperties props = GitRestAPIProperties.builder()
                .owner(owner).repoName(repoName).authToken(authToken).operation(CommonConstants.BASIC_REPO_DETAILS_LOOKUP)
                .aClass(GitRepoBasicDetailsDTO.class)
                .build();
        return ResponseEntity.ok().body(githubApiConsumerService.process(props));
    }
}
