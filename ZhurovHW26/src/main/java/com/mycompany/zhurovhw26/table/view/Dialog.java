package com.mycompany.zhurovhw26.table.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Dialog<T> extends JDialog {

    private JButton ok = null;
    private JButton back = null;
    protected JPanel panel = null;
    private BtnListener listener = null;
    private boolean isOkPressed = false;

    public Dialog() {
        setModal(true);
        setLayout(null);
        setBounds(300, 400, 250, 240);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.listener = new BtnListener();
        this.panel = new JPanel();
        add(panel);
        panel.setBounds(0, 0, 250, 240);
        panel.setLayout(null);

        this.ok = new JButton("ok");
        this.back = new JButton("back");

        ok.addActionListener(listener);
        back.addActionListener(listener);

        ok.setBounds(30, 160, 80, 30);
        back.setBounds(130, 160, 80, 30);

        panel.add(ok);
        panel.add(back);
    }

    public abstract T get();

    private class BtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("ok"))
                isOkPressed = true;

            dispose();
        }
    }

    public boolean isOkPressed() {
        boolean res = isOkPressed;
        isOkPressed = false;
        return res;
    }
}
