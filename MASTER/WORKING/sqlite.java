/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;
import java.sql.*;

public class sqlite
{
  public Connection connect() throws ClassNotFoundException
  {
    Connection c = null;

    try 
    {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
      c.createStatement().execute("PRAGMA foreign_keys = ON");
      
    } catch (SQLException e)
    {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    }
    return c;
  }
}
    
   
        
    
