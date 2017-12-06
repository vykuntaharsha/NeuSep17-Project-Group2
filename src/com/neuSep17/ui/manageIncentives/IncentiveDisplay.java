package com.neuSep17.ui.manageIncentives;

import com.neuSep17.dto.IncentiveTableModel;
import com.neuSep17.service.IncentiveServiceAPI_Test;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by starhaotian on 01/12/2017.
 */
public class IncentiveDisplay extends JPanel {
    IncentiveList incentive_list;

    public IncentiveDisplay() {
        //set layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        incentive_list = new IncentiveList();
        add(incentive_list);
    }

    public IncentiveList getIncentive_list() {
        return incentive_list;
    }
}

class IncentiveList extends JPanel {
    private IncentiveServiceAPI_Test service;
    private JTable incentive_list;
    private IncentiveTableModel incentive_model;
    private TableRowSorter<TableModel> sorter;
    private RowFilter searchRowFilter;

    public IncentiveList() {
        createIncentivelist();
        setIncentivelist();
        setHeader();
    }

    private void setHeader() {
        JTableHeader T_header = incentive_list.getTableHeader();
        T_header.setPreferredSize(new Dimension(1, 25));
        T_header.setBackground(new Color(0, 0, 0));
        T_header.setForeground(new Color(226, 247, 252));
        T_header.setFont(new Font("Menlo", Font.PLAIN, 16));
    }

    private void setIncentivelist() {

        setBackground(new Color(226, 247, 252));
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(1000, 600));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.LEFT);
        for (int x = 0; x < incentive_list.getColumnCount(); x++) {
            incentive_list.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }
        for (int i = 0; i < 7; i++) {
            TableColumn firstColumn = incentive_list.getColumnModel().getColumn(i);
            firstColumn.setPreferredWidth(150);
        }
        // set table attribute
        incentive_list.setForeground(new Color(0, 0, 0));
        incentive_list.setGridColor(new Color(0, 0, 0));
        incentive_list.setBackground(new Color(226, 247, 252));
        incentive_list.setFont(new Font("Menlo", Font.PLAIN, 12));
        //sort function
        incentive_list.setAutoCreateRowSorter(true);
        incentive_list.setPreferredScrollableViewportSize(new Dimension(980, 600));
        incentive_list.setFillsViewportHeight(true);
        incentive_list.setAutoCreateColumnsFromModel(true);
        incentive_list.setRowHeight(60);
        JScrollPane scrollPane = new JScrollPane(incentive_list);
        add(scrollPane);
    }

    private void createIncentivelist() {
        service = new IncentiveServiceAPI_Test("data/IncentiveSample.txt");
        incentive_model = new IncentiveTableModel(service);
        incentive_list = new JTable(incentive_model);
    }

    public JTable get_table() {
        return incentive_list;
    }

    public void searchTable(String searchContent) {
        sorter = new TableRowSorter<>(incentive_list.getModel());
        if (searchContent.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            searchRowFilter = RowFilter.regexFilter("(?i)" + searchContent);
            sorter.setRowFilter(searchRowFilter);
        }
        incentive_list.setRowSorter(sorter);
    }

    public void filterTable(ArrayList<String> filterList) {
        sorter = new TableRowSorter<>(incentive_list.getModel());

        int filterNumber = 0;
        for (String filter : filterList) {
            filterNumber++;
        }
        ArrayList<RowFilter<Object,Object>> filters = new ArrayList<>(filterNumber);
        for (String filter: filterList){
            if (filter != "Not All"){
                filters.add(RowFilter.regexFilter("(?i)" + filter, 5));

        if (searchRowFilter != null) {
            filters.add(searchRowFilter);
        }
        sorter.setRowFilter(RowFilter.andFilter(filters));
        incentive_list.setRowSorter(sorter);
    }

}


