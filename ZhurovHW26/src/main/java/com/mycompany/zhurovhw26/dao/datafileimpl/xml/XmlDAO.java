/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.datafileimpl.xml;

import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAOTextFile;

/**
 *
 * XmlDAO - just initialitation constructor
 * 
 */
public abstract class XmlDAO <T> extends AbstractDAOTextFile<T> {
 
    public XmlDAO(String fileName) {
        super(fileName);
    }
      
}
