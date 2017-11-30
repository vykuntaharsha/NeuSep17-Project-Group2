package com.neuSep17.dto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IncentiveAddEditDialog extends JDialog {

    private Incentive incentive;
    private JLabel labelTitle, labelDiscount,labelStart,labelEnd,labelCriterion,labelDescription;

    private JTextField fieldTitle,fieldDiscount,fieldStart,fieldEnd;

    private JTextArea description;
    private JScrollPane scrollPane;

    private JComboBox comboBoxRange, comboBoxCategory,comboBoxYear,comboBoxMake,comboBoxPrice;
    private JButton buttonSave;
    private JButton buttonCancel;

    public IncentiveAddEditDialog(String dealerId){
        initComponents();
        addComponents();
        makeListeners();
        display();
        setTitle("add incentive");
    }

    //edit constructor
    public IncentiveAddEditDialog(String dealerId, Incentive incentive){
        this.incentive = incentive;

        initComponents();
        addComponents();
        makeListeners();
        display();
        setTitle("edit incentive");
    }

    //jing
    private void initComponents() {
//       fieldDiscount.

        labelTitle = new JLabel("Title: ");
        labelDiscount = new JLabel("Discount: ");
        labelStart = new JLabel("Start Date: ");
        labelEnd = new JLabel("End Date: ");
        labelCriterion = new JLabel("Criterion: ");
        labelDescription = new JLabel("Description: ");


        fieldTitle = new JTextField(incentive == null ? "" : incentive.getTitle(),20);
        fieldDiscount = new JTextField(incentive == null ? "" : String.valueOf(incentive.getDiscount()),20);
        fieldStart = new JTextField(incentive == null ? "" : incentive.getStartDate(),20);
        fieldEnd = new JTextField(incentive == null ? "" : incentive.getEndDate(),20);

        description = new JTextArea(incentive == null ? "" : incentive.getDescription(),3,20);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        scrollPane = new JScrollPane(description);

        comboBoxRange = new JComboBox(new String[]{"All"});
//        comboBoxCategory = new JComboBox(Category.values());
        comboBoxCategory = new JComboBox(new String[] {"NEW", "USED", "CERTIFIED"});
        comboBoxYear = new JComboBox(new String[] {"2010","2011","2012","2013"});
        comboBoxMake = new JComboBox(new String[] {"Cadillac","Chevrolet","Cadillac","Toyota"});
        comboBoxPrice = new JComboBox(new String[] {"500","1000","1500","2000"});
        createComboBox(comboBoxRange,"range");
        createComboBox(comboBoxCategory,"category");
        createComboBox(comboBoxYear,"year");
        createComboBox(comboBoxMake,"make");
        createComboBox(comboBoxPrice,"price");

        if(incentive != null){
            //criterion data,get data from vehicle
            ArrayList<String> criterion = incentive.getCriterion();
            String[] crit = new String[5];
            int i = 0;
            while (i < 4){
                crit[i] = criterion.get(i);
                i++;
            }
            crit[4] = criterion.get(criterion.size() - 1);

            if(crit[0].equals("all")){
                comboBoxRange.setSelectedItem(comboBoxRange.getItemAt(1));
                comboBoxCategory.setEnabled(false);
                comboBoxMake.setEnabled(false);
                comboBoxPrice.setEnabled(false);
                comboBoxYear.setEnabled(false);
            }else {
                //set the selected item
                comboBoxCategory.setSelectedItem(crit[1].equals("no") ? comboBoxCategory.getItemAt(0) : crit[1]);
                comboBoxYear.setSelectedItem(crit[2].equals("no") ? comboBoxYear.getItemAt(0) : crit[2]);
                comboBoxMake.setSelectedItem(crit[3].equals("no") ? comboBoxMake.getItemAt(0) : crit[3]);
                comboBoxPrice.setSelectedItem(crit[4].equals("no") ? comboBoxPrice.getItemAt(0) : crit[4]);
            }
        }

        //logical of the range of criterion

        buttonSave = new JButton("Save");
        buttonCancel = new JButton("Cancel");
    }

    //lulu
    private void makeListeners() {
        CriterionActionListener cl = new CriterionActionListener();
        comboBoxRange.addActionListener(cl);
    }

    class CriterionActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(comboBoxRange.getSelectedIndex() != 0){
                comboBoxCategory.setSelectedItem(comboBoxCategory.getItemAt(0));
                comboBoxYear.setSelectedItem(comboBoxYear.getItemAt(0));
                comboBoxMake.setSelectedItem(comboBoxMake.getItemAt(0));
                comboBoxPrice.setSelectedItem(comboBoxPrice.getItemAt(0));
                comboBoxCategory.setEnabled(false);
                comboBoxYear.setEnabled(false);
                comboBoxMake.setEnabled(false);
                comboBoxPrice.setEnabled(false);
            }else{
                comboBoxCategory.setEnabled(true);
                comboBoxYear.setEnabled(true);
                comboBoxMake.setEnabled(true);
                comboBoxPrice.setEnabled(true);
            }
        }
    }

    //jing
    private void validateForm(){

    }

    //jing
    private void saveIncentive(){
//        incentive.
    }

    private void createComboBox(JComboBox comboBox, String name){
        comboBox.insertItemAt("Choose "+name+"...",0);
        comboBox.setSelectedItem(comboBox.getItemAt(0));
    }

    private void display() {
        setSize(500, 800);
        setVisible(true);
    }

    private void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.insets = new Insets(5,5,5,5);
        constraints.anchor = GridBagConstraints.WEST;

        addLine(constraints,labelTitle,fieldTitle);
        addLine(constraints,labelDiscount,fieldDiscount);
        addLine(constraints,labelStart,fieldStart);
        addLine(constraints,labelEnd,fieldEnd);
        addCriterion(constraints);
//        constraints.ipady = 40;
        addLine(constraints,labelDescription,scrollPane);


//        constraints.ipady = 0;
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelButtons.add(buttonSave);
        panelButtons.add(buttonCancel);

        constraints.gridy++;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(panelButtons,constraints);
    }

    private void addLine(GridBagConstraints c, JComponent label, JComponent text){
        c.gridx = 0;
        add(label,c);
        c.gridx = 1;
        add(text,c);
        c.gridy++;
    }

    private void addCriterion(GridBagConstraints c){

        c.gridx = 0;
        add(labelCriterion,c);
        c.gridheight = 1;
        c.gridx = 1;
        add(comboBoxRange,c);
        c.gridy++;
        add(comboBoxCategory,c);
        c.gridy++;
        add(comboBoxMake,c);
        c.gridy++;
        add(comboBoxYear,c);
        c.gridy++;
        add(comboBoxPrice,c);
        c.gridy++;
    }
}