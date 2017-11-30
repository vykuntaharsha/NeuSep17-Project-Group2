package com.neuSep17.ui.consumer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

public class BrowseInventoryFrame extends JFrame implements Runnable
{
    // service API start
    InventoryServiceAPI_Test invsAPI;
    IncentiveServiceAPI_Test incsAPI;
    // service API end

    private String dealerID;
    private ArrayList<Vehicle> toDisplay;
    private List<Vehicle> searchedVehicles;

    // list
    private HashMap<Vehicle, ImageIcon> cache;
    private ListPanel listpanel;
    private int perpage, page;
    // list end

    // filter start
    private Map<String, List<String>> comboBoxItemsMap;
    private JCheckBox[] categories;
    private JLabel[] filterLabels;
    private JComboBox<String>[] filters;
    private String[] filterKeys = { "year", "make", "price", "type" };
    private FilterCheckBoxListener fchbl = new FilterCheckBoxListener();
    private FilterComboBoxListener fcobl = new FilterComboBoxListener();
    private JButton resetFilter;
    // filter end

    // **search start***
    private JLabel sortBy;
    private JButton search;
    private JTextField searchText;
    private JComboBox<String> sortItem;
    // **search end***

    public BrowseInventoryFrame() // (Dealer dealer)
    {
        super();
        setTitle("Browse Inventory of xx dealer");

        // replaced by your folder
        String file = "data/gmps-bresee";
        invsAPI = new InventoryServiceAPI_Test(file);
        incsAPI = new IncentiveServiceAPI_Test("data/IncentiveSample.txt");
        // dealerID= dealer.getID();
        setPage(0);
        perpage = 15;

        this.cache = new HashMap<Vehicle, ImageIcon>();
        this.toDisplay = new ArrayList<Vehicle>();
        updateVehicle();

        this.setSize(1200, 700);
        searchedVehicles = toDisplay;
        createComponents();
        addComponents();
        doSearch(null);
        addListeners();

    }

    private void createComponents()
    {
        // search panel
        createSearchPanelComponents();
        // filter panel
        createFilterPanelComponents();
        // list panel
        listpanel = new ListPanel(this);
    }

    private void createSearchPanelComponents()
    {
        // search
        search = new JButton("Search");
        searchText = new JTextField(10);

        // sort
        sortBy = new JLabel("Sort by : ");
        sortItem = new JComboBox();
        sortItem.addItem("Select Sort By");
        sortItem.addItem("Price: High To Low");
        sortItem.addItem("Price: Low To High");
        sortItem.addItem("Year: High To Low");
        sortItem.addItem("Year: Low To High");
        sortItem.addItem("Make: A - Z");
        sortItem.addItem("Make: Z - A");
    }

    private void createFilterPanelComponents()
    {
        categories = new JCheckBox[3];
        categories[0] = new JCheckBox();
        categories[0].setText("New");
        categories[0].setName("new");
        categories[1] = new JCheckBox();
        categories[1].setText("Used");
        categories[1].setName("used");
        categories[2] = new JCheckBox();
        categories[2].setText("Certified");
        categories[2].setName("certified");

        filterLabels = new JLabel[4];
        filters = new JComboBox[4];
        for (int i = 0; i < filterLabels.length; i++)
        {
            filterLabels[i] = new JLabel(filterKeys[i].toUpperCase());
            filterLabels[i].setPreferredSize(new Dimension(40, 25));
            filters[i] = new JComboBox<String>();
            filters[i].setPreferredSize(new Dimension(100, 25));
            filters[i].setName(filterKeys[i]);
        }
        resetFilter = new JButton("Reset");
    }

    private void addComponents()
    {
        Container con = getContentPane();
        con.setLayout(new BorderLayout(1, 1));

        // search panel
        JPanel searchPanel = new JPanel();
        addSearchPanelComponents(searchPanel);
        con.add(searchPanel, BorderLayout.NORTH);

        // filter panel
        JPanel filterPanel = new JPanel();
        addFilterPanelComponents(filterPanel);
        con.add(filterPanel, BorderLayout.WEST);
        // list panel

        con.add(listpanel, BorderLayout.CENTER);
    }

    private void addSearchPanelComponents(JPanel searchPanel) // search and sort
    {
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        BoxLayout boxlayout = new BoxLayout(searchPanel, BoxLayout.X_AXIS);
        searchPanel.setLayout(boxlayout);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(Box.createHorizontalGlue());
        searchPanel.add(searchText);
        searchPanel.add(search);
        searchPanel.add(Box.createHorizontalStrut(50));
        searchPanel.add(sortBy);
        searchPanel.add(sortItem);
    }

    private void addFilterPanelComponents(JPanel filterPanel)
    {
        BoxLayout boxLayout = new BoxLayout(filterPanel, BoxLayout.Y_AXIS);
        filterPanel.setLayout(boxLayout);
        filterPanel.setBorder(new TitledBorder("Filter"));
        JPanel checkBoxPanel = new JPanel();
        boxLayout = new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS);
        checkBoxPanel.setLayout(boxLayout);
        checkBoxPanel.setBorder(new TitledBorder("Category"));
        for (int i = 0; i < categories.length; i++)
        {
            JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            linePanel.add(categories[i]);
            checkBoxPanel.add(linePanel);
            checkBoxPanel.add(Box.createVerticalStrut(10));
        }

        filterPanel.add(Box.createVerticalStrut(10));
        filterPanel.add(checkBoxPanel);
        filterPanel.add(Box.createVerticalGlue());

        JPanel comboBoxPanel = new JPanel();
        boxLayout = new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS);
        comboBoxPanel.setLayout(boxLayout);
        for (int i = 0; i < filterLabels.length; i++)
        {
            JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            linePanel.add(filterLabels[i]);
            linePanel.add(filters[i]);
            filterLabels[i].setLabelFor(filters[i]);
            comboBoxPanel.add(linePanel);
        }
        comboBoxPanel.add(Box.createVerticalStrut(20));
        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linePanel.add(resetFilter);
        comboBoxPanel.add(linePanel);
        filterPanel.add(comboBoxPanel);
        filterPanel.add(Box.createVerticalGlue());
        filterPanel.add(Box.createVerticalStrut(50));
    }

    private void addListeners()
    {
        // search panel
        addSearchPanelListeners();
        addSortPanelListeners();
        // filter panel
        addFilterCheckBoxListeners();
        addResetFilterListeners();
        // list panel
        listpanel.addListeners();

        BrowseWindowListener m = new BrowseWindowListener();
        this.addWindowListener(m);

    }

    private void addSearchPanelListeners()
    {
        SearchListener searchlistener = new SearchListener();
        search.addActionListener(searchlistener);
    }

    private void addSortPanelListeners()
    {
        SortListener sortlistener = new SortListener();
        sortItem.addActionListener(sortlistener);
    }

    private void resetFilter()
    {
        removeFilterCheckBoxListeners();
        removeFilterComboBoxListeners();
        resetFilterCheckBox();
        resetFilterComboBox();
        addFilterCheckBoxListeners();
        addFilterComboBoxListeners();
        updateFilter();
    }

    private void addResetFilterListeners()
    {
        resetFilter.addActionListener(new FilterResetListener());
    }

    private void resetFilterCheckBox()
    {
        for (int i = 0; i < categories.length; i++)
        {
            categories[i].setSelected(false);
        }
    }

    private void resetFilterComboBox()
    {
        for (int i = 0; i < filterLabels.length; i++)
        {
            filters[i].setSelectedIndex(0);
        }
    }

    private void removeFilterCheckBoxListeners()
    {
        for (int i = 0; i < categories.length; i++)
        {
            categories[i].removeActionListener(fchbl);
        }
    }

    private void addFilterCheckBoxListeners()
    {
        for (int i = 0; i < categories.length; i++)
        {
            categories[i].addActionListener(fchbl);
        }
    }

    private void removeFilterComboBoxListeners()
    {
        for (int i = 0; i < filterLabels.length; i++)
        {
            filters[i].removeItemListener(fcobl);
        }
    }

    private void addFilterComboBoxListeners()
    {
        for (int i = 0; i < filterLabels.length; i++)
        {
            filters[i].addItemListener(fcobl);
        }
    }
    
    public int getPage() {
        return page;
    }

    void setPage(int page) {
        this.page = page;
    }

    void displaytoList() {
        listpanel.displaytoList(toDisplay);        
    }
    
    int getMaxPage() {
        return toDisplay.size() / perpage;
    }
    
    ImageIcon getIcon(Vehicle v) {
        return cache.get(v);
    }
    
    int getPerPage() {
        return perpage;
    }
    
    ArrayList<Vehicle> getVehiclestoDisplay(){
        return toDisplay;
    }
    
    class updateFinishedListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {

        }
    }

    private void updateVehicle() 
    {
        URL imgURL;
        Image temp = null;
        try {
            imgURL = new URL("http://inventory-dmg.assets-cdk.com/chrome_jpgs/2016/24174x90.jpg");
            temp = ImageIO.read(imgURL);
        } catch (Exception e) {
            e.printStackTrace();
        } 
          // TODO
          // use
          // swingworker
          // thread

        for (Vehicle v : invsAPI.getVehicles()) {
            // int i=(int) Math.round(3*Math.random());
            // Image temp=invsAPI.getImage_Test(v.getBodyType())[i];
            cache.put(v, new ImageIcon(temp, "icon for vehicle " + v.getId()));
            toDisplay.add(v);
        }
        return;

        /* updateThread t=new updateThread(); t.execute(); while(t.isDone()) {} 
         * // this waits until update finish
         */
    }

    class updateThread extends SwingWorker<Void, Void>
    {

        @Override
        protected Void doInBackground() throws Exception
        {
          
            Image img;
            for (Vehicle v : invsAPI.getVehicles())
            {
              //Image img=invsAPI.getImage(v)[0];
                try
                {
                    img = ImageIO.read(v.getPhotoUrl());
                }
                catch (Exception e)
                {
                    img = ImageIO.read(new File("src/com/neuSep17/ui/consumer/imagenotfound.jpg"));
                }
                cache.put(v, new ImageIcon(img, "icon for vehicle " + v.getId()));
            }
            return null;
        }

    }

    private void makeThisVisible()
    {
        this.setVisible(true);
    }

    class FilterCheckBoxListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateFilter();
        }
    }

    class FilterResetListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            resetFilter();
        }
    }

    class FilterComboBoxListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                updateFilter();
            }
        }
    }

    private void updateFilter()
    {
        String categoriesFilter = "";
        int count = 0;
        for (int i = 0; i < categories.length; i++)
        {
            if (categories[i].isSelected())
            {
                categoriesFilter += categories[i].getName() + " ";
                count++;
            }
        }
        if (count == 0 || count == categories.length)
        {
            categoriesFilter = null;
        }
        else
        {
            categoriesFilter = categoriesFilter.trim();
        }

        String yearFilter = filters[0].getSelectedIndex() == 0 ? null : (String) filters[0].getSelectedItem();
        String makeFilter = filters[1].getSelectedIndex() == 0 ? null : (String) filters[1].getSelectedItem();
        String priceFilter = filters[2].getSelectedIndex() == 0 ? null : (String) filters[2].getSelectedItem();
        String typeFilter = filters[3].getSelectedIndex() == 0 ? null : (String) filters[3].getSelectedItem();

        doFilter(categoriesFilter, yearFilter, makeFilter, priceFilter, typeFilter);
    }

    private void updateFilterComboboxItems()
    {
        removeFilterComboBoxListeners();
        for (int i = 0; i < filters.length; i++)
        {
            filters[i].removeAllItems();
            filters[i].addItem("All");
            List<String> comboBoxItems = comboBoxItemsMap.get(filters[i].getName());
            for (String item : comboBoxItems)
            {
                filters[i].addItem(item);
            }
        }
        addFilterComboBoxListeners();
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

    private void updateSearch()
    {
        String searchInfo = searchText.getText();
        // System.out.println(searchInfo);
        doSearch(searchInfo);
    }

    private void updateSort()
    {
        String sortMethod = sortItem.getSelectedIndex() == 0 ? null : (String) sortItem.getSelectedItem();
        // System.out.println(sortMethod);
        doSort(sortMethod);
    }

    private void doSort(String sortMethod)
    {
        if (sortMethod == null)
        {
            return;
        }
        StringBuilder sortType = new StringBuilder();
        boolean isAscending = true;
        isAscending = updateSortType(sortMethod, sortType);
        // System.out.println(sortMethod + " + " + sortType.toString() + " + " +
        // isAscending);
        searchedVehicles = invsAPI.sortVehicles(searchedVehicles, sortType.toString(), isAscending);
        updateFilter();
        displaytoList();
    }

    private boolean updateSortType(String sortMethod, StringBuilder sortType)
    {
        if (sortMethod.toLowerCase().contains("year"))
        {
            sortType.delete(0, sortMethod.length());
            sortType.append("year");
            if (sortMethod.toLowerCase().contains("low to high"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        if (sortMethod.toLowerCase().contains("price"))
        {
            sortType.delete(0, sortMethod.length());
            sortType.append("price");
            if (sortMethod.toLowerCase().contains("low to high"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        if (sortMethod.toLowerCase().contains("make"))
        {
            sortType.delete(0, sortMethod.length());
            sortType.append("make");
            if (sortMethod.toLowerCase().contains("a - z"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    private void doSearch(String search)
    {
        searchedVehicles = InventoryServiceAPI_Test.vehiclesSearchAndFilter(invsAPI.getVehicles(), null, null, null,
                null, null, search);
        toDisplay = (ArrayList<Vehicle>) searchedVehicles;
        comboBoxItemsMap = InventoryServiceAPI_Test.getComboBoxItemsMap(toDisplay);
        setPage(0);
        resetFilterCheckBox();
        updateFilterComboboxItems();
        displaytoList();
    }

    private void doFilter(String category, String year, String make, String price, String type)
    {
        toDisplay = (ArrayList<Vehicle>) InventoryServiceAPI_Test.vehiclesSearchAndFilter(searchedVehicles, category,
                year, make, price, type, null);
        setPage(0);
        displaytoList();
    }

    class BrowseWindowListener implements WindowListener
    {

        @Override
        public void windowActivated(WindowEvent arg0)
        {

        }

        @Override
        public void windowClosed(WindowEvent e)
        {

        }

        @Override
        public void windowClosing(WindowEvent e)
        {
            System.out.println("BrowseInventory closing, back to consumer window.");
            // TODO get to the dealer selection window
        }

        @Override
        public void windowDeactivated(WindowEvent e)
        {

        }

        @Override
        public void windowDeiconified(WindowEvent e)
        {
        }

        @Override
        public void windowIconified(WindowEvent e)
        {

        }

        @Override
        public void windowOpened(WindowEvent e)
        {
        }

    }

    public static void main(String[] args)
    {
        BrowseInventoryFrame bif = new BrowseInventoryFrame();
        Thread BrowseInventoryThread = new Thread(() -> bif.run());
        SwingUtilities.invokeLater(BrowseInventoryThread);
    }

    @Override
    public void run()
    {
        makeThisVisible();
    }

    
}
