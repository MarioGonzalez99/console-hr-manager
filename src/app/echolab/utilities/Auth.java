/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.utilities;

import app.echolab.properties.ConfigProperties;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Mario
 */
public class Auth {
    public static String code;
    public static void generateCode(){
        Auth.code = ""+((int) (Math.random()*1000000));
    }
    public static void sendEmail(String userEmail) throws IOException, EmailException{

        Properties emailProps = new Properties();
        emailProps.load(ConfigProperties.getResourceAsInputStream("emailConfig.properties"));
        HtmlEmail email= new HtmlEmail();
        email.setHostName(Decrypt.decryptText(emailProps.getProperty("host")));
        email.addTo(userEmail);
        email.setFrom(Decrypt.decryptText(emailProps.getProperty("usr")));
        email.setSubject("Codigo de ingreso (Favor no responder a este correo electronico)");
        email.setHtmlMsg("Tu codigo de ingreso es: "+Auth.code);
        email.setTextMsg("Tu correo parece no soportar Html");
        email.setSmtpPort(465);
        email.setAuthentication(Decrypt.decryptText(emailProps.getProperty("usr")),Decrypt.decryptText(emailProps.getProperty("pss")));
        email.setSSLOnConnect(true);
        email.send();
    }
}
