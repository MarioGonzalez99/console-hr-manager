/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.utilities;

import app.echolab.properties.ConfigProperties;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 *
 * @author Mario
 */
public class Decrypt {
    public static String decryptText(String encryptedText){
        Properties encryptProps = new Properties();
        try(InputStream propertyFile = ConfigProperties.getResourceAsInputStream("encryptKey.properties")){
            encryptProps.load(propertyFile);
        } catch (IOException ex) {
            Logger.getLogger(Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        String key = encryptProps.getProperty("key");
        BasicTextEncryptor decrypter = new BasicTextEncryptor();
        decrypter.setPassword(key);
        return decrypter.decrypt(encryptedText);
    }
}
