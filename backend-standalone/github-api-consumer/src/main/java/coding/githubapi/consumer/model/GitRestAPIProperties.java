package coding.githubapi.consumer.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GitRestAPIProperties {
    private String owner;
    private String repoName;
    private String authToken;
    private String operation;
    private Class aClass;

    public Map<String, String> getQueryParams() {
        return Map.of("owner",owner,"repoName",repoName,"authToken",authToken,"operation",operation);
    }

}
