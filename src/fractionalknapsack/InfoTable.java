package fractionalknapsack;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class InfoTable {
    private Rectangle r;
    private Label title;
    private Label content;
    private StackPane infoTable;

    public InfoTable(String title,String content) {
        infoTable = new StackPane();
        r = new Rectangle(200, 250, Color.GRAY);
        VBox v = new VBox(10);
        this.title = new Label(title);
        this.title.setTextFill(Color.WHITE);
        this.title.setFont(Font.font(20));
        this.content = new Label(content);
        this.content.setTextFill(Color.WHITE);
        this.content.setFont(Font.font(20));
        v.getChildren().addAll(this.title,this.content);
        infoTable.getChildren().addAll(r,v);
    }

    public StackPane getInfoTable() {
        return infoTable;
    }

    public void setContent(String content) {
        this.content.setText(content);
    }

    public Label getContent() {
        return content;
    }
    
            
}
