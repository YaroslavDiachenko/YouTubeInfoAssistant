package YouTubeData_Channels;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ChannelListResponse {
    public List<Channel> items;
}
