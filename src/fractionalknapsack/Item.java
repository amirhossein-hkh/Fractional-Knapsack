package fractionalknapsack;

import java.text.DecimalFormat;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Item implements Comparable<Item> {

    private double value;
    private double weight;
    private int index;
    private Circle c;
    private Label info;
    private StackPane item;

    public Item(double value, double weight, int index) {
        this.value = value;
        this.weight = weight;
        c = new Circle(40);
        makeColor();
        DecimalFormat df = new DecimalFormat("#.00");
        info = new Label("Value : " + df.format(this.value) + "\n" + "Weight :" + df.format(this.weight));
        info.setFont(Font.font(10));
        info.setTextFill(Color.BLACK);
        item = new StackPane(c, info);
        //item.setAlignment(Pos.CENTER);
        this.index = index;
    }

    public final void makeColor() {
        Color col;
        if (value < 15) {
            col = Color.ORANGE;
        } else if (value > 15 && value < 25) {
            col = Color.WHITE;
        } else {
            col = Color.YELLOW;
        }
        RadialGradient gradient1 = new RadialGradient(0, // focusAngle  
                .1, // focusDistance       
                0.4, // centerX     
                0.4, // centerY       
                1, // radius    
                true, // proportional         
                CycleMethod.NO_CYCLE, // cycleMethod   
                new Stop(0, col), // stops       
                new Stop(1, Color.BLACK));
        c.setFill(gradient1);
    }
    
    public SequentialTransition animation() {
        DropShadow dp = new DropShadow(40, Color.YELLOW);
        dp.setSpread(0.1);
        KeyValue Shine = new KeyValue(c.effectProperty(), dp);
        KeyFrame shining = new KeyFrame(Duration.ONE, Shine);
        Timeline tShining = new Timeline(shining);
        tShining.setOnFinished(event -> Fractionalknapsack.which = this);
        PauseTransition pause1 = new PauseTransition(Duration.seconds(1));
        FadeTransition fade = new FadeTransition(Duration.seconds(1),item);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        KeyValue labelVisibilty = new KeyValue(info.visibleProperty(),false);
        KeyFrame labelVis = new KeyFrame(Duration.ONE, labelVisibilty);
        Timeline invisibelingLabel = new Timeline(labelVis);
        KeyValue firstTranslateX = new KeyValue(item.translateXProperty(), -item.getLayoutX() + 40);
        KeyValue firstTranslateY = new KeyValue(item.translateYProperty(), -item.getLayoutY() + 20);
        KeyFrame move1 = new KeyFrame(Duration.ONE, firstTranslateX, firstTranslateY);
        Timeline tMove1 = new Timeline(move1);
        FadeTransition appear = new FadeTransition(Duration.seconds(1), item);
        appear.setFromValue(0);
        appear.setToValue(1.0);
        KeyValue secondTranslateY = new KeyValue(item.translateYProperty(), -item.getLayoutY() + 690);
        KeyFrame move2 = new KeyFrame(Duration.seconds(2), secondTranslateY);
        Timeline tMove2 = new Timeline(move2);
        SequentialTransition stackAnimation = new SequentialTransition(item, fade, tMove1, appear, tMove2);
        KeyValue transparecy = new KeyValue(c.opacityProperty(), 0.3);
        KeyFrame transparecing = new KeyFrame(Duration.ONE, transparecy);
        Timeline tTransparecing = new Timeline(transparecing);
        KeyValue ShineOff = new KeyValue(c.effectProperty(), null);
        KeyFrame shiningOff = new KeyFrame(Duration.ONE, ShineOff);
        Timeline tShiningOff = new Timeline(shiningOff);
        PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
        SequentialTransition total = new SequentialTransition(tShining,pause1,
             stackAnimation,tTransparecing,invisibelingLabel,tShiningOff,pause2);
        total.setOnFinished(event -> Fractionalknapsack.inputs.setMaxWeight(Fractionalknapsack.inputs.getMaxWeight()-weight));
        return total;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public StackPane getItem() {
        return item;
    }

    public Circle getC() {
        return c;
    }
    
    @Override
    public int compareTo(Item o) {
        return (o.value / o.weight) - (value / weight) > 0 ? 1 : -1;
    }

}
