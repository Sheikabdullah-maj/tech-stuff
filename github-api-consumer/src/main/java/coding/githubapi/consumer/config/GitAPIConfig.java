package coding.githubapi.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "github.api")
@Component
@Data
public class GitAPIConfig {
    private String baseUrl;
    private String readBasicRepoDetailsUrl;
}
