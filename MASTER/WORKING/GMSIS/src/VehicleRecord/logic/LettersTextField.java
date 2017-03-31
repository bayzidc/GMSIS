/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.logic;

import javafx.scene.control.TextField;

/**
 *
 * @author User
 */
public class LettersTextField extends TextField{
    
    public LettersTextField()
    {
        this.setPromptText("Enter only letters...");
    }
    
    @Override
    public void replaceText(int i, int i1, String string)
    {
        if(string.matches("[a-zA-Z]") || string.matches("[ ]") || string.trim().isEmpty())
        {
            super.replaceText(i,i1,string);
        }
    }
    @Override
    public void replaceSelection(String string)
    {
        super.replaceSelection(string);
    }
}
