package Program;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static Program.Main.requestScreen;

public class ResultScreen {

    static final ObservableList<ChannelData> data = FXCollections.observableArrayList();
    VBox layout;

    TableView<ChannelData> table;
    TableColumn column1;
    TableColumn column2;
    TableColumn column3;
    TableColumn column4;
    TableColumn column5;
    TableColumn column6;

    Text timer;

    public ResultScreen() {
        layout = new VBox();
        layout.setSpacing(10);

        Text title = new Text("Table test");
        table = new TableView<>();
        table.setMinWidth(600);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxHeight(220);
        addColumns();
        table.setItems(data);
        table.getColumns().addAll(column1,column2,column3,column4,column5);

        timer = new Text();
        timer.setOpacity(0.6);

        layout.setSpacing(5);
        layout.setPadding(new Insets(10, 0, 0, 10));
        layout.getChildren().addAll(title,table,timer);
    }

    void setSorting(int requestType) {
        table.getSortOrder().clear();
        if (requestType == 3) {
            String column = requestScreen.multiInput_sortColumn.getValue().toString();
            String type = requestScreen.multiInput_sortType.getValue().toString();
            switch (column) {
                case ("by channel name"):
                    table.getSortOrder().add(column1);
                    column1.setSortType(type.equals("in ascending order") ? TableColumn.SortType.ASCENDING : TableColumn.SortType.DESCENDING);
                    break;
                case ("by creation date"):
                    table.getSortOrder().add(column2);
                    column2.setSortType(type.equals("in ascending order") ? TableColumn.SortType.ASCENDING : TableColumn.SortType.DESCENDING);
                    break;
                case ("by number of subscribers"):
                    table.getSortOrder().add(column3);
                    column3.setSortType(type.equals("in ascending order") ? TableColumn.SortType.ASCENDING : TableColumn.SortType.DESCENDING);
                    break;
                case ("by number of videos"):
                    table.getSortOrder().add(column4);
                    column4.setSortType(type.equals("in ascending order") ? TableColumn.SortType.ASCENDING : TableColumn.SortType.DESCENDING);
                    column4.setSortable(true);
                    break;
                case ("by number of views"):
                    table.getSortOrder().add(column5);
                    column5.setSortType(type.equals("in ascending order") ? TableColumn.SortType.ASCENDING : TableColumn.SortType.DESCENDING);
                    break;
            }
            table.sort();
        }
        if (requestType == 6) {
            table.getSortOrder().add(column6);
            column6.setSortType(TableColumn.SortType.ASCENDING);
            table.sort();
        }
    }

    void addColumns() {
        column1 = new TableColumn("Channel name");
        column1.setCellValueFactory(new PropertyValueFactory<ChannelData,String>("channelName"));
        column1.setMinWidth(150);

        column2 = new TableColumn("Creation date");
        column2.setCellValueFactory(new PropertyValueFactory<ChannelData,Integer>("dateOfCreation"));
        column2.setMinWidth(80);

        column3 = new TableColumn("Subscribers");
        column3.setCellValueFactory(new PropertyValueFactory<ChannelData,Integer>("numberOfSubscribers"));
        column3.setMinWidth(80);

        column4 = new TableColumn("Videos");
        column4.setCellValueFactory(new PropertyValueFactory<ChannelData,Integer>("numberOfVideos"));
        column4.setMinWidth(80);

        column5 = new TableColumn("Views");
        column5.setCellValueFactory(new PropertyValueFactory<ChannelData,Integer>("numberOfViews"));
        column5.setMinWidth(80);

        column6 = new TableColumn("Comments");
        column6.setCellValueFactory(new PropertyValueFactory<ChannelData,Integer>("numberOfComments"));
        column6.setMinWidth(80);
    }
}
