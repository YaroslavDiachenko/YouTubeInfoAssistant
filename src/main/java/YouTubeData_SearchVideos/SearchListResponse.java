package YouTubeData_SearchVideos;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class SearchListResponse {
    public String nextPageToken;
    public List<SearchResult> items;
}
