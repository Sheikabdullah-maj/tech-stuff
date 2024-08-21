package coding.githubapi.consumer.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
public class GitRepoBasicDetailsDTO {

    private String fullName;

    @JsonGetter("fullName")
    public String getFullName() {
        return fullName;
    }

    @JsonSetter("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("description")
    private String description;

    private String cloneUrl;

    @JsonGetter("cloneUrl")
    public String getCloneUrl() {
        return cloneUrl;
    }

    @JsonSetter("clone_url")
    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    private Integer stars;

    @JsonGetter("stars")
    public Integer getStars() {
        return stars;
    }

    @JsonSetter("stargazers_count")
    public void setStars(Integer stars) {
        this.stars = stars;
    }

    private String createdAt;

    @JsonGetter("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonSetter("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonIgnore
    private String gitHubTknExpirationTime;


}
