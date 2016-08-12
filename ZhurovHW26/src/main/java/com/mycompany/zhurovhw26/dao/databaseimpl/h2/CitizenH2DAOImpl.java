/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.databaseimpl.h2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.zhurovhw26.entity.Citizen;

/**
 *
 * Implementation for H2 Database with table Citizen
 * 
 */

public class CitizenH2DAOImpl extends H2DAO<Citizen> {
    
    private final static String CREATE_CITIZEN_QUERY
            = "INSERT INTO CITIZEN (FIRST_NAME, LAST_NAME, AGE, STREET_ID) VALUES (?, ?, ?, ?)";
    private final static String CREATE_CITIZEN_QUERY_WITH_ID
            = "INSERT INTO CITIZEN (ID, FIRST_NAME, LAST_NAME, AGE, STREET_ID) VALUES (?, ?, ?, ?, ?)";
    private final static String SELECT_CITIZEN_QUERY = "SELECT * FROM CITIZEN";
    private final static String SELECT_CITIZEN_QUERY_ONE_PERSON = "SELECT ID, FIRST_NAME, LAST_NAME, AGE, STREET_ID FROM CITIZEN WHERE ID = ?";
    private final static String UPDATE_CITIZEN_QUERY
            = "UPDATE CITIZEN SET FIRST_NAME = ?, LAST_NAME = ?, AGE = ?, STREET_ID =? WHERE ID = ?";
    private final static String DELETE_CITIZEN_QUERY
            = "DELETE FROM CITIZEN WHERE ID = ?";
    private final static String CREATE_METADATA = "CREATE TABLE IF NOT EXISTS STREET (\n"
            + "    ID INT NOT NULL AUTO_INCREMENT,\n"
            + "    STREET_NAME VARCHAR(255),\n"
            + "    PRIMARY KEY (ID)\n"
            + "); -- should be collate cause I catch bug with coding"
            + "USE ADDRESS_BOOK;\n"
            + "CREATE TABLE IF NOT EXISTS CITIZEN (\n"
            + "ID INT NOT NULL AUTO_INCREMENT,\n"
            + "FIRST_NAME VARCHAR(255) NULL,\n"
            + "LAST_NAME VARCHAR(255) NULL,\n"
            + "AGE INT NULL,\n"
            + "STREET_ID INT NULL,\n"
            + "PRIMARY KEY (ID),\n"
            + "FOREIGN KEY (STREET_ID)\n"
            + "    REFERENCES STREET (ID)\n"
            + "); \n"
            + " INSERT INTO STREET (STREET_NAME)\n"
            + " VALUES ('H2 Мониторная 7'),\n"
            + " ('Миргородская 2'),\n"
            + " ('Огородная 3'),\n"
            + " ('Осенняя 5'),\n"
            + " ('Альпийский переулок 2');"
            + " INSERT INTO CITIZEN (FIRST_NAME, LAST_NAME, AGE, STREET_ID)\n"
            + " VALUES\n"
            + " ('ALEXANDR', 'PIRIN', 25, (select id from street where street_name = 'Осенняя 5')),\n"
            + " ('GALINA', 'KUIDINA',  36, (select id from street where street_name = 'H2 Мониторная 7')),\n"
            + " ('ELENA', 'ZHIDOVLENKOVA',  45, (select id from street where street_name = 'H2 Мониторная 7')),\n"
            + " ('JULIA', 'KOLOMOEC', 27, (select id from street where street_name = 'H2 Мониторная 7')),\n"
            + " ('TATYANA', 'BIBER', 36, (select id from street where street_name = 'Миргородская 2')),\n"
            + " ('VLADIMIR', 'KUPREEV', 50, (select id from street where street_name = 'Огородная 3')),\n"
            + " ('ERNEST', 'SPRIVETOV', 20, NULL),\n"
            + " ('EDGAR', 'HACHAPUROV', 40, NULL),\n"
            + " ('VAHTANG', 'TUPOV', 50, NULL),\n"
            + " ('VITALIY', 'SERDUK', 35, (select id from street where street_name = 'Альпийский переулок 2'));";

    public CitizenH2DAOImpl() {
        super();
    }

    public CitizenH2DAOImpl(String url, String user, String pass, String driver) {
        super(url, user, pass, driver);
    }

    @Override
    public void Create(Citizen citizen) {

        int i = 0;
        Long id = citizen.getId();

        try {

            if ((id == null) || (id == 0L)) {

                PreparedStatement statement = super.getPreparedStatement(CREATE_CITIZEN_QUERY, Statement.RETURN_GENERATED_KEYS);
                statement.setString(++i, citizen.getFirstName());
                statement.setString(++i, citizen.getLastName());
                statement.setInt(++i, citizen.getAge());
                statement.setObject(++i, citizen.getStreetId());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    citizen.setId(rs.getLong(1));
                }

            } else {

                PreparedStatement statement = super.getPreparedStatement(CREATE_CITIZEN_QUERY_WITH_ID);
                statement.setLong(++i, citizen.getId());
                statement.setString(++i, citizen.getFirstName());
                statement.setString(++i, citizen.getLastName());
                statement.setInt(++i, citizen.getAge());
                statement.setObject(++i, citizen.getStreetId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public ArrayList<Citizen> Read() {

        ArrayList<Citizen> result = new ArrayList();

        try {
            // Get data from DB
            PreparedStatement statement = super.getPreparedStatement(SELECT_CITIZEN_QUERY);
            ResultSet rs = statement.executeQuery();

            // If we get smth - it will work
            while (rs.next()) {
                result.add(new Citizen(rs.getLong("ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getInt("AGE"), rs.getLong("STREET_ID")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;

    }

    @Override
    public void Update(Citizen citizen) {

        int i = 0;

        try {
            PreparedStatement statement = super.getPreparedStatement(UPDATE_CITIZEN_QUERY);
            statement.setString(++i, citizen.getFirstName());
            statement.setString(++i, citizen.getLastName());
            statement.setInt(++i, citizen.getAge());
            statement.setObject(++i, citizen.getStreetId());
            statement.setLong(++i, citizen.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void Delete(Citizen citizen) {

        try {
            PreparedStatement statement = super.getPreparedStatement(DELETE_CITIZEN_QUERY);
            statement.setLong(1, citizen.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Citizen getOneById(long id) {

        try {
            PreparedStatement statement = super.getPreparedStatement(SELECT_CITIZEN_QUERY_ONE_PERSON);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Citizen(rs.getLong("ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getInt("AGE"), rs.getLong("STREET_ID"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void checkMetaData() {

        try {
            Statement statement = super.getStatement();

            ResultSet rs = super.connection.getMetaData().getTables("", null, "CITIZEN", null);

            if (!rs.next()) {
                statement.execute(CREATE_METADATA);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CitizenH2DAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
