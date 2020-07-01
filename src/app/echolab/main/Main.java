/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mario
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final int PORT = 8888;
    private static final int TIMEOUT_CLIENT = 60000;
    private static final String CONFIGURATION_FILE = "config/config.properties";
    
    public static void main(String[] args) throws IOException{
        Properties prop = new Properties();
        prop.setProperty("active", "true");
        File fProperties = new File(CONFIGURATION_FILE);
        if(!fProperties.exists()){
           fProperties.getParentFile().mkdirs();
           fProperties.createNewFile();
           fProperties = null;
        }
        FileOutputStream outputStream = new FileOutputStream(CONFIGURATION_FILE);
        prop.store(outputStream, "This is a configuration file");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            InputStream input;
            boolean flag = true;
            while(flag){
                new UserThread(serverSocket.accept(), TIMEOUT_CLIENT).start();
                input = new FileInputStream(CONFIGURATION_FILE);
                prop.load(input);
                input.close();
                flag = Boolean.parseBoolean(prop.getProperty("active"));
            }
        }
        
    }
}
