package com.neuSep17.ui.manageIncentives;

import com.neuSep17.dto.Incentive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncentiveToolPanel extends JPanel {

    private JButton addButton, deleteButton, editButton;

    public IncentiveToolPanel(){
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
                IncentiveAddEditDialog addDialog = new IncentiveAddEditDialog("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IncentiveDeleteDialog deleteDialog = new IncentiveDeleteDialog();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Incentive test = new Incentive(new String[]{
                        "000006","gmps-camino","All Sale","500.0","2010-01-01","2100-12-31",
                        "all,no,no,no,no,no,no,no","All the vehicle apply"});
                IncentiveAddEditDialog editDialog = new IncentiveAddEditDialog("",test);
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


