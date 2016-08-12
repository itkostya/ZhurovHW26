/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.dao.abstracts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Abstract class working with different text files: CSV, XML, JSON
 *
 */
public abstract class AbstractDAOTextFile<T> extends AbstractDAO<T> {

    public static void createFileIfNotExist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private final String fileName;
    private File file = null;
    private volatile RandomAccessFile dataFile;

    public AbstractDAOTextFile(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public RandomAccessFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(RandomAccessFile dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    public void getConnection() {

        if (file == null) {
            synchronized (this) {
                try {
                    createFileIfNotExist(fileName);
                    file = new File(fileName);
                    dataFile = new RandomAccessFile(file, "rw");
                } catch (IOException ex) {
                    Logger.getLogger(AbstractDAOTextFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void closeConnection() {

        try {
            dataFile.close();
            file = null;  // file.delete();  // ???
        } catch (IOException ex) {
            Logger.getLogger(AbstractDAOTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Every text file has it's own view - we need performance of this view
    public abstract String viewEntity(T t);

    // Criteria for change file. For delete/update
    public abstract boolean findMatchingInFile(T t);

    // What we should do if we want to change the file
    public abstract void changeEntiryFromFile(T t, String replace);  // For XML, JSON - ???

    // For autoincrement
    public abstract Long getId(T t);

    // For autoincrement
    public Long nextEntireId() {

        ArrayList<T> arrayList = Read();

        Long maxId = -1L;
        for (T t : arrayList) {
            maxId = Long.max(getId(t), maxId);
        }

        return ++maxId;
    }

    @Override
    public void checkMetaData() {
        clearDataInFile(file);
    }

    private static void createFileIfNotExist(String fileName) throws IOException {
        File file = new File(fileName);
        createFileIfNotExist(file);
    }

    private static void createFileIfNotExist(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    // Support autoincrement
    public void clearDataInFile(File fileToClear) {

        // constructor with one parameter - for rewrite
        // create buffered stream
        try (FileWriter fstream1 = new FileWriter(fileToClear); BufferedWriter out1 = new BufferedWriter(fstream1)) {
            out1.write(""); // rewrite with blank string
        } catch (IOException ex) {
            Logger.getLogger(AbstractDAOTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
