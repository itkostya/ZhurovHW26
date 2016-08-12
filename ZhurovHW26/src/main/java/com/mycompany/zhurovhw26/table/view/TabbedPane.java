/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zhurovhw26.table.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.LinkedList;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author kostya
 */
public class TabbedPane extends JTabbedPane {
    
    public LinkedList<TablePanel> listTabelPanel = new LinkedList();
    private ToolPanel toolPanel;
    private Container container;
    private int currentPage = 0;
    
    public TabbedPane() {
    }
    
    public TabbedPane(ToolPanel toolPanel, Container container) {
        this.toolPanel = toolPanel;
        this.container = container;
    }

    public void createToolPanel(int numberInList) {
        this.toolPanel = new ToolPanel(listTabelPanel.get(numberInList));
        container.add(this.toolPanel, BorderLayout.PAGE_END);
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }      
    
    public void addPanel(String name, TablePanel tabelPanel){
       this.addTab(name, tabelPanel);
       listTabelPanel.add(tabelPanel);
    }

    public ChangeListener myListener() {        
        return new ChangeListener() {            
            @Override
            public void stateChanged(ChangeEvent arg0) {
                if (arg0.getSource() instanceof JTabbedPane) {
                    JTabbedPane p = (JTabbedPane) arg0.getSource();
                    int selectedPage = p.getSelectedIndex();                    
                    if (currentPage == selectedPage) {                        
                        // It's ok
                    } else {                 
                         // Cause I wanna close everything hard on current panel
                        listTabelPanel.get(currentPage).closeConnection();
                        container.remove(toolPanel); 
                        toolPanel = null;                        
                        createToolPanel(selectedPage);                        
                        currentPage = selectedPage;
                    }
                }

            }
        };

    }

}
