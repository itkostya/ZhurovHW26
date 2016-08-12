package com.mycompany.zhurovhw26.table.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.zhurovhw26.dao.abstracts.AbstractDAO;
import com.mycompany.zhurovhw26.dao.abstracts.DAO;
import com.mycompany.zhurovhw26.dao.DatasourceDAO;

public abstract class TablePanel<T> extends JPanel {

    private final JTable table;
    private final Dialog<T> dialog;
    private final TableModelContainer<T> tableContainer;
    private DAO<T> dao;
    private DatasourceDAO currentDB;
    private boolean connectionExists;

    public DAO<T> getDao() {
        return dao;
    }

    public void setDao(DAO<T> dao) {
        this.dao = dao;
    }

    public DatasourceDAO getCurrentDB() {
        return currentDB;
    }

    public void setCurrentDB(DatasourceDAO currentDB) {
        this.currentDB = currentDB;
    }

    public boolean isConnectionExists() {
        return connectionExists;
    }

    public void setConnectionExists(boolean connectionExists) {
        this.connectionExists = connectionExists;
    }

    public TablePanel(TableModelContainer<T> tableContainer, DAO<T> dao, Dialog<T> dialog) {
        this.tableContainer = tableContainer;
        this.table = new JTable(tableContainer);
        this.dao = dao;
        this.dialog = dialog;
        this.currentDB = DatasourceDAO.MySQL;
        this.connectionExists = false;
        init();
    }

    private void init() {
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        setBounds(0, 0, 595, 300);
    }

    public void refreshDataView(List<T> data) {
        tableContainer.setData(data);
        tableContainer.clearUpdated();
        table.updateUI();
    }

    public T getSelectedRowData(int row) {
        return tableContainer.getSelectedRowData(row);
    }

    public void closeConnection() {

        if (connectionExists) {
            dao.closeConnection();
            dao = null; 
            connectionExists = false;
            refreshDataView(new ArrayList<T>());
        }

    }

    public ActionListener createListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
                if (dialog.isOkPressed()) {
                    dao.Create(dialog.get());
                    List<T> data = dao.Read();
                    refreshDataView(data);
                }
            }
        };
    }

    public ActionListener readListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<T> data = dao.Read();
                refreshDataView(data);
            }
        };
    }

    public ActionListener updateListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                for (T t : tableContainer.getUpdated()) {
                    dao.Update(t);
                }
                tableContainer.clearUpdated();
                List<T> data = dao.Read();
                refreshDataView(data);
            }
        };
    }

    public ActionListener deleteListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                T t = tableContainer.getSelectedRowData(table.getSelectedRow());
                dao.Delete(t);
                List<T> data = tableContainer.getData();
                data.remove(t);
                refreshDataView(data);
            }
        };

    }

    public ActionListener changeDBListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox) e.getSource();
                DatasourceDAO item = (DatasourceDAO) box.getSelectedItem();

                if (isConnectionExists()) {
                    dao.closeConnection();  // getDao().closeConnection(); - ?
                }    
                setCurrentDB(item);
                if (isConnectionExists()) {
                    changeDBAndData();
                }    
            }
        };
    }

    public ActionListener connectListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDBAndData();
            }
        };
    }

    public ActionListener disconnectListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeConnection();
            }
        };
    }

    public abstract AbstractDAO<T> getCurrentImpl(DatasourceDAO dao);

    public abstract void changeDBAndData();

}
