package Program;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application{

 // requests names
    static String nameRequest1 = "Show channel info";
    static String nameRequest2 = "Compare channels info";
    static String nameRequest3 = "Sort channels";
    static String nameRequest4 = "Media resonans";
    static String nameRequest5 = "Compare media resonans";
    static String nameRequest6 = "Sort by media resonans";

 // settings parameters:
    static boolean setting_useCache;
    static boolean setting_executionTime;
    static String setting_cacheDirectory;

 // program objects
    static Pane pane = new Pane();
    static StartScreen startScreen = new StartScreen();
    static ProgramMenu programMenu = new ProgramMenu();
    static SettingsScreen settingsScreen = new SettingsScreen();
    static MainScreen mainScreen = new MainScreen();
    static RequestScreen requestScreen = new RequestScreen();
    static ResultScreen resultScreen = new ResultScreen();

 // opens separate window with error notification
    public static void alert(String title, String message) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300);
        stage.setMinHeight(100);

        Text text = new Text();
        text.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(text, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

 // opens separate window requesting from user to make YES/NO choice
    public boolean confirm(String title, String message) {
        final boolean[] answer = new boolean[1];
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300);
        stage.setMinHeight(100);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setMinWidth(60);
        noButton.setMinWidth(60);

        yesButton.setOnAction(e -> {
            answer[0] = true;
            stage.close();
        });
        noButton.setOnAction(e -> {
            answer[0] = false;
            stage.close();
        });

        VBox layout = new VBox(10);
        HBox layout2 = new HBox(7);

        //Add buttons
        layout2.getChildren().addAll(yesButton,noButton);
        layout.getChildren().addAll(label, layout2);
        layout.setAlignment(Pos.CENTER);
        layout2.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
        return answer[0];
    }

 // launches JavaFX application
    @Override
    public void start(Stage primaryStage) {
        pane.getChildren().add(startScreen.layout);
        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(programMenu.menuBar, pane);
        Scene scene = new Scene(mainLayout, 700, 400);

        primaryStage.setTitle("YouTube Info Assistant");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            boolean answer = confirm("Closing the program", "Are you sure you want to exit?");
            if (answer) {
                settingsScreen.saveSettings();
                primaryStage.close();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
