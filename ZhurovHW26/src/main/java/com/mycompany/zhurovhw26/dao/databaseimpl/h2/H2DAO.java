/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.databaseimpl.h2;

import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAODatabase;
import com.mycompany.zhurovhw26.prop.Attribute;

/**
 *
 * Abstract class for H2 - initialitation driver, create constructor
 * 
 */
public abstract class H2DAO <T> extends AbstractDAODatabase<T> {
       
    public H2DAO() {        
        super(Attribute.getAttribute("h2.url"), "", "", Attribute.getAttribute("h2.driver"));
    }

    public H2DAO(String url, String user, String pass, String driver) {
        super(url, user, pass, driver);
    }    

}
