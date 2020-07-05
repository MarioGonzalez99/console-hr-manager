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
        //Creamos las propiedades para mantener el programa activo en el servidor
        LOG.info("Creacion de propiedad active con valor true");
        Properties prop = new Properties();
        
        //Al iniciar el programa la propiedad active se establecera como true
        prop.setProperty("active", "true");
        File fProperties = new File(CONFIGURATION_FILE);
        if(!fProperties.exists()){
           LOG.info("Creacion de nuevo directorio y fichero de propiedades");
           fProperties.getParentFile().mkdirs();
           fProperties.createNewFile();
           fProperties = null;
        }
        
        //Creamos un outputStream para escribir las propiedades establecidas previamente al fichero externo de propiedades
        LOG.info("Creacion de outputStream con destino al fichero de propiedades");
        FileOutputStream outputStream = new FileOutputStream(CONFIGURATION_FILE);
        prop.store(outputStream, "This is a configuration file");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOG.info("Creacion de socket en el puerto "+PORT);
            InputStream input;
            boolean flag = true;
            //Mientras la propiedad en el fichero externo tenga el valor true, se ejecutara el programa en un while
            //Aceptando nuevos usuarios que ingresen al sistema
            while(flag){
                LOG.info("Creacion de un nuevo Thread de usuario");
                new UserThread(serverSocket.accept(), TIMEOUT_CLIENT).start();
                LOG.info("Carga de la propiedad active desde el fichero propiedades");
                input = new FileInputStream(CONFIGURATION_FILE);
                prop.load(input);
                input.close();
                flag = Boolean.parseBoolean(prop.getProperty("active"));
            }
        }
        
    }
}
