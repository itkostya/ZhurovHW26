/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.datafileimpl.json;

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
 * Implementation for JSON Data file with table Citizen
 * 
 */
public class CitizenJsonDAOImpl extends JsonDAO<Citizen> {

    public static String HEADER_JSON = "{\"citizenList\":[";
    public static String TAIL_JSON = "]}";

    public CitizenJsonDAOImpl() {
        super(Attribute.getAttribute("dataJsonFileCitizen"));
    }

    public CitizenJsonDAOImpl(String fileName) {
        super(fileName);
    }

    @Override
    public void Create(Citizen citizen) {

        int i = 0;
        Long id = citizen.getId();
        try {
            if ((id == null) || (id == 0L)) {
                citizen.setId(nextEntireId());
            }

            if (getDataFile().length() < (HEADER_JSON.length() + TAIL_JSON.length())) {
                getDataFile().write((HEADER_JSON + "\n").getBytes());
            } else {
                getDataFile().seek(getDataFile().length() - (TAIL_JSON.length()));  // curson at the end of the file.
                getDataFile().write(",".getBytes());
            }
            getDataFile().write(viewEntity(citizen).getBytes());
            getDataFile().write(TAIL_JSON.getBytes());

        } catch (IOException ex) {
            Logger.getLogger(CitizenJsonDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ArrayList<Citizen> Read() {

        ArrayList<Citizen> result = new ArrayList();

        try {

            getDataFile().seek(0);
            String str;
            Citizen citizen = null;

            // read lines till the end of the stream
            while ((str = getDataFile().readLine()) != null) {

                str = str.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "").replaceAll(",", "").replaceAll(":", "");

                if (str.contains("\"citizen\"")) {
                    citizen = new Citizen();
                } else if (str.contains("\"id\"")) {
                    str = str.replace("\"id\"", "");
                    citizen.setId(Long.valueOf(str));
                } else if (str.contains("\"firstName\"")) {
                    str = str.replace("\"firstName\"", "").replaceAll("\"", "");                   
                    citizen.setFirstName(str);
                } else if (str.contains("\"lastName\"")) {
                    str = str.replace("\"lastName\"", "").replaceAll("\"", "");;
                    citizen.setLastName(str);
                } else if (str.contains("\"age\"")) {
                    str = str.replace("\"age\"", "");
                    citizen.setAge(Integer.parseInt(str));
                } else if (str.contains("\"street_id\"")) {
                    str = str.replace("\"street_id\"", "");
                    citizen.setStreetId(str.equals("null") ? null : Long.valueOf(str));
                    result.add(citizen);
                }
            }
        } catch (IOException e) {
            System.out.println("Error get info from file JSON (Citizen)");
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

        return String.format("{\n"
                + "\"citizen\":[\n"
                + "{\n"
                + "\"id\": %s,\n"
                + "\"firstName\": \"%s\",\n"
                + "\"lastName\": \"%s\",\n"
                + "\"age\": %s,\n"
                + "\"street_id\": %s\n"
                + "}]}\n", citizen.getId(), citizen.getFirstName(), citizen.getLastName(), citizen.getAge(), citizen.getStreetId());
    }

    @Override
    public final boolean findMatchingInFile(Citizen citizen) {

        String str;

        try {
            getDataFile().seek(0);
            while (((str = getDataFile().readLine()) != null)) {

                str = str.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "").replaceAll(",", "").replaceAll(":", "");
                if (str.contains("\"id\"")) {
                    str = str.replace("\"id\"", "");
                    if (citizen.getId().equals(Long.valueOf(str))) {
                        return true;
                    }
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
            String strEntire = "";
            Long currentId = 0L;

            while ((str = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                              
                String strCheck = str.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "").replaceAll(",", "").replaceAll(":", "");

                if (strCheck.contains("\"citizen\"")) {                    
                    strEntire += str + "\n";
                } else if (strCheck.contains("}]}")) {                    
                    strEntire += str + "\n";
                    if (citizen.getId().equals(currentId)) {
                        if (!replace.equals("")) 
                        {
                            if (strEntire.substring(0,1).equals(","))  // First symbol in checking string
                                writer.write(",");
                        }    
                        writer.write(replace);
                    } else {
                        writer.write(strEntire);
                    };
                    strEntire = "";
                } else if (strCheck.contains("\"id\"")) {
                    strCheck = strCheck.replace("\"id\"", "");
                    currentId = Long.valueOf(strCheck);
                    strEntire += str + "\n";
                } else if (str.contentEquals(HEADER_JSON)) {
                    writer.write(str + "\n");
                    strEntire = "";
                } else if (str.contentEquals(TAIL_JSON)) {
                    writer.write(str);
                } else {
                    strEntire += str + "\n";
                }
            }
            
            if (!strEntire.equals("")) {
                writer.write(strEntire);
            }

            writer.close();
            reader.close();

            boolean successful = tempFile.renameTo(getFile()); // It's not work :( 
            // so I should do smth else
            if (!successful) {
                // in file writer - all necessary data                
                clearDataInFile(inputFile);
                getDataFile().seek(0);
                reader = new BufferedReader(new FileReader(tempFile));
                while ((str = reader.readLine()) != null) {                    
                    if (str.contentEquals(TAIL_JSON)) {
                        getDataFile().write(str.getBytes());
                    } else {
                        getDataFile().write((str + "\r\n").getBytes());
                    }
                };
                reader.close();
            }

            tempFile.delete();

        } catch (IOException ex) {
            Logger.getLogger(CitizenJsonDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void checkMetaData() {

        clearDataInFile(getFile());

        Long i = 0L;
        Create(new Citizen(++i, "JSON_ALEXANDR", "JSON_PIRIN", 25, 4L));
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
    public Long getId(Citizen citizen) {
        return citizen.getId();
    }

}
