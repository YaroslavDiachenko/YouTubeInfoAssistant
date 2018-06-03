package Program;


import YouTubeData_Channels.Channel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Program.Main.*;
import static YouTubeData_Channels.Request1.getChannelFromServer;
import static YouTubeData_SearchVideos.Request2.getCommentsFromServer;

public class RequestScreen {

    VBox layout;

    int requestType;
    int numberOfInputFields;

    Text requestName;
    Text requestDescription;
    Pane inputAndOutputArea;

    VBox simpleInput_interface;
    VBox multiInput_interface;

    ComboBox simpleInput_field;

    VBox multiInput_labels;
    VBox multiInput_fields;
    Text multiInput_label1;
    Text multiInput_label2;
    ComboBox multiInput_field1;
    ComboBox multiInput_field2;
    Button multiInput_submit;

    ChoiceBox multiInput_sortColumn;
    ChoiceBox multiInput_sortType;
    HBox multiInput_sorting;

    public RequestScreen() {
        layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        Button buttonBackAnalyticsScreen = new Button();
        buttonBackAnalyticsScreen.setText("Back to main page");
        buttonBackAnalyticsScreen.setFocusTraversable(false);
        buttonBackAnalyticsScreen.setOnAction(event -> {
            Main.pane.getChildren().clear();
            Main.pane.getChildren().add(mainScreen.layout);
        });

        requestName = new Text();
        requestDescription = new Text();

        inputAndOutputArea = new Pane();

        createSimpleInputInterface();
        createMultiInputInterface();

        layout.getChildren().addAll(buttonBackAnalyticsScreen,requestName,requestDescription, inputAndOutputArea);

        layout.setSpacing(5);
        layout.setPadding(new Insets(10));

    }

    void createSimpleInputInterface() {
        simpleInput_interface = new VBox();
        simpleInput_interface.setPadding(new Insets(10));
        simpleInput_interface.setSpacing(10);
        simpleInput_field = createComboBox();
        Button simpleInput_submit = new Button("Submit");
        simpleInput_interface.getChildren().addAll(simpleInput_field, simpleInput_submit);

        simpleInput_submit.setOnAction((ActionEvent e) -> {
            long start = System.currentTimeMillis();
            adjustTable();
            ResultScreen.data.clear();
            resultScreen.setSorting(requestType);
            String channelId = simpleInput_field.getValue().toString();
            if (channelId != null && !"".equals(channelId.trim())) {
                if (settingsScreen.historyList.contains(channelId)) channelId = settingsScreen.historyMap.get(channelId);
                ChannelData channelData = getChannelData(channelId);
                if (channelData != null) {
                    ResultScreen.data.add(channelData);
                    showOutputInterface();
                    if (!settingsScreen.historyList.contains(channelData.getChannelName())) {
                        settingsScreen.historyList.add(channelData.getChannelName());
                        settingsScreen.historyMap.put(channelData.getChannelName(), channelData.getChannelId());
                    }
                    long end = System.currentTimeMillis();
                    if (setting_executionTime) resultScreen.timer.setText("Executed in: " + (int)(end - start) + "ms");
                    else resultScreen.timer.setText("");
                }else alert("Invalid data","Channel not found.");
            }else alert("Incomplete input","Please fill input field.");
        });
    }

    void createMultiInputInterface() {
        multiInput_interface = new VBox();
        multiInput_interface.setPadding(new Insets(10));
        multiInput_interface.setSpacing(10);

        multiInput_label1 = new Text("Channel 1:");
        multiInput_label2 = new Text("Channel 2:");
        multiInput_labels = new VBox(multiInput_label1, multiInput_label2);
        multiInput_labels.setTranslateY(5);
        multiInput_labels.setSpacing(16);

        multiInput_field1 = createComboBox();
        multiInput_field2 = createComboBox();
        multiInput_fields = new VBox(multiInput_field1,multiInput_field2);
        multiInput_fields.setSpacing(5);
        numberOfInputFields = 2;

        Button addInputField = new Button("Add field");
        Button removeInputField = new Button("Remove field");

        HBox dataInputExtendButtons = new HBox(15,addInputField,removeInputField);
        multiInput_submit = new Button("Submit");

        HBox dataInput = new HBox(multiInput_labels, multiInput_fields);
        dataInput.setSpacing(5);

        Text multiInput_text = new Text("Sort:");
        multiInput_sortColumn = new ChoiceBox(FXCollections.observableArrayList(new ArrayList<>(Arrays.asList("by channel name","by creation date","by number of subscribers","by number of videos","by number of views"))));
        multiInput_sortType = new ChoiceBox(FXCollections.observableArrayList(new ArrayList<>(Arrays.asList("in ascending order","in descending order"))));
        multiInput_sortColumn.getSelectionModel().selectFirst();
        multiInput_sortType.getSelectionModel().selectFirst();
        multiInput_sorting = new HBox(5, multiInput_text,multiInput_sortColumn,multiInput_sortType);
        multiInput_sorting.setAlignment(Pos.CENTER);


        multiInput_interface.getChildren().addAll(dataInput,dataInputExtendButtons,multiInput_submit);

        addInputField.setOnAction(event -> {
            Text multiInput_addLabel = new Text("Channel " + ((numberOfInputFields++) + 1) + ":");
            multiInput_labels.getChildren().add(multiInput_addLabel);
            ComboBox multiInput_addField = createComboBox();
            multiInput_fields.getChildren().add(multiInput_addField);
        });
        removeInputField.setOnAction(event -> {
            if (numberOfInputFields > 2) {
                multiInput_labels.getChildren().remove(numberOfInputFields-1);
                multiInput_fields.getChildren().remove(numberOfInputFields-1);
                numberOfInputFields--;
            }
        });
        multiInput_submit.setOnAction(event -> {
            long start = System.currentTimeMillis();
            adjustTable();
            ResultScreen.data.clear();
            resultScreen.setSorting(requestType);
            List<String> channelIDsList = new ArrayList<>();
            for (Node i : multiInput_fields.getChildren()) {
                String s = ((ComboBox)i).getValue().toString();
                if (s != null && !"".equals(s.trim())) {
                    if (settingsScreen.historyList.contains(s)) s = settingsScreen.historyMap.get(s);
                    if (!channelIDsList.contains(s)) channelIDsList.add(s);
                }
            }

            if (channelIDsList.size() >= 2) {
                for (String i : channelIDsList) {
                    ChannelData channelData = getChannelData(i);
                    if (channelData != null) {
                        ResultScreen.data.add(channelData);
                        if (!settingsScreen.historyList.contains(channelData.getChannelName())) {
                            settingsScreen.historyList.add(channelData.getChannelName());
                            settingsScreen.historyMap.put(channelData.getChannelName(), channelData.getChannelId());
                        }
                    }
                }
                if (ResultScreen.data.size() > 0) {
                    resultScreen.table.sort();
                    showOutputInterface();
                    long end = System.currentTimeMillis();
                    if (setting_executionTime) resultScreen.timer.setText("Executed in: " + (int)(end - start) + "ms");
                    else resultScreen.timer.setText("");
                }else alert("Invalid data","No channels found.");
            }else alert("Incomplete input","Please add at least two different channels.");
        });
    }

    ComboBox createComboBox() {
        ComboBox comboBox = new ComboBox();
        comboBox.setFocusTraversable(false);
        comboBox.setEditable(true);
        comboBox.setMinWidth(100);
        comboBox.setPromptText("Enter channel ID:");
        comboBox.setItems(settingsScreen.historyList);
        return comboBox;
    }

    void resetInputInterface() {
        if (requestType == 1 || requestType == 4) {
            simpleInput_field.getEditor().clear();
        } else {
            multiInput_labels.getChildren().clear();
            multiInput_fields.getChildren().clear();
            multiInput_field1.getEditor().clear();
            multiInput_field2.getEditor().clear();
            multiInput_labels.getChildren().addAll(multiInput_label1, multiInput_label2);
            multiInput_fields.getChildren().addAll(multiInput_field1, multiInput_field2);
            numberOfInputFields = 2;
        }
    }

    void showRequestScreen() {
        resetInputInterface();
        showInputInterface();
        pane.getChildren().clear();
        pane.getChildren().add(layout);
    }

    void showInputInterface() {
        inputAndOutputArea.getChildren().clear();
        switch (requestType) {
            case 1:
                requestName.setText(nameRequest1);
                requestDescription.setText("Shows global information about a channel.");
                inputAndOutputArea.getChildren().add(simpleInput_interface);
                break;
            case 2:
                requestName.setText(nameRequest2);
                requestDescription.setText("Shows global information about the list of specified channels.");
                if (multiInput_interface.getChildren().contains(multiInput_sorting)) multiInput_interface.getChildren().remove(multiInput_sorting);
                inputAndOutputArea.getChildren().add(multiInput_interface);
                break;
            case 3:
                requestName.setText(nameRequest3);
                requestDescription.setText("Sorts the list of specified channels and their information by specified parameter.");
                if (!multiInput_interface.getChildren().contains(multiInput_sorting)) multiInput_interface.getChildren().add(2,multiInput_sorting);
                inputAndOutputArea.getChildren().add(multiInput_interface);
                break;
            case 4:
                requestName.setText(nameRequest4);
                requestDescription.setText("Shows information (including total number of comments) about a channel.");
                inputAndOutputArea.getChildren().add(simpleInput_interface);
                break;
            case 5:
                requestName.setText(nameRequest5);
                requestDescription.setText("Shows information (including total number of comments) about the list of specified channels.");
                if (multiInput_interface.getChildren().contains(multiInput_sorting)) multiInput_interface.getChildren().remove(multiInput_sorting);
                inputAndOutputArea.getChildren().add(multiInput_interface);
                break;
            case 6:
                requestName.setText(nameRequest6);
                requestDescription.setText("Sorts the list of specified channels and their information by total number of comments.");
                if (multiInput_interface.getChildren().contains(multiInput_sorting)) multiInput_interface.getChildren().remove(multiInput_sorting);
                inputAndOutputArea.getChildren().add(multiInput_interface);
                break;
        }
    }

    void showOutputInterface() {
        inputAndOutputArea.getChildren().clear();
        inputAndOutputArea.getChildren().add(resultScreen.layout);
    }

    void adjustTable() {
        if (requestType > 3) {
            if (!resultScreen.table.getColumns().contains(resultScreen.column6)) resultScreen.table.getColumns().add(resultScreen.column6);
        }
        else {
            if (resultScreen.table.getColumns().contains(resultScreen.column6)) resultScreen.table.getColumns().remove(resultScreen.column6);
        }
    }

    ChannelData getChannelData(String channelId) {
        // CACHE ON:
        if (setting_useCache) {
            // CHANNEL CACHED:
            if (checkIfCached(channelId)) {
                List<String> cachedData = getFromCacheChannelData(channelId);
                ChannelData channelData = createObjectFromCache(cachedData);
                // Adding comments if needed:
                if (requestType > 3) {
                    // COMMENTS CACHED
                    if (cachedData.size() > 6) channelData.setNumberOfComments(Integer.parseInt(cachedData.get(6)));
                    // COMMENTS NOT CACHED
                    else {
                        channelData.setNumberOfComments(getCommentsFromServer(channelId));
                        writeCacheToFile(channelData);
                    }
                }
                return channelData;

            // CHANNEL NOT CACHED:
            } else {
                Channel channel = getChannelFromServer(channelId);
                if (channel != null) {
                    ChannelData channelData = new ChannelData(channel);
                    if (requestType > 3) {
                        channelData.setNumberOfComments(getCommentsFromServer(channelId));
                    }
                    writeCacheToFile(channelData);
                    return channelData;
                } else alert("Error", "No channel returned.");
            }

        // CACHE OFF:
        } else {
            Channel channel = getChannelFromServer(channelId);
            if (channel != null) {
                ChannelData channelData = new ChannelData(channel);
                if (requestType > 3) {
                    channelData.setNumberOfComments(getCommentsFromServer(channelId));
                }
                return channelData;
            }
        }
        return null;
    }

    ChannelData createObjectFromCache(List<String> data) {
        return new ChannelData(data.get(0),data.get(1),data.get(2),
                Integer.parseInt(data.get(3)),Integer.parseInt(data.get(4)),Integer.parseInt(data.get(5)));
    }

    void writeCacheToFile(ChannelData channelData) {
        try {
            String savePath = setting_cacheDirectory + channelData.getChannelId() + ".txt";
            File file = new File(savePath);
            FileWriter writer = new FileWriter(file);
            writer.write(channelData.getChannelId() + "\n");
            writer.write(channelData.getChannelName() + "\n");
            writer.write(channelData.getDateOfCreation() + "\n");
            writer.write(channelData.getNumberOfSubscribers() + "\n");
            writer.write(channelData.getNumberOfVideos() + "\n");
            writer.write(channelData.getNumberOfViews() + "");
            if (channelData.getNumberOfComments() > 0) writer.write("\n" + channelData.getNumberOfComments() + "");
            writer.flush();
        } catch (Exception e){
            alert("Exception thrown","ChannelData not cashed.");
        }
    }

    boolean checkIfCached(String channelId) {
        String channelIdFullName = channelId + ".txt";
        File cashDirectoryFile = new File(setting_cacheDirectory);
        File[] allFiles = cashDirectoryFile.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if (channelIdFullName.equals(file.getName())) return true;
            }
        }
        return false;
    }

    List<String> getFromCacheChannelData(String channelId) {
        String cachedChannelPath = (setting_cacheDirectory + "/" + channelId + ".txt");
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cachedChannelPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            return list;
        }catch (IOException e) {
            alert("IOException thrown","Cannot read cached file.");
        }
        return null;
    }
}
