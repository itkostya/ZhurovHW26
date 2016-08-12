/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.datafileimpl.json;

import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAOTextFile;

/**
 *
 * JsonDAO - just initialitation constructor
 * 
 */
public abstract class JsonDAO <T> extends AbstractDAOTextFile<T> {
 
    public JsonDAO(String fileName) {
        super(fileName);
    }
      
}