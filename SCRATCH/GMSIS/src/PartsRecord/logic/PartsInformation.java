/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.logic;

import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;


/**
 *
 * @author Fabiha
 */
public class PartsInformation {
    
    public StringProperty partName;
    public StringProperty partDescription;
    public DoubleProperty cost;
    
    public PartsInformation(String partName, String partDescription, double cost) {
       
        this.partName = new SimpleStringProperty(partName);
        this.partDescription = new SimpleStringProperty(partDescription);
        this.cost = new SimpleDoubleProperty(cost);
    }
    
    public String getPartName() {
        return partName.get();
    }

    public String getPartDescription() {
        return partDescription.get();
    }

    public double getCost() {
        return cost.get();
    }
    
    
    public void setPartName(String name) {
        partName.set(name);
    }

    public void setPartDescription(String description) {
        partDescription.set(description);
    }
    
    
    public void setCost(double number) {
        cost.set(number);
    }

    
    
    public StringProperty partNameProperty() {
        return partName;
    }

    public StringProperty partDescriptionProperty() {
        return partDescription;
    }
    
    
    public DoubleProperty costProperty() {
        return cost;
    }
    
}
