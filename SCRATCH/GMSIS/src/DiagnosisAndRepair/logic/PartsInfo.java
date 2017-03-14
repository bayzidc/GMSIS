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
   private SimpleStringProperty expiryDate;
   
   public PartsInfo(String name, int quan, String date)
   {
       this.partName = new SimpleStringProperty(name);
       this.quantity = new SimpleIntegerProperty(quan);
       this.expiryDate = new SimpleStringProperty(date);
   }
   
   public String getPartName()
   {
       return partName.get();
   }
   public int getQuantity()
   {
       return quantity.get();
   }
   public String getExpiryDate()
   {
       return expiryDate.get();
   }
}
