/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;
import java.sql.*;

public class sqlite
{
  public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      String sql = "" +
                   "" +
                   ""+
                   ""+
                   ""+
                   ""+
                   ""+
                   ""+
                   "";
      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
  }
}
    
   
        
    
