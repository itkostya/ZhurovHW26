/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.databaseimpl.mongo;

import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAODatabase;
import com.mycompany.zhurovhw26.prop.Attribute;


/**
 *
 * Abstract class for Mongo - initialitation driver, create constructor
 * 
 */
public abstract class MongoDAO <T> extends AbstractDAODatabase<T>  {
    
     public MongoDAO() {
        super(Attribute.getAttribute("mongo.url"), "", "", "");
    }

    public MongoDAO(String url, String user, String pass, String driver) {
        super(url, user, pass, driver);
    }   
    
}
