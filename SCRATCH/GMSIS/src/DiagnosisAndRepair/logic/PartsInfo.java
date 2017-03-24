/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagnosisAndRepair.logic;

import javafx.beans.property.*;

/**
 *
 * @author Bayzid
 */
public class PartsInfo {
      
   private SimpleStringProperty partName;
   private SimpleIntegerProperty quantity;
   private SimpleDoubleProperty cost;
   
   public PartsInfo(String name, int quan, double cost)
   {
       this.partName = new SimpleStringProperty(name);
       this.quantity = new SimpleIntegerProperty(quan);
       this.cost = new SimpleDoubleProperty(cost);
   }
   
   public String getPartName()
   {
       return partName.get();
   }
   public int getQuantity()
   {
       return quantity.get();
   }
   public double getCost()
   {
       return cost.get();
   }
}
