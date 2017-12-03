package com.neuSep17.ui.manageIncentives;

import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.IncentiveTableModel;
import com.neuSep17.service.IncentiveServiceAPI_Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by starhaotian on 01/12/2017.
 */
public class IncentiveDisplay extends JPanel {
    IncentiveList incentive_list;
    SortList sort_list;

    public IncentiveDisplay() {
        //set layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        incentive_list = new IncentiveList();
        sort_list = new SortList();
        //add(sort_list);
        add(incentive_list);

    }
}

class IncentiveList extends JPanel {
    private IncentiveServiceAPI_Test service;
    private JTable incentive_list;
    IncentiveTableModel incentive_model;

    public IncentiveList() {

        setBackground(new Color(206, 206, 206));
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(1000, 530));
        service = new IncentiveServiceAPI_Test("src/com/neuSep17/dto/IncentiveSample.txt");
        incentive_model = new IncentiveTableModel(service);
        incentive_list = new JTable(incentive_model);
        //set table header size, color , font
        JTableHeader T_header = incentive_list.getTableHeader();
        T_header.setPreferredSize(new Dimension(1,25));
        T_header.setBackground(new Color(0, 0, 0));
        T_header.setForeground(new Color(61, 200, 247));
        T_header.setFont(new Font("Menlo", Font.PLAIN, 16));

        for (int i = 0; i < 7; i++) {
            TableColumn firstColumn = incentive_list.getColumnModel().getColumn(i);
            firstColumn.setPreferredWidth(150);
        }

        // set table attribute
        incentive_list.setForeground(new Color(0, 0, 0));
        incentive_list.setGridColor(new Color(0, 0, 0));
        incentive_list.setBackground(new Color(61, 200, 247));
        incentive_list.setFont(new Font("Menlo", Font.PLAIN, 12));
        //sort function
        incentive_list.setAutoCreateRowSorter(true);
        incentive_list.setPreferredScrollableViewportSize(new Dimension(980, 530));
        incentive_list.setFillsViewportHeight(true);
        incentive_list.setAutoCreateColumnsFromModel(true);
        incentive_list.setRowHeight(100);

        JScrollPane scrollPane = new JScrollPane(incentive_list);
        add(scrollPane);
    }


}

class SortList extends JPanel {

    private JComboBox sortCombo;
    private IncentiveSortListener incentiveSortListener;

    public SortList() {
        String[] sort_name = { "Discount high to low", "Discount low to high","Start date", "End date"};
        sortCombo = new JComboBox(sort_name);
        sortCombo.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(sortCombo);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBackground(new Color(206, 206, 206));
        setPreferredSize(new Dimension(1000, 30));

        sortCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = sortCombo.getSelectedIndex();
                IncentiveSortEvent ise = new IncentiveSortEvent(e, selectedIndex);
                if (incentiveSortListener != null) {
                    incentiveSortListener.sortEventOccurred(ise);
                }
            }
        });
    }

    public void setSortListener(IncentiveSortListener incentiveSortListener) {
        this.incentiveSortListener = incentiveSortListener;
    }

}
