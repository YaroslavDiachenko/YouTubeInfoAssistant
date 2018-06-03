package Program;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import static Program.Main.*;

public class MainScreen {
    VBox layout;

    public MainScreen() {
        layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        Button buttonBackMainScreen = new Button("Back to start page");
        buttonBackMainScreen.setFocusTraversable(false);
        buttonBackMainScreen.setOnAction(event -> {
            Main.pane.getChildren().clear();
            Main.pane.getChildren().add(startScreen.layout);
        });

        Text title = new Text("Search options:");

        Hyperlink hyperlinkRequest1 = new Hyperlink(nameRequest1);
        Hyperlink hyperlinkRequest2 = new Hyperlink(nameRequest2);
        Hyperlink hyperlinkRequest3 = new Hyperlink(nameRequest3);
        Hyperlink hyperlinkRequest4 = new Hyperlink(nameRequest4);
        Hyperlink hyperlinkRequest5 = new Hyperlink(nameRequest5);
        Hyperlink hyperlinkRequest6 = new Hyperlink(nameRequest6);

        layout.getChildren().addAll(buttonBackMainScreen,title, hyperlinkRequest1, hyperlinkRequest2, hyperlinkRequest3, hyperlinkRequest4, hyperlinkRequest5, hyperlinkRequest6);

        hyperlinkRequest1.setOnAction(event -> {
            requestScreen.requestType = 1;
            requestScreen.showRequestScreen();
        });
        hyperlinkRequest2.setOnAction(event -> {
            requestScreen.requestType = 2;
            requestScreen.showRequestScreen();
        });
        hyperlinkRequest3.setOnAction(event -> {
            requestScreen.requestType = 3;
            requestScreen.showRequestScreen();
        });
        hyperlinkRequest4.setOnAction(event -> {
            requestScreen.requestType = 4;
            requestScreen.showRequestScreen();
        });
        hyperlinkRequest5.setOnAction(event -> {
            requestScreen.requestType = 5;
            requestScreen.showRequestScreen();
        });
        hyperlinkRequest6.setOnAction(event -> {
            requestScreen.requestType = 6;
            requestScreen.showRequestScreen();
        });
    }
}
