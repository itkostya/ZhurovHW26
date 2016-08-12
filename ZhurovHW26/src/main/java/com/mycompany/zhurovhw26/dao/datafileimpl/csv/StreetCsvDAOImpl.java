/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.datafileimpl.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.zhurovhw26.entity.Street;
import com.mycompany.zhurovhw26.prop.Attribute;


/**
 *
 * Implementation for CSV Data file with table Street
 * 
 */
public class StreetCsvDAOImpl extends CsvDAO<Street> {

    public StreetCsvDAOImpl() {        
        super(Attribute.getAttribute("dataCsvFileStreet"));        
    }
    
    

    public StreetCsvDAOImpl(String fileName) {
        super(fileName);
    }

    @Override
    public void Create(Street street) {

        int i = 0;
        Long id = street.getId();
        try {
            if (id == null) {
                street.setId(nextEntireId());
            }

            getDataFile().seek(getDataFile().length());  // curson at the end of the file                        
            getDataFile().write(viewEntity(street).getBytes());

        } catch (IOException ex) {
            Logger.getLogger(CitizenCsvDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ArrayList<Street> Read() {

        ArrayList<Street> result = new ArrayList();

        try {

            getDataFile().seek(0);

            String str;

            // read lines till the end of the stream
            while ((str = getDataFile().readLine()) != null) {

                String[] strValue = str.split(";");

                //  Integer.valueOf(citizenValue[3]) - ??? if null
                result.add(new Street(Long.valueOf(strValue[0]), strValue[1]));

            }
        } catch (IOException e) {
            System.out.println("Error in get info from file");
        }

        return result;

    }

    @Override
    public void Update(Street street) {

        if (findMatchingInFile(street)) {
            changeEntiryFromFile(street, viewEntity(street));
        }

    }

    @Override
    public void Delete(Street street) {

        // read lines till the end of the stream - 
        // we should know if mathing exist
        if (findMatchingInFile(street)) {
            changeEntiryFromFile(street, "");
        }

    }

    @Override
    public Street getOneById(long id) {
        return null;
    }

    @Override
    public String viewEntity(Street street) {
        //return ""+street.getId()+";"+street.getStreetName()+"\r\n";
        return String.format("%s;%s\r\n", street.getId(), street.getStreetName());
    }

    @Override
    public final boolean findMatchingInFile(Street street) {

        String str;

        try {
            getDataFile().seek(0);
            while (((str = getDataFile().readLine()) != null)) {
                String[] strValue = str.split(";");
                if (street.getId().equals(Long.valueOf(strValue[0]))) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error in findMatchingInFile - get info from file");
        }

        return false;

    }

    @Override
    public final void changeEntiryFromFile(Street street, String replace) {

        File inputFile = getFile();
        File tempFile = new File(getFile().getAbsolutePath() + "_tmp");

        try {

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String str;

            while ((str = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String[] citizenValue = str.split(";");
                if (street.getId().equals(Long.valueOf(citizenValue[0]))) {
                    // nothing to do
                    writer.write(replace);
                } else {
                    writer.write(str + "\r\n");
                };
            }

            writer.close();
            reader.close();

            boolean successful = tempFile.renameTo(getFile()); // It's not work :( 
            // so I should do smth else
            if (!successful) {
                // in file writer - all necessary data
                //clearDataInFile(inputFile);
                clearDataInFile(inputFile);

                getDataFile().seek(0);
                reader = new BufferedReader(new FileReader(tempFile));
                while ((str = reader.readLine()) != null) {                    
                    getDataFile().write((str + "\r\n").getBytes());
                };
                reader.close();

            }

            tempFile.delete();

        } catch (IOException ex) {
            Logger.getLogger(CitizenCsvDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void checkMetaData() {

        clearDataInFile(getFile());

        Long i = 0L;
        Create(new Street(++i, "CSV MONITORNAYA 7"));
        Create(new Street(++i, "MIRGORODSKAYA 2"));
        Create(new Street(++i, "OGORODNAYA 3"));
        Create(new Street(++i, "OSENNYAYA 5"));
        Create(new Street(++i, "ALPEN ROW 2"));

    }

    @Override
    public Long getId(Street street) {
        return street.getId();
    }

}
