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
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Mario
 */
public class ConnectionDB {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ConnectionDB.class);
    private static final ConnectionDB main = new ConnectionDB();
    private static BasicDataSource basicDataSource;
    
    private ConnectionDB(){
    }
    
    public static void loadConnection(String User, String Password){
        Properties dbProps = new Properties();
        try(InputStream propertyFile = ConfigProperties.getResourceAsInputStream("dbConfig.properties")){
            dbProps.load(propertyFile);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = Decrypt.decryptText(dbProps.getProperty("url"));
        String driver = Decrypt.decryptText(dbProps.getProperty("driver"));
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUsername(User);
        basicDataSource.setPassword(Password);
        basicDataSource.setUrl(url);
        basicDataSource.setInitialSize(4); //The initial number of connections that are created when the pool is started. 
        basicDataSource.setMaxTotal(10); //The maximum number of active connections that can be allocated from this pool at the same time, or negative for no limit.
        basicDataSource.setMaxIdle(8);
    }
    
    public static ConnectionDB getInstance(){
        return main;
    }
    
    public static Connection openConnection() throws SQLException, ClassNotFoundException{
        return basicDataSource.getConnection();
    }
    
    public static void closeConnection(Connection con) throws SQLException{
        if(con!=null&&!con.isClosed()) con.close();
    }
}
