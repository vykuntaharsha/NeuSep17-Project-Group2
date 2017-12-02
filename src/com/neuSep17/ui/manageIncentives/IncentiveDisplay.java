package com.neuSep17.ui.manageIncentives;

import com.neuSep17.dto.IncentiveTableModel;
import com.neuSep17.service.IncentiveServiceAPI_Test;

import javax.swing.*;
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
        add(sort_list);
        add(incentive_list);

    }
}

class IncentiveList extends JPanel {
    private IncentiveServiceAPI_Test service;
    private JTable incentive_list;

    public IncentiveList() {

        setBackground(new Color(206, 206, 206));
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(1000, 530));
        service = new IncentiveServiceAPI_Test("src/com/neuSep17/dto/IncentiveSample.txt");
        incentive_list = new JTable(new IncentiveTableModel(service));
        for (int i = 0; i < 7; i++) {
            TableColumn firstColumn = incentive_list.getColumnModel().getColumn(i);
            firstColumn.setPreferredWidth(150);
        }
        incentive_list.setPreferredScrollableViewportSize(new Dimension(980, 530));
        incentive_list.setFillsViewportHeight(true);
        incentive_list.setAutoCreateColumnsFromModel(true);
        incentive_list.setRowHeight(100);
        incentive_list.setBackground(new Color(206, 206, 206));
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
