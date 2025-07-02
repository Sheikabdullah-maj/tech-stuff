package coding.githubapi.consumer.service;

import coding.githubapi.consumer.dto.GitRepoBasicDetailsDTO;
import coding.githubapi.consumer.model.GitRestAPIProperties;

public interface GithubApiConsumerService {
    Object process(GitRestAPIProperties props);
}
