package com.neuSep17.ui.manageIncentives;

import com.neuSep17.dto.Category;
import com.neuSep17.dto.Incentive;
import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Map;

public class IncentiveAddEditDialog extends JDialog {

    //service
    private IncentiveServiceAPI_Test incentiveAPI;
    private InventoryServiceAPI_Test inventoryAPI;

    //current dearler
    private String dealerId;

    //label
    private Incentive incentive;
    private JLabel labelTitle, labelDiscount,labelStart,labelEnd,labelCriterion,labelDescription;

    private JTextField fieldTitle,fieldDiscount,fieldStart,fieldEnd;

    private JTextArea description;
    private JScrollPane scrollPane;

    //comboBoxes for criterion
    private JComboBox[] criterions;
    private Map<String, List<String>> criterionMap;
    private String[] criterionKey = {"range","category","year","make","price"};

    private JButton buttonSave;
    private JButton buttonCancel;
    private JOptionPane alert;

    //incentive file
    String file = "data/IncentiveSample.txt";

    public IncentiveAddEditDialog(String dealerId){
        setTitle("add incentive");
        init(dealerId);
    }

    //edit constructor
    public IncentiveAddEditDialog(String dealerId, Incentive incentive){
        this.incentive = incentive;
        init(dealerId);
        setTitle("edit incentive");
    }

    private void init(String dealerId){
        //replace by current dealerId
        dealerId = "gmps-bresee";//"gmps-camino";
        incentiveAPI = new IncentiveServiceAPI_Test(file);
        inventoryAPI = new InventoryServiceAPI_Test("data/"+dealerId);
        initComponents();
        addComponents();
        makeListeners();
        display();
    }

    private void initComponents() {
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

        createCriterionComponents();

        if(incentive != null){
            initCriterion(incentive);
        }

        buttonSave = new JButton("Save");
        buttonCancel = new JButton("Cancel");
    }

    private void createCriterionComponents(){
        criterions = new JComboBox[criterionKey.length];
        criterionMap = inventoryAPI.getComboBoxItemsMap(inventoryAPI.getVehicles());
        for(int i = 0;i < criterions.length;i++){
            criterions[i] = new JComboBox();
            criterions[i].addItem("Choose "+criterionKey[i]+"...");
            criterions[i].setPreferredSize(new Dimension(200,30));
            criterions[i].setName(criterionKey[i]);
            if(i > 1){
                for(String item : criterionMap.get(criterionKey[i])){
                    if(criterionKey[i].equals("price")){
                        item = item.substring(item.indexOf("~")+1);
                    }
                    criterions[i].addItem(item);
                }
            }
        }
        criterions[0].addItem("all");
        for(Category s : Category.values()){
            criterions[1].addItem(s);
        }
    }



    private void initCriterion(Incentive incentive) {
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
            criterions[0].setSelectedItem(crit[0]);
            for(i = 1; i < criterions.length;i++){
                criterions[i].setEnabled(false);
            }
        }else{
            for(i = 1; i < criterions.length;i++){
                criterions[i].setSelectedItem(crit[i].equals("no") ? criterions[i].getItemAt(0) : crit[i]);
            }
        }
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

        addButton(constraints);
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
        for(int i = 0; i < criterions.length;i++){
            add(criterions[i],c);
            c.gridy++;
        }
    }

    private void addButton(GridBagConstraints constraints){
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

    private void makeListeners() {
        CriterionActionListener cl = new CriterionActionListener();
        criterions[0].addActionListener(cl);

        ValidationActionListener vl = new ValidationActionListener();
        buttonSave.addActionListener(vl);
    }

    class CriterionActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateCreterion();
        }
    }

    private void updateCreterion(){
        if(criterions[0].getSelectedIndex() != 0){
            for(int i = 1; i < criterions.length;i++){
                criterions[i].setSelectedItem(criterions[i].getItemAt(0));
                criterions[i].setEnabled(false);
            }
        }else{
            for(int i = 1; i < criterions.length;i++){
                criterions[i].setEnabled(true);
            }
        }
    }

    class ValidationActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){

            String s= "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
            if(fieldTitle.getText() == null || fieldTitle.getText().trim().equals("")){
                qq(labelTitle.getText()+" is not supposed to be NULL.");
            }else if(fieldDiscount.getText().trim().matches(s)){
                qq(" Price is must be digits.");
            }else if(description.getText() == null || description.getText().trim().equals("")){
                qq(labelDescription.getText()+" is not supposed to be NULL.");
            }
//            InputValidation(fieldTitle,fieldTitle.getText(),labelTitle.getText());
//            //validate discount
//            InputValidation(fieldDiscount,fieldDiscount.getText(),labelDiscount.getText());
//            //validate description
//            InputValidation(description,description.getText(),labelDescription.getText());

        }

    }
    private void qq(String content){
        alert.showMessageDialog(new JFrame(),
                content,
                "Input Invalid",
                JOptionPane.WARNING_MESSAGE);
    }
    //jing
    private void InputValidation(JComponent comp,String text,String content) {
        alert = new JOptionPane();
        if (text.equals(null) || text.trim().equals("")){
            alert.showMessageDialog(new JFrame(),
                    content+" is not supposed to be NULL.",
                    "Input Invalid",
                    JOptionPane.WARNING_MESSAGE);
        }
        if (comp.equals(fieldDiscount)){
            Pattern p = Pattern.compile("^\\+?[1-9][0-9]*$");//验证非零的正整数
            Matcher m = p.matcher(fieldDiscount.getText());
            if (m.matches()){
                return;
            }else {
                alert.showMessageDialog(new JFrame(),
                        " Price is must be digits.",
                        "Input Invalid",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    private void saveIncentive(){
    }


    private void display() {
        setSize(500, 800);
        setVisible(true);
    }

}