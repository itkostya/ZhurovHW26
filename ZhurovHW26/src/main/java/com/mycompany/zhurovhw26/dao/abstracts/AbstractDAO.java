/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.abstracts;

import java.util.ArrayList;

/**
 * 
 * Abstact class for different data container: Database, textfile, etc.
 * 
 */
public abstract class AbstractDAO<T> implements DAO<T> {

    @Override
    public abstract void Create(T t);

    @Override
    public abstract ArrayList<T> Read();

    @Override
    public abstract void Update(T t);

    @Override
    public abstract void Delete(T t);

    @Override
    public abstract void getConnection();

    @Override
    public abstract void closeConnection();

    public abstract void checkMetaData();

}
