/*
 * To change this license header, choose License Headers in Project Attribute.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.prop;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * This class work with table properties
 * 
 */
public class Attribute {
    
    public static String getAttribute(String propertyName) {
        
        Properties property = new Properties();         
        
        try ( FileInputStream fis= new FileInputStream("src/main/resources/config.properties"))
        {
            property.load(fis);            
            return property.getProperty(propertyName);

        } catch (IOException ex) {
            Logger.getLogger(Attribute.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    
    }    

}
