/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;
import java.sql.*;
/**
 *
 * @author User
 */
public class sqlite{

    /**
     *
     * @return
     */
    public static Connection Connector(){
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Authentication.sqlite");
            return conn;
        }
        
        catch(ClassNotFoundException | SQLException e){
            return null;
        }
    
   
        }
    
}