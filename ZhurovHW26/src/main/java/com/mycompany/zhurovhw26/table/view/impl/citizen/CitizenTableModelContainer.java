package com.mycompany.zhurovhw26.table.view.impl.citizen;

import java.util.List;

import com.mycompany.zhurovhw26.entity.Citizen;
import com.mycompany.zhurovhw26.table.view.TableModelContainer;

public class CitizenTableModelContainer extends TableModelContainer<Citizen> {

    public CitizenTableModelContainer() {
        super(new String[]{"ID", "First name", "Last Name", "Age", "Street ID"});
    }

    public CitizenTableModelContainer(List<Citizen> data) {
        super(new String[]{"ID", "First name", "Last Name", "Age", "Street ID"}, data);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Citizen citizen = data.get(rowIndex);
        switch (columnIndex) {
            case 1:
                return citizen.getFirstName();
            case 2:
                return citizen.getLastName();
            case 3:
                return citizen.getAge();
            case 4:
                return citizen.getStreetId();
            default:
                return citizen.getId();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Citizen citizen = data.get(rowIndex);
        String updatedValue = String.valueOf(aValue);
        switch (columnIndex) {
            case 1:
                citizen.setFirstName(updatedValue);
                break;
            case 2:
                citizen.setLastName(updatedValue);
                break;
            case 3:
                citizen.setAge(Integer.valueOf(updatedValue));
                break;
            case 4:
                citizen.setStreetId(Long.valueOf(updatedValue));
                break;
        }
        getUpdated().add(citizen);
    }
}
