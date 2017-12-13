package com.neuSep17.ui.manageIncentives;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class IncentiveMainFrame extends JFrame {
    private IncentiveFilterPanel filterPanel;
    private IncentiveSearchPanel searchPanel;
    private IncentiveDisplay listPanel;
    private IncentiveToolPanel toolPanel;

    public IncentiveMainFrame() throws IOException {

        super("Manage Incentives");
        setFrame();
        createPanel();
        setBorderLayout();
        setListener();

    }

    private void setListener() {
        //set up listener
        searchPanel.setSearchListener(new IncentiveSearchListener() {
            public void searchEventOccurred(IncentiveSearchEvent se) {
                String searchContent = se.getSearchContent();
                listPanel.getIncentive_list().searchTable(searchContent);
            }
        });

        filterPanel.setFilterListener(new IncentiveFilterListener() {
            @Override
            public void filterEventOccurred(IncentiveFilterEvent fe) {
                ArrayList<String> filterList = fe.getFilterList();
                listPanel.getIncentive_list().filterTable(filterList);
            }
        });
    }

    private void setBorderLayout() {
        //set up layout
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(toolPanel, BorderLayout.SOUTH);
        add(filterPanel, BorderLayout.WEST);
        add(listPanel, BorderLayout.CENTER);
    }

    private void createPanel() {
        //panel instance
        filterPanel = new IncentiveFilterPanel();
        searchPanel = new IncentiveSearchPanel();
        listPanel = new IncentiveDisplay();
        JTable table = listPanel.getIncentive_list().get_table();
        toolPanel = new IncentiveToolPanel(table);
    }

    private void setFrame() {
        setSize(1920, 1200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
