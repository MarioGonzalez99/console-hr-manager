/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.connection;

import app.echolab.properties.ConfigProperties;
import app.echolab.utilities.Decrypt;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Mario
 */
public class ConnectionDB {
    
    public static Connection openConnection(String User, String Password) throws SQLException, ClassNotFoundException{
        Properties dbProps = new Properties();
        try(InputStream propertyFile = ConfigProperties.getResourceAsInputStream("dbconfig.properties")){
            dbProps.load(propertyFile);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = Decrypt.decryptText(dbProps.getProperty("url"));
        String driver = Decrypt.decryptText(dbProps.getProperty("driver"));
        Class.forName(driver);
        return DriverManager.getConnection(url, User, Password);
    }
    
    public static void closeConnection(Connection con) throws SQLException{
        if(con!=null&&!con.isClosed()) con.close();
    }
}
