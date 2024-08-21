package coding.githubapi.consumer.service;

import coding.githubapi.consumer.dto.GitRepoBasicDetailsDTO;

public interface GithubApiConsumerService {
    GitRepoBasicDetailsDTO doReadOnlyOperation(String owner, String repoName, String authToken, String operation);
}
