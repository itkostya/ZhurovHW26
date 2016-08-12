package com.mycompany.zhurovhw26.table.view;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import com.mycompany.zhurovhw26.table.view.impl.citizen.CitizenTablePanel;
import com.mycompany.zhurovhw26.table.view.impl.street.StreetTablePanel;

public class Application {
   
    public static void main(String[] args) {
        
        JFrame frame = new JFrame();

        Container container = frame.getContentPane();

        TabbedPane tabbedPane = new TabbedPane();
        tabbedPane.setContainer(container);
        tabbedPane.addPanel("Street Tab", new StreetTablePanel());
        tabbedPane.addPanel("Citizen Tab", new CitizenTablePanel());
        
        tabbedPane.addChangeListener(tabbedPane.myListener());        
        container.add(tabbedPane, BorderLayout.CENTER);
                
        tabbedPane.createToolPanel(0);        

        frame.setVisible(true);
        frame.setBounds(0, 0, 600, 600);  // 0, 0, 400, 600
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          
    }
}
