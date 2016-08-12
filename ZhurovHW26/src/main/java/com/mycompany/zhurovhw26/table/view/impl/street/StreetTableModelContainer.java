package com.mycompany.zhurovhw26.table.view.impl.street;

import com.mycompany.zhurovhw26.entity.Street;
import com.mycompany.zhurovhw26.table.view.TableModelContainer;

import java.util.List;

public class StreetTableModelContainer extends TableModelContainer<Street> {

    public StreetTableModelContainer() {
        super(new String[]{"ID", "Street name"});
    }

    public StreetTableModelContainer(List<Street> data) {
        super(new String[]{"ID", "Street name"}, data);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Street street = data.get(rowIndex);
        switch (columnIndex) {
            case 1:
                return street.getStreetName();            
            default:
                return street.getId();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Street street = data.get(rowIndex);
        String updatedValue = String.valueOf(aValue);
        switch (columnIndex) {
            case 1:
                street.setStreetName(updatedValue);
                break;            
        }
        getUpdated().add(street);
    }
}
