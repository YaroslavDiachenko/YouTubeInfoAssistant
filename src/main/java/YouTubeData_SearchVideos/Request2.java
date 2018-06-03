package YouTubeData_SearchVideos;


import YouTubeData_Channels.Request1;
import YouTubeData_Videos.Request3;
import YouTubeData_Videos.VideoListResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static Program.Main.alert;

public class Request2 {
    static String myApiKey = "AIzaSyBifuUatBiNP7C8X2zjMM1BJWinnrES6Ic";

    public static void main(String[] args) throws UnirestException {
        System.out.println(getCommentsFromServer("UCxuSu641oYj9ty254odMNdQ"));
    }

    public static SearchListResponse requestSearchVideosList(String channelId) throws UnirestException {
        Request1.initApplication();
        HttpResponse<SearchListResponse> response = Unirest.get("https://www.googleapis.com/youtube/v3/search")
                .queryString("type", "video")
                .queryString("channelId", channelId)
                .queryString("part", "id")
                .queryString("fields", "nextPageToken,items(id(videoId))")
                .queryString("maxResults", "49")
                .queryString("key", myApiKey)
                .asObject(SearchListResponse.class);
        return response.getBody();
    }

    public static SearchListResponse requestSearchVideosListForNextPage(String channelId, String pageToken) throws UnirestException {
        Request1.initApplication();
        HttpResponse<SearchListResponse> response = Unirest.get("https://www.googleapis.com/youtube/v3/search")
                .queryString("type", "video")
                .queryString("channelId", channelId)
                .queryString("part", "id")
                .queryString("fields", "items(id(videoId))")
                .queryString("maxResults", "49")
                .queryString("pageToken", pageToken)
                .queryString("key", myApiKey)
                .asObject(SearchListResponse.class);
        return response.getBody();
    }

    public static int getCommentsFromServer(String channelId) {
        int totalCommentsCount = 0;
        try {
            totalCommentsCount = 0;
            String pageToken;
            SearchListResponse searchListResponse = requestSearchVideosList(channelId);
            StringBuilder videosList = new StringBuilder();
            for (SearchResult i : searchListResponse.items) {
                videosList.append(i.id.videoId);
                videosList.append(",");
            }
            VideoListResponse videoListResponse = Request3.requestVideoDataAsObject(videosList.toString(),myApiKey);
            totalCommentsCount += videoListResponse.countComments();
            pageToken = searchListResponse.nextPageToken;

            while (pageToken != null) {
                SearchListResponse nextSearchListResponse = requestSearchVideosListForNextPage(channelId,pageToken);
                StringBuilder nextVideosList = new StringBuilder();
                for (SearchResult i : nextSearchListResponse.items) {
                    nextVideosList.append(i.id.videoId);
                    nextVideosList.append(",");
                }
                VideoListResponse nextVideoListResponse = Request3.requestVideoDataAsObject(nextVideosList.toString(),myApiKey);
                totalCommentsCount += nextVideoListResponse.countComments();
                pageToken = nextSearchListResponse.nextPageToken;
            }
        } catch (UnirestException e) {
            alert("UnirestException thrown", "Cannot get comments from server.");
        }
        return totalCommentsCount;
    }
}
