/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.entity;

/**
 *
 * Street with constructors, getters, setters, view (toString)
 * 
 */
public class Street {
    
    private Long id;
    private String streetName;

    public Street() {
    }
    
    public Street(Long id, String streetName) {
        this.id = id;
        this.streetName = streetName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
}