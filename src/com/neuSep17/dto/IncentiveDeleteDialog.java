package com.neuSep17.dto;

import javax.swing.*;
import java.awt.*;

public class IncentiveDeleteDialog extends JDialog {
    private JLabel explaination;
    private JTextField textField;
    private JButton submitButton;

    public IncentiveDeleteDialog(){
        setTitle("delete incentive");
        setSize(300,300);
        setVisible(true);

        explaination = new JLabel("Please put the incentive ID you want to delete");
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(100, 35));
        submitButton = new JButton("Submit");
        display();
    }

    public void display(){
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 1;
        gc.gridx = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;
        gc.insets = new Insets(5,5,5,5);

        gc.gridy++;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(explaination, gc);


        gc.gridy++;
        gc.anchor = GridBagConstraints.CENTER;
        add(textField, gc);

        gc.weighty = 1;
        gc.gridy++;
        gc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gc);
    }
}
