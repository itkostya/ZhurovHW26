/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.test;

import java.util.ArrayList;
import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAOTextFile;
import com.mycompany.zhurovhw26.entity.Citizen;

/**
 *
 * Test for CRUD in AbstractDAOTextFile (table Citizen)
 * 
 */
public class TestAbstractDAOTestFileCitizen {

    public static void test(AbstractDAOTextFile<Citizen> crud) {

        crud.getConnection();
        crud.checkMetaData();  

        ArrayList<Citizen> peopleForTest = new ArrayList<>();
        ArrayList<Citizen> people = new ArrayList<>();

        System.out.println("Start data:");
        people = crud.Read();
        for (Citizen person : people) {
            System.out.println(person);
        }

        System.out.println("After creating new people:");
        for (int i = 0; i < 10; i++) {
             peopleForTest.add(new Citizen(null, String.valueOf(i), String.valueOf(i), i, Long.valueOf(i)));
           // peopleForTest.add(new Citizen(Long.valueOf(i), String.valueOf(i), String.valueOf(i), i, Long.valueOf(i)));
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


    }
}
