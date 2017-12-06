package com.neuSep17.ui.manageIncentives;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IncentiveFilterPanel extends JPanel {
    private JLabel rangeLabel;
    private JLabel categoryLabel;
    private JLabel makerLabel;
    private JLabel typeLabel;
    private JLabel modelLabel;

    private JComboBox rangeCombo;
    private JComboBox categoryCombo;
    private JComboBox makerCombo;
    private JComboBox modelCombo;
    private JComboBox typeCombo;

    private JButton filterSubmit;

    private IncentiveFilterListener filterListener;

    private Color bgColor = new Color(226, 247, 252);

    public IncentiveFilterPanel(){
        Dimension dim = getPreferredSize();
        dim.width = 200;
        setPreferredSize(dim);
        setBackground(bgColor);

        Border innerBorder = BorderFactory.createTitledBorder("Refine by");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        //set up boxes
        rangeCombo = new JComboBox();
        categoryCombo = new JComboBox();
        makerCombo = new JComboBox();
        typeCombo = new JComboBox();
        modelCombo = new JComboBox();
        setBox();

        //set up labels
        categoryLabel = new JLabel("Category");
        rangeLabel = new JLabel("Range");
        makerLabel = new JLabel("Maker");
        modelLabel = new JLabel("Model");
        typeLabel = new JLabel("Type");


        //set up OK button
        filterSubmit = new JButton("Submit");

        //set layout
        filterLayout();

        allChosenListeners();

        //search button
        filterSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String range = (String)rangeCombo.getSelectedItem();
                String category = (String)categoryCombo.getSelectedItem();
                String maker = (String)makerCombo.getSelectedItem();
                String type = (String)typeCombo.getSelectedItem();
                String model = (String)modelCombo.getSelectedItem();

                ArrayList<String> filterList = new ArrayList<>();
                filterList.add(range);
                filterList.add(category);
                filterList.add(maker);
                filterList.add(type);
                filterList.add(model);

                IncentiveFilterEvent fe = new IncentiveFilterEvent(e, filterList);

                if (filterListener != null){
                    filterListener.filterEventOccurred(fe);
                }
            }
        });
    }

    private void allChosenListeners() {
        allChosenListener cl = new allChosenListener();
        rangeCombo.addActionListener(cl);
    }

    class allChosenListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateCreterion();
        }
    }

    private void updateCreterion(){
        if(rangeCombo.getSelectedIndex() != 0){
            categoryCombo.setSelectedItem(categoryCombo.getItemAt(0));
            categoryCombo.setEnabled(false);
            makerCombo.setSelectedItem(makerCombo.getItemAt(0));
            makerCombo.setEnabled(false);
            modelCombo.setSelectedItem(modelCombo.getItemAt(0));
            modelCombo.setEnabled(false);
            typeCombo.setSelectedItem(typeCombo.getItemAt(0));
            typeCombo.setEnabled(false);
        }else{
            categoryCombo.setEnabled(true);
            makerCombo.setEnabled(true);
            modelCombo.setEnabled(true);
            typeCombo.setEnabled(true);
        }
    }


    public void setBox(){
        DefaultComboBoxModel rangeModel = new DefaultComboBoxModel();
        rangeModel.addElement("Not All");
        rangeModel.addElement("All");
        rangeCombo.setModel(rangeModel);

        DefaultComboBoxModel modelModel = new DefaultComboBoxModel();
        modelModel.addElement("");
        modelModel.addElement("Camry");
        modelModel.addElement("Corolla");
        modelModel.addElement("RAV4");
        modelCombo.setModel(modelModel);

        DefaultComboBoxModel categoryModel = new DefaultComboBoxModel();
        categoryModel.addElement("");
        categoryModel.addElement("New");
        categoryModel.addElement("Used");
        categoryCombo.setModel(categoryModel);

        DefaultComboBoxModel makerModel = new DefaultComboBoxModel();
        makerModel.addElement("");
        makerModel.addElement("Toyota");
        makerModel.addElement("Nissan");
        makerModel.addElement("BMW");
        makerModel.addElement("Honda");
        makerModel.addElement("Chevrolet");
        makerCombo.setModel(makerModel);

        DefaultComboBoxModel typeModel = new DefaultComboBoxModel();
        typeModel.addElement("");
        typeModel.addElement("SUV");
        typeModel.addElement("Sedan");
        typeModel.addElement("Van");
        typeCombo.setModel(typeModel);

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
        gc.insets = new Insets (0, 0, 0, 5);
        add(rangeLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets (0, 0, 0, 0);
        add(rangeCombo, gc);

        //second row
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 5);
        add(categoryLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(categoryCombo, gc);


        //4th row
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 10);
        add(makerLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(makerCombo, gc);

        //5th row
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 10);
        add(modelLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(modelCombo, gc);

        //6th row
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets (0, 0, 0, 10);
        add(typeLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets (0, 0, 0, 0);
        add(typeCombo, gc);

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
