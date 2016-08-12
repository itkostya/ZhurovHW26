/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.databaseimpl.mongo.help;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mycompany.zhurovhw26.entity.Citizen;
import com.mycompany.zhurovhw26.entity.Street;

/**
 *
 * Help for referencies in Database Mongo
 * 
 */
public class MongoTestDataCreator {
    
     public static void insertStreet(DBCollection collection, Street street) {

        DBCursor cursor = collection.find(new BasicDBObject("ID", street.getId()));

        if (!cursor.hasNext()) {
            BasicDBObject document = new BasicDBObject();
            document.put("ID", street.getId());
            document.put("STREET_NAME", street.getStreetName());
            collection.insert(document);
        }

    }

    public static void insertCitizen(DB db, Citizen citizen) {

        DBCollection collection = db.getCollection("CITIZEN");
        DBCursor cursor = collection.find(new BasicDBObject("ID", citizen.getId()));

        if (!cursor.hasNext()) {
            BasicDBObject document = new BasicDBObject();
            document.put("ID", citizen.getId());
            document.put("FIRST_NAME", citizen.getFirstName());
            document.put("LAST_NAME", citizen.getLastName());
            document.put("AGE", citizen.getAge());
            document.put("STREET_ID", StreetMyMongoImpl.getDBObjectOfStreet(db, citizen.getStreetId()));
            collection.insert(document);
        }

    }

 
    
}
