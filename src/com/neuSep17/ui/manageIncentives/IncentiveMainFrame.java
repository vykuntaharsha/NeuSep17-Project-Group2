package com.neuSep17.ui.manageIncentives;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class IncentiveMainFrame extends JFrame {
    private IncentiveFilterPanel filterPanel;
    private IncentiveSearchPanel searchPanel;
    private IncentiveListAndSortPanel listSortPanel;
    private IncentiveToolPanel toolPanel;

    public IncentiveMainFrame() throws IOException {
        super("Manage Incentives");
        setSize(1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //panel instance
        filterPanel = new IncentiveFilterPanel();
        searchPanel = new IncentiveSearchPanel();
        toolPanel = new IncentiveToolPanel();
        listSortPanel = new IncentiveListAndSortPanel();

        //set up layout
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(toolPanel, BorderLayout.SOUTH);
        add(filterPanel, BorderLayout.WEST);
        add(listSortPanel, BorderLayout.EAST);


        //set up listener
        searchPanel.setSearchListener(new IncentiveSearchListener(){
            public void searchEventOccurred(IncentiveSearchEvent se){
                String searchContent = se.getSearchContent();
                listSortPanel.getListPanel().searchTable(searchContent);
            }
        });

        filterPanel.setFilterListener(new IncentiveFilterListener(){
            @Override
            public void filterEventOccurred(IncentiveFilterEvent fe) {
                ArrayList<String> filterList = fe.getFilterList();
                listSortPanel.getListPanel().filterTable(filterList);
            }
        });

        /*
        listSortPanel.getSortPanel().setSortListener(new IncentiveSortListener(){
            @Override
            public void sortEventOccurred(IncentiveSortEvent ise) {
                int selectedIndex = ise.getSelectedIndex();
                listSortPanel.getListPanel().sortTable(selectedIndex);
            }
        });
         */
    }
}
