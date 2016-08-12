/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.databaseimpl.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.zhurovhw26.dao.databaseimpl.mongo.help.StreetMyMongoImpl;
import com.mycompany.zhurovhw26.dao.databaseimpl.mongo.help.MongoTestDataCreator;
import com.mycompany.zhurovhw26.entity.Citizen;
import com.mycompany.zhurovhw26.entity.Street;
import com.mycompany.zhurovhw26.prop.Attribute;

/**
 *
 * Implementation for Mongo Database with table Citizen
 * 
 */
public class CitizenMongoDAOImpl extends MongoDAO<Citizen> {

    private Mongo mongo;
    private DB db;

    public CitizenMongoDAOImpl() {
        super();
        try {
            this.mongo = new MongoClient(Attribute.getAttribute("mongo.name"), Integer.valueOf(Attribute.getAttribute("mongo.port")));
            this.db = this.mongo.getDB("ADDRESS_BOOK");
        } catch (UnknownHostException ex) {
            Logger.getLogger(CitizenMongoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CitizenMongoDAOImpl(String url, String user, String pass, String driver) {
        super(url, user, pass, driver);
        try {
            // url = "localhost:27017"
            this.mongo = new MongoClient(url.substring(0, url.indexOf(":")), Integer.valueOf(url.substring(url.indexOf(":") + 1)));
            this.db = this.mongo.getDB("ADDRESS_BOOK");
        } catch (UnknownHostException ex) {
            Logger.getLogger(CitizenMongoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Create(Citizen citizen) {

        Long id = citizen.getId();
        DBCollection collection = db.getCollection("CITIZEN");

        if ((id == null) || (id == 0L)) {

            // find max ID in DB
            DBCursor cursor = collection.find().sort(new BasicDBObject("ID", -1)).limit(1);

            Long max = 0L;
            if (cursor.hasNext()) {
                DBObject dbo = cursor.next();
                max = (Long) dbo.get("ID");
            }

            BasicDBObject document = new BasicDBObject();
            document.put("ID", ++max);
            document.put("FIRST_NAME", citizen.getFirstName());
            document.put("LAST_NAME", citizen.getLastName());
            document.put("AGE", citizen.getAge());
            document.put("STREET_ID", StreetMyMongoImpl.getDBObjectOfStreet(db, id));
            collection.insert(document);
            citizen.setId(max);

        } else {

            BasicDBObject document = new BasicDBObject();
            document.put("ID", citizen.getId());
            document.put("FIRST_NAME", citizen.getFirstName());
            document.put("LAST_NAME", citizen.getLastName());
            document.put("AGE", citizen.getAge());
            document.put("STREET_ID", StreetMyMongoImpl.getDBObjectOfStreet(db, id));
            collection.insert(document);

        }

    }

    @Override
    public ArrayList<Citizen> Read() {

        ArrayList<Citizen> result = new ArrayList();

        DBCollection collection = db.getCollection("CITIZEN");

        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            DBObject streetIDDBO = (DBObject) dbo.get("STREET_ID");
            Long streetID = (streetIDDBO == null ? null : (Long) streetIDDBO.get("ID"));
            result.add(new Citizen((Long) dbo.get("ID"), (String) dbo.get("FIRST_NAME"), (String) dbo.get("LAST_NAME"), (int) dbo.get("AGE"), streetID)); //
        }

        return result;

    }

    @Override
    public void Update(Citizen citizen) {

        DBCollection collection = db.getCollection("CITIZEN");

        BasicDBObject document = new BasicDBObject();
        document.put("ID", citizen.getId());
        document.put("FIRST_NAME", citizen.getFirstName());
        document.put("LAST_NAME", citizen.getLastName());
        document.put("AGE", citizen.getAge());
        document.put("STREET_ID", StreetMyMongoImpl.getDBObjectOfStreet(db, citizen.getStreetId()));

        collection.findAndModify(new BasicDBObject("ID", citizen.getId()), document);

    }

    @Override
    public void Delete(Citizen citizen) {

        DBCollection collection = db.getCollection("CITIZEN");
        collection.remove(new BasicDBObject("ID", citizen.getId()));

    }

    @Override
    public Citizen getOneById(long id) {

        DBCollection collection = db.getCollection("CITIZEN");
        DBObject dbo = collection.findOne(new BasicDBObject("ID", id));
        if (dbo == null) {
            return null;
        } else {
            return new Citizen((Long) dbo.get("ID"), (String) dbo.get("FIRST_NAME"), (String) dbo.get("LAST_NAME"), (int) dbo.get("AGE"), (Long) ((DBObject) dbo.get("STREET_ID")).get("ID"));
        }

    }

    @Override
    public void checkMetaData() {

        DB db = mongo.getDB("ADDRESS_BOOK");
        DBCollection collection = db.getCollection("STREET");

        DBCursor cursor = collection.find().sort(new BasicDBObject("ID", -1)).limit(1);

        Long max = 0L;
        if (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            max = (Long) dbo.get("ID");
        }

        if (max.equals(0L)) { // Do it only one time

            DB dbdel = mongo.getDB("ADDRESS_BOOK");
            dbdel.dropDatabase();

            db = mongo.getDB("ADDRESS_BOOK");
            collection = db.getCollection("STREET");

            List<Street> streets = new LinkedList();
            Collections.addAll(streets, new Street(1L, "MONGO Мониторная 7"), new Street(2L, "Миргородская 2"), new Street(3L, "Огородная 3"), new Street(4L, "Осенняя 5"), new Street(5L, "Альпийский переулок 2"));
            for (Street street : streets) {
                MongoTestDataCreator.insertStreet(collection, street);
            }

            List<Citizen> people = new LinkedList();
            people.add(new Citizen(1L, "MONGO ALEXANDR", "PIRIN", 25, 4L));  // Осенняя 5
            people.add(new Citizen(2L, "GALINA", "KUIDINA", 36, 1L));  // Мониторная 7
            people.add(new Citizen(3L, "ELENA", "ZHIDOVLENKOVA", 45, 1L));  // Мониторная 7
            people.add(new Citizen(4L, "JULIA", "KOLOMOEC", 27, 1L));  // Мониторная 7
            people.add(new Citizen(5L, "TATYANA", "BIBER", 36, 2L));  // Миргородская 2
            people.add(new Citizen(6L, "VLADIMIR", "KUPREEV", 50, 3L));  // Огородная 3
            people.add(new Citizen(7L, "ERNEST", "SPRIVETOV", 20));   // null
            people.add(new Citizen(8L, "EDGAR", "HACHAPUROV", 40));  // null
            people.add(new Citizen(9L, "VAHTANG", "TUPOV", 50));  // null
            people.add(new Citizen(10L, "VITALIY", "SERDUK", 35, 5L));  // Альпийский переулок 2

            for (Citizen person : people) {
                MongoTestDataCreator.insertCitizen(db, person);
            }

        }

    }

    @Override
    public void closeConnection() {
        this.db = null;
        this.mongo.close();
    }

    @Override
    public void getConnection() {
        //return null;
    }

}
