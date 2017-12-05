package com.neuSep17.ui.manageIncentives;

import com.neuSep17.dto.Incentive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IncentiveToolPanel extends JPanel {

    private JButton addButton, deleteButton, editButton;
    private String[] incentive_temp;
    private Incentive incentive_test;

    public IncentiveToolPanel(JTable incentive_list){
        Dimension dim = getPreferredSize();
        dim.height = 80;
        setPreferredSize(dim);

        addButton = new JButton("add");
        deleteButton = new JButton("delete");
        editButton = new JButton("edit");
        buttonLayout();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //first parameter should be replaced by current dealerId
                new IncentiveAddEditDialog("gmps-camino",incentive_list);
            }
        });



        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //temp
                IncentiveDeleteDialog deleteDialog = new IncentiveDeleteDialog(incentive_test);


            }
        });



        incentive_list.addMouseListener(new MouseAdapter(){    //鼠标事件
            public void mouseClicked(MouseEvent e){
                int selectedRow = incentive_list.getSelectedRow();
                String oa = (String)incentive_list.getValueAt(selectedRow, 0);
                String oc = (String)incentive_list.getValueAt(selectedRow, 1);
                String od = String.valueOf(incentive_list.getValueAt(selectedRow, 2));
                String oe = (String)incentive_list.getValueAt(selectedRow, 3);
                String of = (String)incentive_list.getValueAt(selectedRow, 4);
                String og = incentive_list.getValueAt(selectedRow, 5).toString();;
                String oh = (String)incentive_list.getValueAt(selectedRow, 6);
                incentive_temp = new String[]{oa,"",oc,od,oe,of,og,oh};


            }
        });

        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(incentive_temp == null || incentive_temp.length == 0){
                    JOptionPane.showMessageDialog(null, "Please select a line first!");
                }else {
                    incentive_test = new Incentive(incentive_temp);
                    //first parameter should be replaced by current dealerId
                    new IncentiveAddEditDialog("gmps-camino", incentive_test, incentive_list);
                }
            }
        });
    }


    public void buttonLayout(){
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();


        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridy = 0;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(addButton, gc);
        addButton.setPreferredSize(new Dimension(100,80));

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.CENTER;
        add(deleteButton, gc);
        deleteButton.setPreferredSize(new Dimension(100,80));

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(editButton, gc);
        editButton.setPreferredSize(new Dimension(100,80));
    }
}


