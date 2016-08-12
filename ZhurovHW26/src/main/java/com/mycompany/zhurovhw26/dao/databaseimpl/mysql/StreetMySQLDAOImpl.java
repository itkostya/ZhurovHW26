/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.databaseimpl.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.zhurovhw26.dao.databaseimpl.h2.CitizenH2DAOImpl;
import com.mycompany.zhurovhw26.entity.Street;

/**
 *
 * Implementation for MySQL Database with table Street
 *
 */
public class StreetMySQLDAOImpl extends MySQLDAO<Street> {

    private final static String CREATE_STREET_QUERY
            = "INSERT INTO STREET (STREET_NAME) VALUES (?)";
    private final static String CREATE_STREET_QUERY_WITH_ID
            = "INSERT INTO STREET (ID, STREET_NAME) VALUES (?, ?)";
    private final static String SELECT_STREET_QUERY = "SELECT * FROM STREET";
    private final static String SELECT_STREET_QUERY_ONE_PERSON
            = "SELECT ID, STREET_ID FROM STREET WHERE ID = ?";
    private final static String UPDATE_STREET_QUERY
            = "UPDATE STREET SET STREET_NAME = ? WHERE ID = ?";
    private final static String DELETE_STREET_QUERY
            = "DELETE FROM STREET WHERE ID = ?";

    public StreetMySQLDAOImpl() {
        super();
    }

    public StreetMySQLDAOImpl(String url, String user, String pass, String driver) {
        super(url, user, pass, driver);
    }

    @Override
    public void Create(Street street) {

        int i = 0;
        Long id = street.getId();

        try {

            if ((id == null) || (id == 0L)) {

                PreparedStatement statement = super.getPreparedStatement(CREATE_STREET_QUERY, Statement.RETURN_GENERATED_KEYS);
                statement.setString(++i, street.getStreetName());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    street.setId(rs.getLong(1));
                }

            } else {

                PreparedStatement statement = super.getPreparedStatement(CREATE_STREET_QUERY_WITH_ID);
                statement.setLong(++i, id);
                statement.setString(++i, street.getStreetName());
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitizenMySQLDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ArrayList<Street> Read() {

        ArrayList<Street> result = new ArrayList();

        try {
            // Выбираем данные с БД
            PreparedStatement statement = super.getPreparedStatement(SELECT_STREET_QUERY);
            ResultSet rs = statement.executeQuery();

            // И если что то было получено то цикл while сработает   
            while (rs.next()) {
                result.add(new Street(rs.getLong("ID"), rs.getString("STREET_NAME")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return result;

    }

    @Override
    public void Update(Street street) {

        int i = 0;

        try {
            PreparedStatement statement = super.getPreparedStatement(UPDATE_STREET_QUERY);
            statement.setString(++i, street.getStreetName());
            statement.setLong(++i, street.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void Delete(Street street) {

        try {
            PreparedStatement statement = super.getPreparedStatement(DELETE_STREET_QUERY);
            statement.setLong(1, street.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Street getOneById(long id) {

        try {
            PreparedStatement statement = super.getPreparedStatement(SELECT_STREET_QUERY_ONE_PERSON);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Street(rs.getLong("ID"), rs.getString("STREET_NAME"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    @Override
    public void checkMetaData() {

        try {
            Statement statement = super.getStatement();
            ResultSet rs = super.connection.getMetaData().getTables("ADDRESS_BOOK", null, "CITIZEN", null);

            if (!rs.next()) {

                statement.execute("CREATE SCHEMA IF NOT EXISTS ADDRESS_BOOK;\n");
                statement.execute("USE ADDRESS_BOOK;\n");
                statement.execute("CREATE TABLE IF NOT EXISTS STREET (\n"
                        + "    ID INT NOT NULL AUTO_INCREMENT,\n"
                        + "    STREET_NAME VARCHAR(255),\n"
                        + "    PRIMARY KEY (ID)\n"
                        + ") COLLATE='utf8_general_ci'; -- should be collate cause I catch bug with coding");
                statement.execute("USE ADDRESS_BOOK;\n");
                statement.execute("CREATE TABLE IF NOT EXISTS CITIZEN (\n"
                        + "ID INT NOT NULL AUTO_INCREMENT,\n"
                        + "FIRST_NAME VARCHAR(255) NULL,\n"
                        + "LAST_NAME VARCHAR(255) NULL,\n"
                        + "AGE INT NULL,\n"
                        + "STREET_ID INT NULL,\n"
                        + "PRIMARY KEY (ID),\n"
                        + "FOREIGN KEY (STREET_ID) "
                        + "REFERENCES STREET (ID)) COLLATE='utf8_general_ci';");
                statement.execute("USE ADDRESS_BOOK;\n");
                statement.execute("INSERT INTO STREET (STREET_NAME)\n"
                        + " VALUES ('My SQL Мониторная 7'),\n"
                        + " ('Миргородская 2'),\n"
                        + " ('Огородная 3'),\n"
                        + " ('Осенняя 5'),\n"
                        + " ('Альпийский переулок 2');");
                statement.execute("USE ADDRESS_BOOK;\n");
                statement.execute("INSERT INTO CITIZEN (FIRST_NAME, LAST_NAME, AGE, STREET_ID)\n"
                        + " VALUES\n"
                        + " ('ALEXANDR', 'PIRIN', 25, (select id from street where street_name = 'Осенняя 5')),\n"
                        + " ('GALINA', 'KUIDINA',  36, (select id from street where street_name = 'My SQL Мониторная 7')),\n"
                        + " ('ELENA', 'ZHIDOVLENKOVA',  45, (select id from street where street_name = 'My SQL Мониторная 7')),\n"
                        + " ('JULIA', 'KOLOMOEC', 27, (select id from street where street_name = 'My SQL Мониторная 7')),\n"
                        + " ('TATYANA', 'BIBER', 36, (select id from street where street_name = 'Миргородская 2')),\n"
                        + " ('VLADIMIR', 'KUPREEV', 50, (select id from street where street_name = 'Огородная 3')),\n"
                        + " ('ERNEST', 'SPRIVETOV', 20, NULL),\n"
                        + " ('EDGAR', 'HACHAPUROV', 40, NULL),\n"
                        + " ('VAHTANG', 'TUPOV', 50, NULL),\n"
                        + " ('VITALIY', 'SERDUK', 35, (select id from street where street_name = 'Альпийский переулок 2'));");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitizenH2DAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
