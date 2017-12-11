package com.neuSep17.ui.consumer;

import com.neuSep17.dto.Vehicle;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;

public class SearchPanel extends JPanel
{
    private BrowseInventoryFrame parent;
    private JPanel searchPanel;
    private JLabel sortBy;
    private JButton search;
    private JLabel searchLabel;
    private JComboBox searchTextComboBox;
    private Vector dictionaryVector;
    private HashSet<String> dictionary;
    private JComboBox<String> sortItem;
    public String[] sortKeys = { "Select Sort By", "Price: High To Low", "Price: Low To High", "Year: High To Low",
            "Year: Low To High", "Make: A - Z", "Make: Z - A" };
    private ArrayList<Vehicle> vehicles;

    public SearchPanel(BrowseInventoryFrame bif, ArrayList<Vehicle> vehicles)
    {
        this.parent = bif;
        this.vehicles = vehicles;
        this.setLayout(new BorderLayout(0, 0));
        this.setPreferredSize(new Dimension(1200, 50));
        dictionaryVector = new Vector();
        createSearchPanelComponents();
        addSearchPanelComponents();
    }

    public JComboBox<String> getSortItem()
    {
        return this.sortItem;
    }

    public String[] getSortKeys()
    {
        return sortKeys;
    }

    private void createSearchPanelComponents()
    {
        // background add picture
        searchPanel = new JPanel()
        {
            public void paintComponent(Graphics g)
            {
                ImageIcon backImage = new ImageIcon("data/images/bannerOfSearchCar.jpg");

                g.drawImage(backImage.getImage(), 0, 0, this.getSize().width, this.getSize().height, this);
            }
        };
        searchPanel.setOpaque(true);
        // search
        //search = new JButton("Search");
        ImageIcon icon = new ImageIcon("data/images/searchIcon.png");
        search = new JButton(icon);

        searchTextComboBox = new JComboBox();
        for (Component component : searchTextComboBox.getComponents()) {
            if (component instanceof JButton) {
                searchTextComboBox.remove(component);
            }
        }
        setDictionaryVector();
        setSearchTextComboBox();
        searchLabel = new JLabel("Search for your car:");
        searchLabel.setFont(new Font("Black", 1, 24));
        searchLabel.setForeground(Color.WHITE);

        // sort
        sortBy = new JLabel("Sort by : ");
        sortBy.setForeground(Color.WHITE);
        sortItem = new JComboBox();
        for (int i = 0; i < sortKeys.length; i++)
        {
            sortItem.addItem(sortKeys[i]);
        }

        //font
        Font font = new Font("", 0,22);
        //searchText.setFont(font);
        searchTextComboBox.setFont(font);
        //sortBy.setFont(font);
        //search.setFont(new Font("", 0, 16));

        //location
        setItemsLocation();
    }
    private void setItemsLocation() {
        int heightOfSearchCombo = 50;
        int heightOfItemsOnPanel = 60;
        int weightOfSearchLabel = 400;
        int weightOfSearchComo = 460;
        int weightOfSortBy = 80;
        int weightOfSortItem = 170;
        int yOfSort = 150;
        int xOfSearch = 60;
        int sizeOfSearchButton = 30;
        searchLabel.setBounds(xOfSearch, 30, weightOfSearchLabel, heightOfItemsOnPanel);
        searchTextComboBox.setBounds(xOfSearch,80, weightOfSearchComo, heightOfSearchCombo);
        search.setBounds(xOfSearch + weightOfSearchComo - 5, 90, sizeOfSearchButton, sizeOfSearchButton);
        sortBy.setBounds(940, yOfSort, weightOfSortBy, heightOfItemsOnPanel);
        sortItem.setBounds(1010, yOfSort, weightOfSortItem, heightOfItemsOnPanel);
    }
    private void setSearchTextComboBox() {
        searchTextComboBox.setMaximumSize(new Dimension(1200, 30));
        searchTextComboBox.setPreferredSize(new Dimension(400, 30));

        Vector vector = new Vector();
        searchTextComboBox.setModel(new DefaultComboBoxModel(vector));
        searchTextComboBox.setSelectedIndex(-1);
        searchTextComboBox.setEditable(true);
        JTextField text = (JTextField)searchTextComboBox.getEditor().getEditorComponent();
        text.setFocusable(true);
        text.setText("");

        SearchTextComboBoxListener searchTextListener = new SearchTextComboBoxListener(searchTextComboBox, dictionary, this);
        text.addKeyListener(searchTextListener);
        searchTextComboBox.addActionListener(new SearchComboBoxActionListener(searchTextListener));
    }

    private void setDictionary() {
        dictionary = new HashSet<String>();
        for (Vehicle v : vehicles) {
            dictionary.add(v.getYear().toString().toLowerCase());
            dictionary.add(v.getMake().toLowerCase());
            dictionary.add(v.getModel().toLowerCase());
            dictionary.add(v.getBodyType().toLowerCase());
            dictionary.add(v.getTrim().toLowerCase());
            dictionary.add(v.getCategory().toString().toLowerCase());
        }
        if (dictionary.contains("")) {
            dictionary.remove("");
        }
    }

    private void setDictionaryVector() {
        setDictionary();
        Iterator<String> iterator = dictionary.iterator();
        while (iterator.hasNext()) {
            dictionaryVector.add(iterator.next());
        }
    }

    private void addSearchPanelComponents() // search and sort
    {

        searchPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        searchPanel.setLayout(null);

        searchPanel.add(searchLabel);
        searchPanel.add(searchTextComboBox);
        searchPanel.add(search);
        searchPanel.add(sortBy);
        searchPanel.add(sortItem);
        this.add(searchPanel);
    }

    public void addListeners()
    {
        addSearchPanelListeners();
        addSortPanelListeners();
    }

    private void addSearchPanelListeners()
    {
        // search button listener
        SearchListener searchlistener = new SearchListener();
        search.addActionListener(searchlistener);
    }

    private void addSortPanelListeners()
    {
        SortListener sortlistener = new SortListener();
        sortItem.addActionListener(sortlistener);
    }

    public void updateSearch()
    {
        JTextField searchText = (JTextField)searchTextComboBox.getEditor().getEditorComponent();
        String searchInfo = searchText.getText();

//        System.out.println(searchInfo);
        parent.doSearch(searchInfo);
    }

    public void updateSort()
    {
         String sortMethod = sortItem.getSelectedIndex() == 0 ? null :
         (String) sortItem.getSelectedItem();
        // System.out.println(sortMethod);
         parent.doSort(sortMethod);
    }
    class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
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
