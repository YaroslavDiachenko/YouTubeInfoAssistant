package Program;


import YouTubeData_Channels.Channel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ChannelData {
    private final SimpleStringProperty channelId;
    private final SimpleStringProperty channelName;
    private final SimpleStringProperty dateOfCreation;
    private final SimpleIntegerProperty numberOfSubscribers;
    private final SimpleIntegerProperty numberOfVideos;
    private final SimpleIntegerProperty numberOfViews;
    private SimpleIntegerProperty numberOfComments;

    public ChannelData(String channelId, String channelName, String dateOfCreation, int numberOfSubscribers, int numberOfVideos, int numberOfViews) {
        this.channelId = new SimpleStringProperty(channelId);
        this.channelName = new SimpleStringProperty(channelName);
        this.dateOfCreation = new SimpleStringProperty(dateOfCreation);
        this.numberOfSubscribers = new SimpleIntegerProperty(numberOfSubscribers);
        this.numberOfVideos = new SimpleIntegerProperty(numberOfVideos);
        this.numberOfViews = new SimpleIntegerProperty(numberOfViews);
        this.numberOfComments = new SimpleIntegerProperty();
    }

    public ChannelData(Channel channel) {
        this.channelId = new SimpleStringProperty(channel.id);
        this.channelName = new SimpleStringProperty(channel.snippet.title);
        this.dateOfCreation = new SimpleStringProperty(channel.snippet.publishedAt.substring(0,10));
        this.numberOfSubscribers = new SimpleIntegerProperty(channel.statistics.subscriberCount);
        this.numberOfVideos = new SimpleIntegerProperty(channel.statistics.videoCount);
        this.numberOfViews = new SimpleIntegerProperty(channel.statistics.viewCount);
        this.numberOfComments = new SimpleIntegerProperty();
    }

    public String getChannelId() {
        return channelId.get();
    }

    public String getChannelName() {
        return channelName.get();
    }

    public String getDateOfCreation() {
        return dateOfCreation.get();
    }

    public int getNumberOfSubscribers() {
        return numberOfSubscribers.get();
    }

    public int getNumberOfVideos() {
        return numberOfVideos.get();
    }

    public int getNumberOfViews() {
        return numberOfViews.get();
    }

    public int getNumberOfComments() {
        return numberOfComments.get();
    }

    public void setChannelId(String channelId) {
        this.channelId.set(channelId);
    }

    public void setChannelName(String channelName) {
        this.channelName.set(channelName);
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation.set(dateOfCreation);
    }

    public void setNumberOfSubscribers(int numberOfSubscribers) {
        this.numberOfSubscribers.set(numberOfSubscribers);
    }

    public void setNumberOfVideos(int numberOfVideos) {
        this.numberOfVideos.set(numberOfVideos);
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews.set(numberOfViews);
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments.set(numberOfComments);
    }
}

