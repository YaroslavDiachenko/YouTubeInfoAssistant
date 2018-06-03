package YouTubeData_Videos;


import YouTubeData_Channels.Statistics;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Video {
    public YouTubeData_Videos.Statistics statistics;

}
