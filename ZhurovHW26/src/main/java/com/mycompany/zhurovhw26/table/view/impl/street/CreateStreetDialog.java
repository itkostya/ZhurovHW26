package com.mycompany.zhurovhw26.table.view.impl.street;

import com.mycompany.zhurovhw26.entity.Street;
import com.mycompany.zhurovhw26.table.view.Dialog;

import java.awt.*;

public class CreateStreetDialog extends Dialog<Street> {

    private TextField streetName;
    
    public CreateStreetDialog() {
        super();
        initComponents();
    }

    private void initComponents() {
        initLabels();
        initTextFields();
    }

    private void initLabels() {
        Label streetNameLabel = new Label("Street name:");
        
        streetNameLabel.setBounds(30, 0, 65, 25);
        
        panel.add(streetNameLabel);
        
    }

    private void initTextFields() {
        
        this.streetName = new TextField();        
        streetName.setBounds(110, 0, 100, 25);        
        panel.add(streetName);
        
    }

    @Override
    public Street get() {
        Street street = getStreet();
        clearFields();
        return street;
    }

    private Street getStreet() {        
        return new Street(
                null,
                streetName.getText()
        );
    }

    private void clearFields() {
        streetName.setText("");        
    }
}
