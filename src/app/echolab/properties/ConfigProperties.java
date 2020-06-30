/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.properties;

import java.io.InputStream;

/**
 *
 * @author Mario
 */
public class ConfigProperties {
    public static InputStream getResourceAsInputStream(String name){
        return ConfigProperties.class.getResourceAsStream(name);
    }    
}
