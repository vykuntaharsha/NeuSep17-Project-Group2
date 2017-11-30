package com.neuSep17.dto;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IncentiveFilterPanel extends JPanel {

    private JLabel rangeLabel;
    private JLabel vehicleLabel;
    private JLabel colorLabel;
    private JLabel makerLabel;
    private JLabel typeLabel;
    private JLabel yearLabel;

    private JList rangeList;
    private JComboBox vehicleCombo;
    private JComboBox colorCombo;
    private JComboBox makerCombo;
    private JComboBox typeCombo;
    private JComboBox yearCombo;

    private JButton filterSubmit;

    private IncentiveFilterListener filterListener;

    public IncentiveFilterPanel(){
        Dimension dim = getPreferredSize();
        dim.width = 200;
        setPreferredSize(dim);

        Border innerBorder = BorderFactory.createTitledBorder("Refine by");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        //set up boxes
        vehicleCombo = new JComboBox();
        colorCombo = new JComboBox();
        makerCombo = new JComboBox();
        typeCombo = new JComboBox();
        yearCombo = new JComboBox();
        setBox();

        //set up lists
        rangeList = new JList();
        setList();


        //set up labels
        vehicleLabel = new JLabel("Vehicle");
        colorLabel = new JLabel("Color");
        rangeLabel = new JLabel("Range");
        makerLabel = new JLabel("Maker");
        typeLabel = new JLabel("Type");
        yearLabel = new JLabel("Year");

        //set up OK button
        filterSubmit = new JButton("Submit");

        //set layout
        filterLayout();

        //search button
        filterSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String range = (String)rangeList.getSelectedValue();
                String color = (String)colorCombo.getSelectedItem();
                String vehicle = (String)vehicleCombo.getSelectedItem();
                String maker = (String)makerCombo.getSelectedItem();
                String type = (String)typeCombo.getSelectedItem();
                String year = (String)yearCombo.getSelectedItem();

                ArrayList<String> filterList = new ArrayList<>();
                filterList.add(range);
                filterList.add(color);
                filterList.add(vehicle);
                filterList.add(maker);
                filterList.add(type);
                filterList.add(year);

                IncentiveFilterEvent fe = new IncentiveFilterEvent(e, filterList);

                if (filterListener != null){
                    filterListener.filterEventOccurred(fe);
                }
            }
        });
    }

    public void setBox(){
        DefaultComboBoxModel vehicleModel = new DefaultComboBoxModel();
        vehicleModel.addElement("");
        vehicleModel.addElement("Camry");
        vehicleModel.addElement("Corolla");
        vehicleModel.addElement("RAV4");
        vehicleCombo.setModel(vehicleModel);

        DefaultComboBoxModel colorsModel = new DefaultComboBoxModel();
        colorsModel.addElement("");
        colorsModel.addElement("Red");
        colorsModel.addElement("white");
        colorsModel.addElement("Grey");
        colorCombo.setModel(colorsModel);

        DefaultComboBoxModel makerModel = new DefaultComboBoxModel();
        makerModel.addElement("");
        makerModel.addElement("Toyota");
        makerModel.addElement("Nissan");
        makerModel.addElement("BMW");
        makerCombo.setModel(makerModel);

        DefaultComboBoxModel typeModel = new DefaultComboBoxModel();
        typeModel.addElement("");
        typeModel.addElement("SUV");
        typeModel.addElement("Sedan");
        typeModel.addElement("Van");
        typeCombo.setModel(typeModel);

        DefaultComboBoxModel yearModel = new DefaultComboBoxModel();
        yearModel.addElement("");
        yearModel.addElement("2017");
        yearModel.addElement("2016");
        yearModel.addElement("2015");
        yearModel.addElement("2014");
        yearModel.addElement("2013");
        yearModel.addElement("2012");
        yearModel.addElement("2011");
        yearModel.addElement("2010");
        yearModel.addElement("2009");
        yearModel.addElement("2008");
        yearModel.addElement("2007");
        yearModel.addElement("2006");
        yearModel.addElement("2005");
        yearModel.addElement("2004");
        yearModel.addElement("2003");
        yearModel.addElement("2002");
        yearModel.addElement("2001");
        yearCombo.setModel(yearModel);
    }

    public void setList(){
        DefaultListModel rangeModel = new DefaultListModel();
        rangeModel.addElement("Not All");
        rangeModel.addElement("All");
        rangeList.setModel(rangeModel);
        rangeList.setPreferredSize(new Dimension(80, 66));
        rangeList.setBorder(BorderFactory.createEtchedBorder());
    }

    public void filterLayout(){
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        //first row
        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 15);
        add(rangeLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets (0, 0, 0, 5);
        add(rangeList, gc);

        //second row
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 30);
        add(colorLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(colorCombo, gc);


        //third row
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 30);
        add(vehicleLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(vehicleCombo, gc);

        //4th row
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 30);
        add(makerLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(makerCombo, gc);

        //5th row
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 30);
        add(typeLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(typeCombo, gc);

        //5th row
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 30);
        add(yearLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(yearCombo, gc);

        //final row
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 2.0;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(filterSubmit, gc);
    }

    //grey out
//    class ActionListener implements ActionListener{
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//        }
//    }

    public void setFilterListener(IncentiveFilterListener filterListener){
        this.filterListener = filterListener;
    }
}
