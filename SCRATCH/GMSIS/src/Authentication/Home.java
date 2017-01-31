/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author User
 */
public class Home {
  


   public SimpleStringProperty password = new SimpleStringProperty(); 
   public SimpleStringProperty ID = new SimpleStringProperty();
   public SimpleStringProperty firstName = new SimpleStringProperty();
   public SimpleStringProperty surname = new SimpleStringProperty();

   
   public String getPassword() {
      return password.get();
   }

   public String getID() {
      return ID.get();
   }

   public String getFirstName() {
      return firstName.get();
   }

   public String getSurname() {
      return surname.get();
   }

    
}
