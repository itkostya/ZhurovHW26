/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.datafileimpl.xml;

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
 * Implementation for XML Data file with table Street
 * 
 */
public class StreetXmlDAOImpl extends XmlDAO<Street> {

    public static String HEADER_XML = "<streetList>";
    public static String TAIL_XML = "</streetList>";

    public StreetXmlDAOImpl() {
        super(Attribute.getAttribute("dataXmlFileStreet"));
    }

    public StreetXmlDAOImpl(String fileName) {
        super(fileName);
    }

    @Override
    public void Create(Street street) {

        int i = 0;
        Long id = street.getId();
        try {
            if ((id == null)||(id == 0L)) {
                street.setId(nextEntireId());                
            }                        

            if (getDataFile().length() < (HEADER_XML.length() + TAIL_XML.length())) {
                getDataFile().write((HEADER_XML+"\n").getBytes());
            } else {
                getDataFile().seek(getDataFile().length() - TAIL_XML.length());  // curson at the end of the file
            }
            getDataFile().write(viewEntity(street).getBytes());
            getDataFile().write(TAIL_XML.getBytes());

        } catch (IOException ex) {
            Logger.getLogger(StreetXmlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ArrayList<Street> Read() {

        ArrayList<Street> result = new ArrayList();

        try {

            getDataFile().seek(0);
            String str;
            Street street = null;

            // read lines till the end of the stream
            while ((str = getDataFile().readLine()) != null) {

                str = str.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");

                if (str.contains("<street>")) {
                    street = new Street();
                } else if (str.contains("<id>")) {
                    str = str.replace("<id>", "").replace("</id>", "");
                    street.setId(Long.valueOf(str));
                } else if (str.contains("<streetName>")) {
                    str = str.replace("<streetName>", "").replace("</streetName>", "");
                    street.setStreetName(str);                
                } else if (str.contains("</street>")) {
                    result.add(street);
                }

            }
        } catch (IOException e) {
            System.out.println("Error get info from file XML (Street)");
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

        return String.format("\t<street>\n"
                + "\t\t<id>%s</id>\n"
                + "\t\t<streetName>%s</streetName>\n"                
                + "\t</street>\n", street.getId(), street.getStreetName());
    }

    @Override
    public final boolean findMatchingInFile(Street street) {

        String str;

        try {
            getDataFile().seek(0);
            while (((str = getDataFile().readLine()) != null)) {
                str = str.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");
                if (str.contains("<id>")) {
                    str = str.replace("<id>", "").replace("</id>", "");
                    if (street.getId().equals(Long.valueOf(str))) {
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
    public final void changeEntiryFromFile(Street street, String replace) {

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
                String strCheck = str.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");

                if (strCheck.contains("<street>")) {
                    strEntire = "";                                        
                    strEntire += str+"\n";                
                } else if (strCheck.contains("</street>")) {                    
                    strEntire += str+"\n";  
                    if (street.getId().equals(currentId)) {
                        writer.write(replace);
                    } else {
                        writer.write(strEntire);
                    };
                    strEntire = "";
                } else if (strCheck.contains("<id>")) {
                    strCheck = strCheck.replace("<id>", "").replace("</id>", "");
                    currentId = Long.valueOf(strCheck);
                    strEntire += str+"\n";                
                } else if (HEADER_XML.contains(strCheck)) {
                    writer.write(str+"\n");
                    strEntire = "";
                } else if (strCheck.contains(TAIL_XML)) {
                    writer.write(str);
                } else
                    strEntire += str+"\n";   
            }

            writer.close();
            reader.close();

            boolean successful = tempFile.renameTo(getFile()); // It's not work :( 
            // so I should do smth else
            if (!successful) {                
                clearDataInFile(inputFile);
                getDataFile().seek(0);
                reader = new BufferedReader(new FileReader(tempFile));
                while ((str = reader.readLine()) != null) {
                    if (str.contains(TAIL_XML))
                        getDataFile().write(str.getBytes());                    
                    else                      
                        getDataFile().write((str + "\r\n").getBytes());                    
                };
                reader.close();
            }

            tempFile.delete();

        } catch (IOException ex) {
            Logger.getLogger(StreetXmlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void checkMetaData() {

        clearDataInFile(getFile());

        Long i = 0L;
        Create(new Street(++i, "XML_MONITORNAYA_7"));
        Create(new Street(++i, "MIRGORODSKAYA_2"));
        Create(new Street(++i, "OGORODNAYA_3"));
        Create(new Street(++i, "OSENNYAYA_5"));
        Create(new Street(++i, "ALPEN_ROW_2"));

    }

    @Override
    public Long getId(Street street) {
        return street.getId();
    }

}

