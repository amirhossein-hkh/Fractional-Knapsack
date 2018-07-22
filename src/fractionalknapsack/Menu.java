package fractionalknapsack;

import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Menu {

    private VBox root;
    private Scene scene;
    private Button random;
    private Button input;
    private final GetInput userInputScene = new GetInput();
    

    public Menu() {
        //title
        Label title = new Label("Fractional Knapsack");
        title.setFont(Font.font(40));

        root = new VBox(100);
        root.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(title, new Insets(50, 0, 0, 0));
        HBox h = new HBox(100);
        h.setAlignment(Pos.CENTER);
        VBox.setMargin(h, new Insets(50, 0, 0, 0));
        //random input button
        random = new Button("Random input");
        random.setStyle("-fx-background-color:#5ccd23;");
        random.setOnAction(event -> {
            randomInput();
            Fractionalknapsack.setTheScene();
            Fractionalknapsack.window.setScene(Fractionalknapsack.mainScene);
            Fractionalknapsack.algorithm();
        });
        //user input button
        input = new Button("User input");
        input.setStyle("-fx-background-color:#1fc6da;");
        input.setOnAction(event -> Fractionalknapsack.window.setScene(userInputScene.getScene()));
        
        
        h.getChildren().addAll(input, random);
        root.getChildren().addAll(title, h);
        scene = new Scene(root, 500, 500);
    }

    public void randomInput() {
        Random r = new Random(System.nanoTime());
        Fractionalknapsack.inputs = new ProblemInput(r.nextDouble() * 100 + 30);
        int c = r.nextInt(30) + 5;
        for (int i = 0; i < c; i++) {
            Fractionalknapsack.inputs.getItems().add(
                  new Item(r.nextDouble() * 30 + 5, r.nextDouble() * 30 + 5,i));
        }
    }

    public Scene getScene() {
        return scene;
    }
}
