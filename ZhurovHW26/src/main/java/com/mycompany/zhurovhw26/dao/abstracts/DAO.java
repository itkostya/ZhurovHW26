/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.abstracts;

import java.util.ArrayList;

/**
 * 
 * Interface for CRUD operations
 * 
 */
public interface DAO<T> {

    void Create(T t);

    ArrayList<T> Read();

    void Update(T t);

    void Delete(T t);

    T getOneById(long id);

    void getConnection();

    void closeConnection();
}
