package Program;


import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import static Program.Main.*;

public class StartScreen {
    VBox layout;

    public StartScreen() {
        layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        Text hello = new Text("Welcome to YouTube Info Assistant!");
        Hyperlink hyperlinkYouTubeAnalytics = new Hyperlink("Search for channels");
        hyperlinkYouTubeAnalytics.setFocusTraversable(false);
        hyperlinkYouTubeAnalytics.setOnAction(event -> {
            pane.getChildren().clear();
            pane.getChildren().add(mainScreen.layout);
        });
        Hyperlink hyperlinkSettings = new Hyperlink("Settings");
        hyperlinkSettings.setFocusTraversable(false);
        hyperlinkSettings.setOnAction(event -> {
            settingsScreen.updateSettings();
            pane.getChildren().clear();
            pane.getChildren().add(settingsScreen.layout);
        });
        layout.getChildren().addAll(hello, hyperlinkYouTubeAnalytics, hyperlinkSettings);
    }
}
