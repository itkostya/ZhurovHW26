/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.entity;

/**
 *
 * Citizen with constructors, getters, setters, view (toString)
 *
 */
public class Citizen {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private Long streetId;

    public Citizen() {
    }

    // Without street_id - homeless person
    public Citizen(Long id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Citizen(Long id, String firstName, String lastName, int age, Long streetId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.streetId = streetId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    @Override
    public String toString() {
        return "Citizen{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", streetId=" + streetId + "}";
    }

}
