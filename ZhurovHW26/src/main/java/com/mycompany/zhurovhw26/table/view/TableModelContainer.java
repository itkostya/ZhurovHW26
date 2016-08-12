package com.mycompany.zhurovhw26.table.view;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class TableModelContainer<T> extends AbstractTableModel {

    private String[] columns;
    protected List<T> data = new ArrayList<>();
    private List<T> updated = new ArrayList<>();

    public TableModelContainer(String[] columns) {
        this.columns = columns;
    }

    public TableModelContainer(String[] columns, List<T> data) {
        this.columns = columns;
        this.data = data;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public abstract boolean isCellEditable(int rowIndex, int columnIndex);

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    @Override
    public abstract void setValueAt(Object aValue, int rowIndex, int columnIndex);

    public T getSelectedRowData(int row) {
        return data.get(row);
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public List<T> getUpdated() {
        return updated;
    }

    public void clearUpdated(){
        updated = new ArrayList<>();
    }
}
