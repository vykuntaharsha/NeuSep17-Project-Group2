package com.neuSep17.ui.inventoryList.CenterSection;

import com.neuSep17.ui.inventoryList.CenterSection.IncentiveControlPanel;
import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.IncentiveTableModel;
import com.neuSep17.service.IncentiveServiceAPI_Test;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class IncentiveTableView extends JPanel {
    private IncentiveTableModel incentiveTableModel;
    private IncentiveServiceAPI_Test incentiveServiceAPI_test;
    private IncentiveControlPanel incentiveControlPanel;
    private PageController<Incentive> pageController;
    private JTable jTable;

    public IncentiveTableView(IncentiveServiceAPI_Test incentiveServiceAPI_test){
        this.setSize(1100,1000);
        this.incentiveServiceAPI_test = incentiveServiceAPI_test;
        this.pageController = new PageController(incentiveServiceAPI_test.getIncentives(),10);
        this.incentiveControlPanel = new IncentiveControlPanel(pageController,this);
        incentiveTableModel = new IncentiveTableModel(incentiveServiceAPI_test);
        jTable = new JTable(incentiveTableModel);
        jTable.setSelectionBackground(Color.DARK_GRAY);
        JScrollPane sp=new JScrollPane(jTable);
        jTable.setRowHeight(30);
        sp.setPreferredSize(new Dimension(1050,700));
        jTable.setPreferredSize(new Dimension(700,700));
        jTable.setFont(new Font("Menu.font", Font.PLAIN, 16));
        jTable.getTableHeader().setFont(new Font("Menu.font", Font.PLAIN, 16));
        jTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        jTable.getColumnModel().getColumn(6).setPreferredWidth(120);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.LEFT);
        jTable.setDefaultRenderer(Object.class, r);

        sp.setBorder(new TitledBorder("Incitive information.."));

        this.setLayout(new BorderLayout());
        this.add(incentiveControlPanel,"North");
        this.add(sp,"Center");

    }

    public void update(){
        jTable.updateUI();
    }
}
