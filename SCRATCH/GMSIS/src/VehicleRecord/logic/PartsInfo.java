/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author User
 */
public class PartsInfo {
   public SimpleIntegerProperty partID = new SimpleIntegerProperty();
   public SimpleStringProperty partsUsed = new SimpleStringProperty();
   public SimpleIntegerProperty quantity = new SimpleIntegerProperty();
   
   public int getPartID()
   {
       return partID.get();
   }
           
   public String getPartsUsed()
   {
       return partsUsed.get();
   }
   
   public int getQuantity()
   {
       return quantity.get();
   }
   
   public void setPartID(int pID)
   {
       partID.set(pID);
   }
   
   public void setPartsUsed(String pUsed)
   {
       partsUsed.set(pUsed);
   }
   
   public void setQuantity(int quan)
   {
       quantity.set(quan);
   }
}
