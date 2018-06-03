package Program;


import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Program.Main.*;

public class SettingsScreen {
    VBox layout;
    private String savedSettingsPath;
    Map<String,String> historyMap;
    ObservableList<String> historyList;

    // setting ui and control elements:
    private RadioButton option1_ButtonYes;
    private RadioButton option1_ButtonNo;
    private ToggleGroup option1_ToggleGroup;
    private Text option2_CurrentCacheSavePath;
    private ToggleGroup option3_ToggleGroup;
    private RadioButton option3_ButtonYes;
    private RadioButton option3_ButtonNo;
    private TitledPane option1;
    private TitledPane option2;
    private TitledPane option3;

    public SettingsScreen() {
        layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        Button buttonBackMainScreen = new Button("Back to start page");
        buttonBackMainScreen.setFocusTraversable(false);
        buttonBackMainScreen.setOnAction(event -> {
            pane.getChildren().clear();
            pane.getChildren().add(startScreen.layout);
        });

        savedSettingsPath = "settings.txt";
        downloadSavedSettings();
        Text title = new Text("Settings");

     // use cache
        Text option1_Title = new Text("Cache using:");
        option1_ToggleGroup = new ToggleGroup();
        option1_ButtonYes = new RadioButton("Yes");
        option1_ButtonYes.setToggleGroup(option1_ToggleGroup);
        option1_ButtonNo = new RadioButton("No");
        option1_ButtonNo.setToggleGroup(option1_ToggleGroup);
        Button option1SaveButton = new Button("Save");
        option1SaveButton.setOnAction(e -> {
            setting_useCache = option1_ButtonYes.isSelected();
            alert("Use cache", setting_useCache ? "Cache enabled." : "Cache disabled.");
        });
        option1 = new TitledPane("Cache using",new VBox(5,option1_Title, option1_ButtonYes, option1_ButtonNo,option1SaveButton));

     // cache directory
        Text option2_Title = new Text("Current path to cache folder:");
        option2_CurrentCacheSavePath = new Text(setting_cacheDirectory);
        Button option2_ClearCacheButton = new Button("Clear cache");
        option2_ClearCacheButton.setOnAction(e -> cleanCacheDirectory());
        TextField option2_NewCacheSavePath = new TextField();
        option2_NewCacheSavePath.setPromptText("Enter new path...");
        option2_NewCacheSavePath.setFocusTraversable(false);
        Button option2_SaveButton = new Button("Save");
        option2_SaveButton.setOnAction(e -> {
            setting_cacheDirectory = option2_NewCacheSavePath.getText();
            option2_CurrentCacheSavePath.setText(setting_cacheDirectory);
        });
        option2 = new TitledPane("Cache save path",new VBox(5,option2_Title,option2_CurrentCacheSavePath,option2_ClearCacheButton,option2_NewCacheSavePath,option2_SaveButton));

     // execution time
        Text option3Text = new Text("Show execution time spent?");
        option3_ToggleGroup = new ToggleGroup();
        option3_ButtonYes = new RadioButton("Yes");
        option3_ButtonYes.setToggleGroup(option3_ToggleGroup);
        option3_ButtonNo = new RadioButton("No");
        option3_ButtonNo.setToggleGroup(option3_ToggleGroup);
        Button option3SaveButton = new Button("Save");
        option3SaveButton.setOnAction(e -> {
            setting_executionTime = option3_ButtonYes.isSelected();
            alert("Execution time spent", setting_executionTime ? "Execution time spent enabled." : "Execution time spent disabled.");

        });
        option3 = new TitledPane("Execution time spent",new VBox(5,option3Text, option3_ButtonYes, option3_ButtonNo,option3SaveButton));

        layout.getChildren().addAll(buttonBackMainScreen,title,option1,option2,option3);

        updateHistoryList();
    }

 // contracts title panes and sets ui elements according to up-to-date settings parameters values;
    void updateSettings() {
        option1_ToggleGroup.selectToggle(setting_useCache ? settingsScreen.option1_ButtonYes : settingsScreen.option1_ButtonNo);
        option3_ToggleGroup.selectToggle(setting_executionTime ? settingsScreen.option3_ButtonYes : settingsScreen.option3_ButtonNo);
        option2_CurrentCacheSavePath.setText(setting_cacheDirectory);
        option1.setExpanded(false);
        option2.setExpanded(false);
        option3.setExpanded(false);
    }

 // downloads from file settings parameters values saved on last closing (applied each time program is opened);
    void downloadSavedSettings() {
        try (BufferedReader br = new BufferedReader(new FileReader(savedSettingsPath))) {
            setting_useCache = br.readLine().equals("true");
            setting_cacheDirectory = br.readLine();
            setting_executionTime = br.readLine().equals("true");
        } catch (IOException e) {
            alert("IOException thrown","Cannot download from file saved settings.");
        }
    }

 // saves up-to-date settings parameters values to file (applied each time program is closed);
    void saveSettings() {
        try {
            File file = new File(savedSettingsPath);
            FileWriter writer = new FileWriter(file);
            writer.write((setting_useCache ? "true" : "false") + "\n");
            writer.write(setting_cacheDirectory + "\n");
            writer.write(setting_executionTime ? "true" : "false");
            writer.flush();
        } catch (Exception e){
            alert("Exception thrown","Settings file not found.");
        }
    }

    void cleanCacheDirectory() {
        File folder = new File(setting_cacheDirectory);
        for (File file : folder.listFiles()) file.delete();
    }

    void updateHistoryList() {
        historyMap = new HashMap<>();
        File cashDirectoryFile = new File(setting_cacheDirectory);
        File[] allFiles = cashDirectoryFile.listFiles(file -> !file.isHidden());
        if (allFiles != null) {
            for (File file : allFiles) {
                if (!file.isDirectory()) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String id = br.readLine();
                        String name = br.readLine();
                        historyMap.put(name,id);
                    } catch (IOException e) {
                        alert("IOException thrown","Cannot read cache while updating history list");
                    }
                }
            }
        }
        historyList = new ObservableListWrapper<>(new ArrayList<>(historyMap.keySet()));
    }
}
