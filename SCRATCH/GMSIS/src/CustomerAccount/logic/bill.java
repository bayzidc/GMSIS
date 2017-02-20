/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAccount.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Joen
 */
public class bill {

    public IntegerProperty totalCost;
    public IntegerProperty bookingID;
    
    public bill(int ID, int costOfBill) {
        this.totalCost = new SimpleIntegerProperty(costOfBill);
        this.bookingID = new SimpleIntegerProperty(ID);
    }

    public int getTotalCost() {
        return totalCost.get();
    }

    public int getBookingID() {
        return bookingID.get();
    }

    public IntegerProperty totalCost() {
        return totalCost;
    }

    public IntegerProperty bookingID() {
        return bookingID;
    }
   
}
