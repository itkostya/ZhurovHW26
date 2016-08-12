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
import com.mycompany.zhurovhw26.entity.Citizen;
import com.mycompany.zhurovhw26.prop.Attribute;

/**
 *
 * Implementation for CSV Data file with table Citizen
 * 
 */
public class CitizenCsvDAOImpl extends CsvDAO<Citizen> {

    public CitizenCsvDAOImpl() {        
        super(Attribute.getAttribute("dataCsvFileCitizen"));        
    }

    public CitizenCsvDAOImpl(String fileName) {
        super(fileName);
    }

    @Override
    public void Create(Citizen citizen) {

        int i = 0;
        Long id = citizen.getId();
        try {          
            if (id == null) {
                citizen.setId(nextEntireId());                
            }            
            
            getDataFile().seek(getDataFile().length());  // curson at the end of the file
            getDataFile().write(viewEntity(citizen).getBytes());
        } catch (IOException ex) {
            Logger.getLogger(CitizenCsvDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ArrayList<Citizen> Read() {

        ArrayList<Citizen> result = new ArrayList();

        try {

            getDataFile().seek(0);
            String str;

            // read lines till the end of the stream
            while ((str = getDataFile().readLine()) != null) {

                String[] strValue = str.split(";");
                
                result.add(new Citizen(Long.valueOf(strValue[0]), strValue[1], strValue[2], Integer.valueOf(strValue[3]), 
                        (strValue[4].equals("null") ? null : Long.valueOf(strValue[4])))
                );
            }
        } catch (IOException e) {
            System.out.println("Error in get info from file");
        }

        return result;

    }

    @Override
    public void Update(Citizen citizen) {

        if (findMatchingInFile(citizen)) {
            changeEntiryFromFile(citizen, viewEntity(citizen));
        }

    }

    @Override
    public void Delete(Citizen citizen) {

        // read lines till the end of the stream - 
        // we should know if mathing exist
        if (findMatchingInFile(citizen)) {
            changeEntiryFromFile(citizen, "");
        }

    }

    @Override
    public Citizen getOneById(long id) {
        return null;
    }

    @Override
    public String viewEntity(Citizen citizen) {
        return String.format("%s;%s;%s;%s;%s\r\n", citizen.getId(), citizen.getFirstName(), citizen.getLastName(), citizen.getAge(), citizen.getStreetId());
    }

    @Override
    public final boolean findMatchingInFile(Citizen citizen) {

        String str;

        try {
            getDataFile().seek(0);
            while (((str = getDataFile().readLine()) != null)) {
                String[] strValue = str.split(";");
                if (citizen.getId().equals(Long.valueOf(strValue[0]))) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error in findMatchingInFile - get info from file");
        }

        return false;

    }

    @Override
    public final void changeEntiryFromFile(Citizen citizen, String replace) {

        File inputFile = getFile();
        File tempFile = new File(getFile().getAbsolutePath() + "_tmp");

        try {

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String str;

            while ((str = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String[] citizenValue = str.split(";");
                if (citizen.getId().equals(Long.valueOf(citizenValue[0]))) {                    
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
        Create(new Citizen(++i, "CSV ALEXANDR", "CSV PIRIN", 25, 4L));
        Create(new Citizen(++i, "GALINA", "KUIDINA", 36, 1L));
        Create(new Citizen(++i, "ELENA", "ZHIDOVLENKOVA", 45, 1L));
        Create(new Citizen(++i, "JULIA", "KOLOMOEC", 27, 1L));
        Create(new Citizen(++i, "TATYANA", "BIBER", 36, 2L));
        Create(new Citizen(++i, "VLADIMIR", "KUPREEV", 50, 3L));
        Create(new Citizen(++i, "ERNEST", "SPRIVETOV", 20, null));
        Create(new Citizen(++i, "EDGAR", "HACHAPUROV", 40, null));
        Create(new Citizen(++i, "VAHTANG", "TUPOV", 50, null));
        Create(new Citizen(++i, "VITALIY", "SERDUK", 35, 5L));

    }
     
    @Override
    public Long getId(Citizen citizen){
        return citizen.getId();
    }
            
            
}
