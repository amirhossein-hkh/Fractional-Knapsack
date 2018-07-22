package fractionalknapsack;

import java.text.DecimalFormat;
import java.util.Collections;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Fractionalknapsack extends Application {

    public static ProblemInput inputs = new ProblemInput();
    public static Stage window;
    public static Scene mainScene;
    public static Group root;
    public static GridPane tableOfItems;
    public static Image bucketImg;
    public static ImageView bucket;
    public static Menu menu;
    public static Arc a1;
    public static Arc a2;
    public static InfoTable remainWeight;
    public static InfoTable lastItem;
    public static Item which;
    
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        bucketImg = new Image(getClass().getResourceAsStream("image/bucket.png"));
        menu = new Menu();
        primaryStage.setTitle("Fractional Knapsack");
        primaryStage.setScene(menu.getScene());
        primaryStage.show();
    }

    public static void setTheScene() {
        root = new Group();
        mainScene = new Scene(root, 800, 800);
        bucket = new ImageView(bucketImg);
        bucket.setLayoutX(10);
        bucket.setLayoutY(620);
        root.getChildren().add(bucket);
        tableOfItems = new GridPane();
        tableOfItems.setPadding(new Insets(20, 0, 0, 160));
        tableOfItems.setHgap(10);
        tableOfItems.setVgap(10);
        for (int i = 0; i < inputs.getItems().size(); i++) {
            GridPane.setConstraints(inputs.getItems(i).getItem(), i % 7, i / 7);
            tableOfItems.getChildren().add(inputs.getItems(i).getItem());
            inputs.getItems(i).getItem().setLayoutX(i * 90 + 160);
            inputs.getItems(i).getItem().setLayoutX(i * 90 + 20);
        }
        root.getChildren().add(tableOfItems);
        DecimalFormat df = new DecimalFormat("#.00");
        remainWeight = new InfoTable("Remain Weight", df.format(inputs.getMaxWeight()));
        remainWeight.getInfoTable().setLayoutX(300);
        remainWeight.getInfoTable().setLayoutY(500);
        lastItem = new InfoTable("Current Item", "");
        lastItem.getInfoTable().setLayoutX(550);
        lastItem.getInfoTable().setLayoutY(500);
        root.getChildren().addAll(lastItem.getInfoTable(),remainWeight.getInfoTable());
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                remainWeight.setContent(df.format(inputs.getMaxWeight()));
                if(which!=null){
                    lastItem.setContent("Value : "+df.format(which.getValue())+"\n"+
                            "Weight : "+df.format(which.getWeight())+"\n"+
                            "Value/Weight : "+df.format(which.getValue()/which.getWeight()));
                }
            }
        }.start();
        
    }

    public static void algorithm() {
        Collections.sort(inputs.getItems());
        double w = inputs.getMaxWeight();
        SequentialTransition finalAnimation = new SequentialTransition();
        int c = 0;
        while (w > 0) {
            if (w - inputs.getItems().get(c).getWeight() < 0) {
                break;
            }
            w -= inputs.getItems().get(c).getWeight();
            finalAnimation.getChildren().add(inputs.getItems().get(c).animation());
            c++;
        }
        double scale = w / inputs.getItems().get(c).getWeight();
        if (scale != 0) {
            a1 = new Arc(inputs.getItems().get(c).getItem().getLayoutX()+40,
            inputs.getItems().get(c).getItem().getLayoutY()+40, 40, 40, 0, 360 * scale);
            a1.setFill(inputs.getItems().get(c).getC().getFill());
            a1.setVisible(false);
            a1.setType(ArcType.ROUND);
            a2 = new Arc(80,60, 40, 40,360 * scale, 360 - (360 * scale));
            a2.setFill(inputs.getItems().get(c).getC().getFill());
            a2.setType(ArcType.ROUND);
            a2.setVisible(false);
            root.getChildren().addAll(a1, a2);
            finalAnimation.getChildren().add(arcAnimation(c));
        }
        finalAnimation.play();
    }

    public static SequentialTransition arcAnimation(int index) {
        //circle
        DropShadow dp = new DropShadow(40, Color.YELLOW);
        dp.setSpread(0.1);
        KeyValue Shine = new KeyValue(inputs.getItems().get(index).getC().effectProperty(), dp);
        KeyFrame shining = new KeyFrame(Duration.ONE, Shine);
        Timeline tShining = new Timeline(shining);
        PauseTransition pause1 = new PauseTransition(Duration.seconds(1));
        KeyValue ShineOff = new KeyValue(inputs.getItems().get(index).getC().effectProperty(), null);
        KeyFrame shiningOff = new KeyFrame(Duration.ONE, ShineOff);
        Timeline tShiningOff = new Timeline(shiningOff);
        FadeTransition fade = new FadeTransition(Duration.seconds(1), inputs.getItems().get(index).getItem());
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        KeyValue invisible = new KeyValue(inputs.getItems().get(index).getC().visibleProperty(), false);
        KeyFrame invisibleTime = new KeyFrame(Duration.ONE, invisible);
        Timeline invisibleAnimation = new Timeline(invisibleTime);
        SequentialTransition circle = new SequentialTransition(tShining,pause1,tShiningOff,fade,invisibleAnimation);
        //arc1
        KeyValue visible1 = new KeyValue(a1.visibleProperty(), true);
        KeyFrame visible1Time = new KeyFrame(Duration.ONE, visible1);
        Timeline visible1Animation = new Timeline(visible1Time);
        FadeTransition appear1 = new FadeTransition(Duration.seconds(1), a1);
        appear1.setFromValue(0);
        appear1.setToValue(1.0);
        SequentialTransition arc1 = new SequentialTransition(visible1Animation);
        //arc2
        KeyValue visible2 = new KeyValue(a2.visibleProperty(), true);
        KeyFrame visible2Time = new KeyFrame(Duration.ONE, visible2);
        Timeline visible2Animation = new Timeline(visible2Time);
        FadeTransition appear2 = new FadeTransition(Duration.seconds(1), a2);
        appear2.setFromValue(0);
        appear2.setToValue(1.0);
        ParallelTransition parallelAppearing = new ParallelTransition(appear1, appear2);
        KeyValue move = new KeyValue(a2.translateYProperty(), 675);
        KeyFrame moving = new KeyFrame(Duration.seconds(2), move);
        Timeline tMoving = new Timeline(moving);
        KeyValue transparency = new KeyValue(a2.opacityProperty(), 0.3);
        KeyFrame transparencyTime = new KeyFrame(Duration.ONE, transparency);
        Timeline transparencyAnimation = new Timeline(transparencyTime);
        SequentialTransition arc2 = new SequentialTransition(tMoving, transparencyAnimation);
        PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
        SequentialTransition total = new SequentialTransition(circle,arc1,visible2Animation,parallelAppearing,arc2,pause2);
        return total;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
