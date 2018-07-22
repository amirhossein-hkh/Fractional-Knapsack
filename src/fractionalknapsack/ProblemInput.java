package fractionalknapsack;

import java.util.ArrayList;

public class ProblemInput {
    private ArrayList<Item> items = new ArrayList<>();
    private double maxWeight;
    
    public ProblemInput() {
    }

    public ProblemInput(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    public Item getItems(int index) {
        return items.get(index);
    }
   
    public void setItemsValue(int index,double value) {
       this.items.get(index).setValue(value);
    }
    
    public void setItemsWeight(int index,double weight){
        this.items.get(index).setWeight(weight);
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }  
}
