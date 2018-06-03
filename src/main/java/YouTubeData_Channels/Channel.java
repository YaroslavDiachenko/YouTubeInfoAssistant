package YouTubeData_Channels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Channel {
    public String id;
    public Snippet snippet;
    public Statistics statistics;


}
