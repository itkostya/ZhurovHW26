/*
 * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.databaseimpl.mysql;

import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAODatabase;
import com.mycompany.zhurovhw26.prop.Attribute;

/**
 *
 * Abstract class for MySQL - initialitation driver, create constructor
 * 
 */
public abstract class MySQLDAO <T> extends AbstractDAODatabase<T>  {        

    public MySQLDAO() {
       
        super(Attribute.getAttribute("mysql.url"), 
                Attribute.getAttribute("mysql.user"), 
                Attribute.getAttribute("mysql.pass"), 
                Attribute.getAttribute("mysql.driver"));
    }

    public MySQLDAO(String url, String user, String pass, String driver) {
        super(url, user, pass, driver);
    }    

}
