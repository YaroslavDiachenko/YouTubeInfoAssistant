package YouTubeData_Channels;


import Program.Main;
import YouTubeData_Videos.Request3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class Request1 {

    public static void main(String[] args) throws UnirestException {
//        ChannelListResponse channelListResponse = requestChannelData(channelId);
/*
        SearchListResponse searchListResponse = Request2.requestSearchVideosList(channelId, myApiKey);
        StringBuilder videosList = new StringBuilder();
        for (SearchResult i : searchListResponse.items) {
            videosList.append(i.id.videoId);
            videosList.append(",");
        }
        VideoListResponse videoListResponse = Request3.requestVideoDataAsObject(videosList.toString(),myApiKey);
        System.out.println(videoListResponse.countComments());

*/
        String result = Request3.requestVideoDataAsString("JsKIZdXhnho", myApiKey);
        System.out.println(result);
    }

    public static String myApiKey = "AIzaSyBifuUatBiNP7C8X2zjMM1BJWinnrES6Ic";

    public static Channel getChannelFromServer(String channelId) {
        ChannelListResponse channelsResponse = requestChannelData(channelId);
        return channelsResponse == null ? null : channelsResponse.items.get(0);
    }
    public static ChannelListResponse getYouTubeChannelResponse(String channelId) throws UnirestException {
        return requestChannelData(channelId);
    }

    public static void initApplication() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private static ChannelListResponse requestChannelData(String channelId) {
        try {
            initApplication();
            HttpResponse<ChannelListResponse> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                    .queryString("id", channelId)
                    .queryString("part", "snippet,statistics")
                    .queryString("fields", "items(id,snippet(title,publishedAt),statistics(viewCount,subscriberCount,videoCount))")
                    .queryString("key", myApiKey)
                    .asObject(ChannelListResponse.class);
            return response.getBody();
        } catch (UnirestException e) {
            Main.alert("UnirestException thrown","Channel not found");
        }
        return null;
    }
}
