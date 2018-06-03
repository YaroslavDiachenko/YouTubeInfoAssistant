package Program;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import static Program.Main.*;

public class ProgramMenu{
    MenuBar menuBar;

    public ProgramMenu() {
        menuBar = new MenuBar();
        Menu menuFile = new Menu("Go to");

            Menu fileMenuAnalytics = new Menu("YouTube Analytics");
            MenuItem menuItemRequest1 = new MenuItem(nameRequest1);
            MenuItem menuItemRequest2 = new MenuItem(nameRequest2);
            MenuItem menuItemRequest3 = new MenuItem(nameRequest3);
            MenuItem menuItemRequest4 = new MenuItem(nameRequest4);
            MenuItem menuItemRequest5 = new MenuItem(nameRequest5);
            MenuItem menuItemRequest6 = new MenuItem(nameRequest6);
            fileMenuAnalytics.getItems().addAll(menuItemRequest1, menuItemRequest2, menuItemRequest3, menuItemRequest4, menuItemRequest5, menuItemRequest6);

            menuItemRequest1.setOnAction(event -> {
                requestScreen.requestType = 1;
                requestScreen.showRequestScreen();
            });
            menuItemRequest2.setOnAction(event -> {
                requestScreen.requestType = 2;
                requestScreen.showRequestScreen();
            });
            menuItemRequest3.setOnAction(event -> {
                requestScreen.requestType = 3;
                requestScreen.showRequestScreen();
            });
            menuItemRequest4.setOnAction(event -> {
                requestScreen.requestType = 4;
                requestScreen.showRequestScreen();
            });
            menuItemRequest5.setOnAction(event -> {
                requestScreen.requestType = 5;
                requestScreen.showRequestScreen();
            });
            menuItemRequest6.setOnAction(event -> {
                requestScreen.requestType = 6;
                requestScreen.showRequestScreen();
            });

            MenuItem fileMenuSettings = new MenuItem("Settings");
            fileMenuSettings.setOnAction(event -> {
                settingsScreen.updateSettings();
                pane.getChildren().clear();
                pane.getChildren().add(settingsScreen.layout);
            });
            menuFile.getItems().addAll(fileMenuAnalytics,fileMenuSettings);

        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().addAll(menuFile, menuEdit);
    }
}
