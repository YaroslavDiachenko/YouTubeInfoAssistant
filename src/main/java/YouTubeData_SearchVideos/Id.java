package YouTubeData_SearchVideos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Id {
    public String videoId;
}
