package com.neuSep17.ui.manageIncentives;

import com.neuSep17.dto.Incentive;

import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class IncentiveListAndSortPanel extends JPanel {
    private IncentiveSortPanel sortPanel;
    private IncentiveListPanel listPanel;

    public IncentiveListAndSortPanel() throws IOException {
        sortPanel = new IncentiveSortPanel();
        listPanel = new IncentiveListPanel();

        panelLayout();
    }

    public void panelLayout(){
        Dimension dim = getPreferredSize();
        dim.width = 1000;
        setPreferredSize(dim);

        BoxLayout whole_layout = new BoxLayout(this,BoxLayout.Y_AXIS);
        setLayout(whole_layout);
        setBackground(new Color(206, 206, 206));
        add(sortPanel);
        add(listPanel);
    }

    public IncentiveListPanel getListPanel() {
        return listPanel;
    }

    public IncentiveSortPanel getSortPanel() {
        return sortPanel;
    }
}

class IncentiveSortPanel extends JPanel{

}

class IncentiveListPanel extends JPanel{
    private JTable list_table;
    private TempFileReader tempFileReader;
    private ArrayList<Incentive> data;
    private IncentiveListTableModel tableModelData;
    private TableRowSorter<TableModel> sorter;

    public IncentiveListPanel() throws IOException {
        setBackground(new Color(206, 206, 206));
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(1000,530));

        tempFileReader = new TempFileReader();
        data = tempFileReader.read();
        tableModelData = new IncentiveListTableModel(data);
        list_table = new JTable(tableModelData);
        list_table.setBackground(new Color(206, 206, 206));
        for (int i = 0; i < 8; i++ ){
            TableColumn firstColumn = list_table.getColumnModel().getColumn(i);
            firstColumn.setPreferredWidth(150);
        }
        list_table.setPreferredScrollableViewportSize(new Dimension(980,530));
        list_table.setFillsViewportHeight(true);
        list_table.setAutoCreateColumnsFromModel(true);
        list_table.setRowHeight(100);
        JScrollPane scrollPane = new JScrollPane(list_table);
        add(scrollPane);
    }

    public void searchTable(String searchContent){
        sorter = new TableRowSorter<>(list_table.getModel());
        if (searchContent.length() == 0){
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchContent));
        }
        list_table.setRowSorter(sorter);
    }

    public void filterTable(ArrayList<String> filterList){
        sorter = new TableRowSorter<>(list_table.getModel());

        int filterNumber = 0;
        for (String filter: filterList){
            filterNumber++;
        }
        ArrayList<RowFilter<Object,Object>> filters = new ArrayList<>(filterNumber);
        for (String filter: filterList){
            if (filter != "Not All"){
                filters.add(RowFilter.regexFilter(filter));
            }
        }
        sorter.setRowFilter(RowFilter.andFilter(filters));
        list_table.setRowSorter(sorter);
    }

    public void sortTable(int selectedIndex){
        sorter = new TableRowSorter<>(list_table.getModel());
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        if (selectedIndex == 1) {
            sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
        } else if (selectedIndex == 2) {
            sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        } else {
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        }
        sorter.setSortKeys(sortKeys);
        list_table.setRowSorter(sorter);
    }
}