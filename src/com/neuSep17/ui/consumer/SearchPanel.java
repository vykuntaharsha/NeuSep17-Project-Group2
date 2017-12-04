package com.neuSep17.ui.consumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchPanel extends JPanel {
    private BrowseInventoryFrame parent;
    private JPanel searchPanel;
    private JPanel backPanel;
    private JLabel sortBy;
    private JButton search;
    private JTextField searchText;
    private JComboBox<String> sortItem;
    public String[] sortKeys = {"Select Sort By",
            "Price: High To Low",
            "Price: Low To High",
            "Year: High To Low",
            "Year: Low To High",
            "Make: A - Z", "Make: Z - A" };

    public SearchPanel(BrowseInventoryFrame bif) {
        this.parent = bif;
        this.setLayout(new BorderLayout(0, 0));
        this.setPreferredSize(new Dimension(1200, 50));
        createSearchPanelComponents();
        addSearchPanelComponents();
    }
    public JComboBox<String> getSortItem() {
        return this.sortItem;
    }

    public String[] getSortKeys() {
        return sortKeys;
    }

    private void createSearchPanelComponents()
    {
        //background add picture
        searchPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                ImageIcon backImage =
                        new ImageIcon("data/images/bannerOfCar.jpg");

                g.drawImage(backImage.getImage(), 0, 0, this.getSize().width,this.getSize().height,this);
            }
        };
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setOpaque(true);
        // search
        search = new JButton("Search");
        searchText = new JTextField(100);
        //searchText.setPreferredSize(new Dimension(800, 20));
        searchText.setMaximumSize(new Dimension(800, 30));

        // sort
        sortBy = new JLabel("Sort by : ");
        sortItem = new JComboBox();
        for (int i = 0; i < sortKeys.length; i++)
        {
            sortItem.addItem(sortKeys[i]);
        }
    }
    private void addSearchPanelComponents() // search and sort
    {


        searchPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        BoxLayout boxlayout = new BoxLayout(searchPanel, BoxLayout.X_AXIS);
        searchPanel.setLayout(boxlayout);
        searchPanel.add(Box.createHorizontalStrut(210));
        //searchPanel.add(Box.createHorizontalGlue());
        searchPanel.add(searchText);
        searchPanel.add(search);
        //searchPanel.add(Box.createHorizontalGlue());
        searchPanel.add(Box.createHorizontalStrut(190));
        searchPanel.add(sortBy);
        searchPanel.add(sortItem);
        this.add(searchPanel);

    }

    public void addListeners(){
        addSearchPanelListeners();
        addSortPanelListeners();
    }
    private void addSearchPanelListeners()
    {
        // search button listener
        SearchListener searchlistener = new SearchListener();
        search.addActionListener(searchlistener);

        // press enter listener
        SearchKeyListener searchKeyListener = new SearchKeyListener();
        searchText.addKeyListener(searchKeyListener);
    }

    private void addSortPanelListeners()
    {
        SortListener sortlistener = new SortListener();
        sortItem.addActionListener(sortlistener);
    }
    public void updateSearch()
    {
        String searchInfo = searchText.getText();
        // System.out.println(searchInfo);
        parent.doSearch(searchInfo);
    }

    public void updateSort()
    {
        String sortMethod = sortItem.getSelectedIndex() == 0 ? null : (String) sortItem.getSelectedItem();
        // System.out.println(sortMethod);
        parent.doSort(sortMethod);
    }
    class SearchKeyListener implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == e.VK_ENTER)
            {
                updateSearch();
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {

        }
    }

    class SearchListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateSearch();
        }
    }

    class SortListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateSort();
        }
    }
}

