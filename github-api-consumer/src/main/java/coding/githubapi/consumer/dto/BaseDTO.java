package coding.githubapi.consumer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BaseDTO implements Serializable {
    @JsonIgnore
    private String gitHubTknExpirationTime;
    private Object data;
}
