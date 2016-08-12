package com.mycompany.zhurovhw26.table.view.impl.citizen;

import com.mycompany.zhurovhw26.entity.Citizen;
import com.mycompany.zhurovhw26.table.view.Dialog;

import java.awt.*;

public class CreateCitizenDialog extends Dialog<Citizen> {

    private TextField firstName;
    private TextField lastName;
    private TextField age;
    private TextField streetId;

    public CreateCitizenDialog() {
        super();
        initComponents();
    }

    private void initComponents() {
        initLabels();
        initTextFields();      
    }

    private void initLabels() {
        Label firstNameLabel = new Label("First name:");
        Label lastNameLabel = new Label("Last name:");
        Label ageLabel = new Label("Age:");
        Label streetIdLabel = new Label("Street ID:");

        firstNameLabel.setBounds(30, 0, 65, 25);
        lastNameLabel.setBounds(30, 35, 65, 25);
        ageLabel.setBounds(30, 70, 65, 25);
        streetIdLabel.setBounds(30, 105, 65, 25);

        panel.add(firstNameLabel);
        panel.add(lastNameLabel);
        panel.add(ageLabel);
        panel.add(streetIdLabel);
    }

    private void initTextFields() {
        this.firstName = new TextField();
        this.lastName = new TextField();
        this.age = new TextField();
        this.streetId = new TextField();

        firstName.setBounds(110, 0, 100, 25);
        lastName.setBounds(110, 35, 100, 25);
        age.setBounds(110, 70, 100, 25);
        streetId.setBounds(110, 105, 100, 25);

        panel.add(firstName);
        panel.add(lastName);
        panel.add(age);
        panel.add(streetId);
    }

    @Override
    public Citizen get() {
        Citizen citizen = getCitizen();
        clearFields();
        return citizen;
    }

    private Citizen getCitizen() {
        String streetIdValue = streetId.getText();
        Long streetIdNumberValue = (streetIdValue.length() == 0 || streetIdValue.equals("0")) ? null : Long.valueOf(streetIdValue);
        return new Citizen(
                null,
                firstName.getText(),
                lastName.getText(),
                Integer.valueOf(age.getText()),
                streetIdNumberValue
        );
    }

    private void clearFields() {
        firstName.setText("");
        lastName.setText("");
        age.setText("");
        streetId.setText("");
    }
}
