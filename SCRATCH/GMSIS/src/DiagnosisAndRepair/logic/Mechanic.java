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
public class Mechanic{
    
    private SimpleIntegerProperty mechanicID;
    private SimpleDoubleProperty hourlyRate;
    private SimpleDoubleProperty hoursWorked;
    
    public Mechanic(int id, double rate, double hour)
    {
        this.mechanicID = new SimpleIntegerProperty(id);
        this.hourlyRate = new SimpleDoubleProperty(rate);
        this.hoursWorked = new SimpleDoubleProperty(hour);
    }

    public int getMechanicID() {
        return mechanicID.get();
    }

    public double getHourlyRate() {
        return hourlyRate.get();
    }

    public double getHoursWorked() {
        return hoursWorked.get();
    }
    
    public void setmechanicID(int id) {
        mechanicID.set(id);
    }

    public void setHourlyRate(double rate) {
        hourlyRate.set(rate);
    }

    public void setHoursWorked(double hour) {
        hoursWorked.set(hour);
    }
    
}
