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
import com.mongodb.DBObject;
import com.mycompany.zhurovhw26.entity.Street;

/**
 *
 * Help for referencies in Database Mongo
 * 
 */
public class StreetMyMongoImpl {

    public static DBObject getDBObjectOfStreet(DB db, Long streetId) {
        DBCollection collection = db.getCollection("STREET");
        DBCursor cursor = collection.find(new BasicDBObject("ID", streetId));
        if (cursor.hasNext()) {
            DBObject cur = cursor.next();
            return cur;
        }

        return null;
    }

    public static DBObject getDBObjectOfStreet(DB db, Street street) {
        return getDBObjectOfStreet(db, street.getId());
    }
}
