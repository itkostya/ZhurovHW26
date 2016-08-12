/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.test;

import java.util.ArrayList;
import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAODatabase;
import com.mycompany.zhurovhw26.entity.Citizen;

/**
 *
 * Test for CRUD in AbstractDAODatabase (table Citizen)
 * 
 */
public class TestAbstractDAOCitizen {

    public static void test(AbstractDAODatabase<Citizen> crud) {

        crud.getConnection();
        crud.checkMetaData();  // First of all for H2 database

        ArrayList<Citizen> peopleForTest = new ArrayList<>();
        ArrayList<Citizen> people = new ArrayList<>();

        System.out.println("Start data:");
        people = crud.Read();
        for (Citizen person : people) {
            System.out.println(person);
        }
        
        System.out.println("After creating new people:");
        for (int i = 0; i < 10; i++) {
            peopleForTest.add(new Citizen(null, String.valueOf(i), String.valueOf(i), i)); 
        }
        for (Citizen person : peopleForTest) {
            crud.Create(person);
        }
        
        for (Citizen person : crud.Read()) {
            System.out.println(person);
        }

        System.out.println("After updating:");
        for (Citizen person : peopleForTest) {
            person.setAge(2 * person.getAge());
            crud.Update(person);
        }        
        
        for (Citizen person : crud.Read()) {
            System.out.println(person);
        }
        
        System.out.println("After deleting:");
        for (Citizen person : peopleForTest) {
            crud.Delete(person);
        }
        
        for (Citizen person : crud.Read()) {
            System.out.println(person);
        }
        
        Citizen oneMan = crud.getOneById(1);
        System.out.println("One Man: " + oneMan);

    }
    
}
