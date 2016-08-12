/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.datafileimpl.csv;

import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAOTextFile;

/**
 *
 * CsvDAO - just initialitation constructor
 * 
 */
public abstract class CsvDAO <T> extends AbstractDAOTextFile<T> {
    
    public CsvDAO(String fileName) {
        super(fileName);
    }
      
    
}