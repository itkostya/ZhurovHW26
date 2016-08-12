package com.mycompany.zhurovhw26.table.view;

import java.awt.Dimension;
import javax.swing.*;
import com.mycompany.zhurovhw26.dao.DatasourceDAO;

public class ToolPanel extends JPanel {

    private TablePanel workingPanel;    

    public ToolPanel(TablePanel workingPanel) {

        this.workingPanel = workingPanel;
        
        // new begin
        setSize(new Dimension(200, 400));
        setVisible(true);
        //setLayout(null);
        //setBounds(0, 0, 600, 400);
        // new end

        ButtonGroup bg = new ButtonGroup();
        JToggleButton btnConnect = new JToggleButton("Connect");
        JToggleButton btnDisConnect = new JToggleButton("Disconnect");

        JComboBox cmbChangeDB = new JComboBox(DatasourceDAO.values());

        JButton btnCreate = new JButton("Create");
        JButton btnRead = new JButton("Read");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        bg.add(btnConnect);
        bg.add(btnDisConnect);
        add(cmbChangeDB);
        add(btnConnect);
        add(btnDisConnect);
        add(btnCreate);
        add(btnRead);
        add(btnUpdate);
        add(btnDelete);

        btnConnect.setBounds(25, 0 + 310, 125, 20);
        btnDisConnect.setBounds(25, 30 + 310, 125, 20);
        cmbChangeDB.setBounds(160, 0 + 310, 80, 20);
        btnCreate.setBounds(80 * 0 + 250, 310, 75, 50);
        btnRead.setBounds(80 * 1 + 250, 310, 75, 50);
        btnUpdate.setBounds(80 * 2 + 250, 310, 75, 50);
        btnDelete.setBounds(80 * 3 + 250, 310, 75, 50);

        btnConnect.addActionListener(workingPanel.connectListener());
        btnDisConnect.addActionListener(workingPanel.disconnectListener());
        cmbChangeDB.addActionListener(workingPanel.changeDBListener());
        btnCreate.addActionListener(workingPanel.createListener());
        btnRead.addActionListener(workingPanel.readListener());
        btnUpdate.addActionListener(workingPanel.updateListener());
        btnDelete.addActionListener(workingPanel.deleteListener());
    }

}
