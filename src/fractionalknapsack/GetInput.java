package fractionalknapsack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GetInput {

    private Scene scene;
    private GridPane root;
    private TextField[] textfields;
    private Label[] labels;
    private TextArea textarea;
    private Button add;
    private Button finish;

    public GetInput() {
        root = new GridPane();
        scene = new Scene(root, 500, 500);
        labels = new Label[3];
        textfields = new TextField[3];
        textarea = new TextArea();
        makeInputScreen();
    }

    public final void makeInputScreen() {
        root.setPadding(new Insets(75, 30, 30, 50));
        root.setVgap(10);
        root.setHgap(10);
        //weight Label
        labels[0] = new Label("Weight :");
        GridPane.setConstraints(labels[0], 0, 0);
        //weight textfield
        textfields[0] = new TextField();
        textfields[0].setPromptText("Weight");
        textfields[0].setMaxWidth(80);
        textfields[0].setAlignment(Pos.CENTER);
        GridPane.setConstraints(textfields[0], 1, 0);
        //value label
        labels[1] = new Label("Value :");
        GridPane.setConstraints(labels[1], 0, 1);
        //value textfield
        textfields[1] = new TextField();
        textfields[1].setPromptText("Value");
        textfields[1].setAlignment(Pos.CENTER);
        textfields[1].setMaxWidth(80);
        GridPane.setConstraints(textfields[1], 1, 1);
        //textarea (for items info)
        textarea.setMaxWidth(300);
        GridPane.setConstraints(textarea, 1, 3);
        //add button
        Button add = new Button("add");
        add.setOnAction(event -> {
            try {
                Fractionalknapsack.inputs.getItems().add(
                        new Item(Double.parseDouble(textfields[1].getText()),
                                Double.parseDouble(textfields[0].getText()),
                                Fractionalknapsack.inputs.getItems().size() - 1));
                textarea.setText(textarea.getText()
                        + (Fractionalknapsack.inputs.getItems().size())
                        + " : " + "Weight : " + textfields[0].getText() + " , "
                        + "Value : " + textfields[1].getText() + "\n");
                textfields[0].clear();
                textfields[1].clear();
            } catch (Exception e) {
                textfields[0].clear();
                textfields[1].clear();
            }
        });
        GridPane.setConstraints(add, 1, 2);
        //max weight label
        labels[2] = new Label("Max Weight : ");
        GridPane.setConstraints(labels[2], 0, 4);
        //max weight textfield
        textfields[2] = new TextField();
        textfields[2].setPromptText("Max Weight");
        textfields[2].setMaxWidth(80);
        textfields[2].setAlignment(Pos.CENTER);
        GridPane.setConstraints(textfields[2], 1, 4);
        //finish button
        Button finish = new Button("finish");
        GridPane.setConstraints(finish, 1, 5);
        finish.setOnAction(event -> {
            try {
                Fractionalknapsack.inputs.setMaxWeight(Double.parseDouble(textfields[2].getText()));
            } catch (Exception e) {
            }
            Fractionalknapsack.setTheScene();
            Fractionalknapsack.window.setScene(Fractionalknapsack.mainScene);
            Fractionalknapsack.algorithm();
        });
        for (int i = 0; i < 3; i++) {
            root.getChildren().add(labels[i]);
            root.getChildren().add(textfields[i]);
        }
        root.getChildren().addAll(add, textarea, finish);
    }

    public Scene getScene() {
        return scene;
    }

    
}
