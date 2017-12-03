package com.neuSep17.ui.consumer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
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
    private JScrollPane filterScrollPane;
    private Map<String, List<String>> checkBoxPanelsMap;
    private FilterCheckBoxPanel[] checkBoxPanels;
    private String[] filterKeys = { "category", "year", "make", "price", "type" };
    private JPanel filterPanel;
    private JButton resetFilter;
    // filter end

    // **search start***
    private JLabel sortBy;
    private JButton search;
    private JTextField searchText;
    private JComboBox<String> sortItem;
    private String[] sortKeys = { "Select Sort By", "Price: High To Low", "Price: Low To High", "Year: High To Low",
            "Year: Low To High", "Make: A - Z", "Make: Z - A" };
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
        for (int i = 0; i < sortKeys.length; i++)
        {
            sortItem.addItem(sortKeys[i]);
        }
    }

    private void createFilterPanelComponents()
    {
        filterPanel = new JPanel();
        filterScrollPane = new JScrollPane();
        checkBoxPanels = new FilterCheckBoxPanel[filterKeys.length];
        for (int i = 0; i < checkBoxPanels.length; i++)
        {
            checkBoxPanels[i] = new FilterCheckBoxPanel(filterKeys[i], this);
        }
        resetFilter = new JButton("Clear All");
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
        filterScrollPane.setViewportView(filterPanel);
        addFilterPanelComponents();
        con.add(filterScrollPane, BorderLayout.WEST);
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

    private void addFilterPanelComponents()
    {
        BoxLayout boxLayout = new BoxLayout(filterPanel, BoxLayout.Y_AXIS);
        filterPanel.setLayout(boxLayout);
        filterPanel.setAutoscrolls(true);
        filterPanel.setBorder(new TitledBorder("Filter"));
        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linePanel.add(resetFilter);
        filterPanel.add(linePanel);
        for (int i = 0; i < checkBoxPanels.length; i++)
        {
            filterPanel.add(checkBoxPanels[i]);
        }
        filterPanel.add(Box.createVerticalStrut(0));
    }

    public void resizeFilterPanel()
    {
        int height = 40;
        for (FilterCheckBoxPanel checkBoxPanel : checkBoxPanels)
        {
            if (!checkBoxPanel.isButtonHide())
            {
                height += 40;
            }
            else
            {
                height += checkBoxPanel.getSize().height;
            }
        }

        if (height < 615)
        {
            filterPanel.remove(filterPanel.getComponentCount() - 1);
            filterPanel.add(Box.createVerticalStrut(615 - height));
        }
        else
        {
            filterPanel.remove(filterPanel.getComponentCount() - 1);
            filterPanel.add(Box.createVerticalStrut(0));
        }
    }

    private void addListeners()
    {
        // search panel
        addSearchPanelListeners();
        addSortPanelListeners();
        // filter panel
        addResetFilterListeners();
        // list panel
        listpanel.addListeners();

        BrowseWindowListener m = new BrowseWindowListener();
        this.addWindowListener(m);

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

    private void resetFilter()
    {
        resetFilterCheckBox();
        updateFilterConditions();
    }

    private void addResetFilterListeners()
    {
        resetFilter.addActionListener(new FilterResetListener());
    }

    private void resetFilterCheckBox()
    {
        for (int i = 0; i < checkBoxPanels.length; i++)
        {
            checkBoxPanels[i].clearAllChecked();
        }
    }

    public int getPage()
    {
        return page;
    }

    void setPage(int page)
    {
        this.page = page;
    }

    void displaytoList()
    {
        listpanel.displaytoList(toDisplay);
    }

    int getMaxPage()
    {
        return toDisplay.size() / perpage;
    }

    ImageIcon getIcon(Vehicle v)
    {
        return cache.get(v);
    }

    int getPerPage()
    {
        return perpage;
    }

    ArrayList<Vehicle> getVehiclestoDisplay()
    {
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
        /*   
         *    load the first page 15 images and then let the swing worker do the rest
         */
        
        for (int i=0;i<perpage;++i){
            Vehicle v=invsAPI.getVehicles().get(i);
            int random=(int) Math.round(2*Math.random());
            Image temp=invsAPI.getVehicleImage(v.getBodyType()).get(random);
            temp=temp.getScaledInstance(181, 181, Image.SCALE_DEFAULT); 
            cache.put(v, new ImageIcon(temp, "icon for vehicle " + v.getId()));
            toDisplay.add(v);
        }
        //updateThread t=new updateThread(); t.execute();  //TODO check this strange exception
        
        return;        
    }

    class updateThread extends SwingWorker<Void, Void>
    {

        @Override
        protected Void doInBackground() throws Exception
        {

            for (Vehicle v : invsAPI.getVehicles())
            {
                if (!cache.containsKey(v)){
                    int i=(int) Math.round(2*Math.random());
                    Image temp=invsAPI.getVehicleImage(v.getBodyType()).get(i);
                    temp=temp.getScaledInstance(181, 181, Image.SCALE_DEFAULT); 
                    cache.put(v, new ImageIcon(temp, "icon for vehicle " + v.getId()));
                    toDisplay.add(v);
                }                
            }            
            return null;
        }

    }

    private void makeThisVisible()
    {
        this.setVisible(true);
    }

    class FilterResetListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            resetFilter();
        }
    }

    public void updateFilterConditions()
    {
        String categoriesFilter = checkBoxPanels[0].generateFilterCondition();
        if (categoriesFilter != null)
        {
            categoriesFilter = categoriesFilter.trim().toLowerCase();
        }
        doFilter(categoriesFilter, checkBoxPanels[1].generateFilterCondition(),
                checkBoxPanels[2].generateFilterCondition(), checkBoxPanels[3].generateFilterCondition(),
                checkBoxPanels[4].generateFilterCondition());
    }

    private void updateFilterCheckBoxPanels()
    {
        for (int i = 0; i < checkBoxPanels.length; i++)
        {
            checkBoxPanels[i].populateCheckBoxes(checkBoxPanelsMap.get(filterKeys[i]));
        }
    }

    private void resetSortItem()
    {
        sortItem.setSelectedIndex(0);
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
        updateFilterConditions();
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
        checkBoxPanelsMap = InventoryServiceAPI_Test.getComboBoxItemsMap(toDisplay);
        setPage(0);
        resetFilterCheckBox();
        updateFilterCheckBoxPanels();
        displaytoList();

        resetSortItem();
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
