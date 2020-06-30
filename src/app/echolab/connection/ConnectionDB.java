/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Mario
 */
public class ConnectionDB {
    
    public static Connection openConnection(String Url, String User, String Password, String Driver) throws SQLException, ClassNotFoundException{
        Class.forName(Driver);
        return DriverManager.getConnection(Url, User, Password);
    }
    
    public static void closeConnection(Connection con) throws SQLException{
        if(con!=null&&!con.isClosed()) con.close();
    }
}
