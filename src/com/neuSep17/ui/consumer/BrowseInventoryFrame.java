package com.neuSep17.ui.consumer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
import javax.swing.border.MatteBorder;
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
    private HashMap<Vehicle, Image> cache;
    private JScrollPane listScrollPane;
    private JPanel carList, listPanel;
    private static int page, perpage;
    // list end

    // filter start
    private Map<String, List<String>> comboBoxItemsMap;
    private JCheckBox[] categories;
    private JLabel[] filterLabels;
    private JComboBox<String>[] filters;
    private String[] filterKeys = { "year", "make", "price", "type" };
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
        // incsAPI = new IncentiveServiceAPI_Test();
        // dealerID= dealer.getID();
        page = 0;
        perpage = 15;

        this.cache = new HashMap<Vehicle, Image>();
        this.toDisplay = new ArrayList<Vehicle>();
        try
        {
            updateVehicle();
        }
        catch (IOException e)
        {            
            e.printStackTrace();
        }

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
        createListPanelComponents();
    }

    private void createSearchPanelComponents()
    {
        search = new JButton("Search");
        sortBy = new JLabel("Sort by : ");

        searchText = new JTextField(10);

        sortItem = new JComboBox<String>();
        sortItem.addItem("Select Sort By");
        sortItem.addItem("Price: High To Low");
        sortItem.addItem("Price: Low To High");
        sortItem.addItem("Year: High To Low");
        sortItem.addItem("Year: Low To High");
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
            filters[i] = new JComboBox<String>();
            filters[i].setName(filterKeys[i]);
        }
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

        addListPanelComponents();
        con.add(listPanel, BorderLayout.CENTER);
    }

    private void addSearchPanelComponents(JPanel searchPanel)
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
        filterPanel.add(Box.createVerticalStrut(30));
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

        filterPanel.add(comboBoxPanel);
        filterPanel.add(Box.createVerticalGlue());
        filterPanel.add(Box.createVerticalStrut(50));
    }

    private void addListeners()
    {
        // search panel
        addSearchPanelListeners();
        // filter panel
        addFilterPanelListeners();
        // list panel
        addListPanelListeners();

        BrowseWindowListener m = new BrowseWindowListener();
        this.addWindowListener(m);

    }

    private void addSearchPanelListeners()
    {
        // TODO Auto-generated method stub

    }

    private void addFilterPanelListeners()
    {
        FilterListener fl = new FilterListener();
        for (int i = 0; i < categories.length; i++)
        {
            categories[i].addActionListener(fl);
        }

        for (int i = 0; i < filterLabels.length; i++)
        {
            filters[i].addActionListener(fl);
        }
    }

    private void createListPanelComponents()
    {
        listPanel = new JPanel();
        listPanel.setSize(new Dimension(this.getWidth() - 300, this.getHeight() - 200)); // TODO
                                                                                         // total
                                                                                         // width-filer,
                                                                                         // total
                                                                                         // height-search
        carList = new JPanel();
        carList.setSize(new Dimension(this.getWidth() - 300, this.getHeight() - 300)); // TODO
                                                                                       // total
                                                                                       // width-filer,
                                                                                       // total
                                                                                       // height-search

        listScrollPane = new JScrollPane();
    }

    private void addListPanelComponents()
    {
        carList.setBorder(BorderFactory.createLineBorder(Color.black));
        carList.setLayout(new BoxLayout(carList, BoxLayout.Y_AXIS));
        listScrollPane.setViewportView(carList);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.add(listScrollPane);
        listPanel.add(new pagePane(toDisplay.size() / perpage));
        displaytoList();
    }

    private void displaytoList()
    { // TODO put the car in toDisplay after searching/filtering/sorting/, set
      // page=0, then invoke this method

        carList.removeAll();
        carList.revalidate();
        listScrollPane.getVerticalScrollBar().setValue(0);
        Vehicle v;
        int n;
        for (int i = 0; i < perpage; ++i)
        {
            n = page * perpage + i;
            if (n >= toDisplay.size())
                break;
            v = toDisplay.get(n);
            carList.add(new vehicleCell(v, createTestImageIcon(cache.get(v), "test"))); // TODO
                                                                                        // use
                                                                                        // normal
                                                                                        // icon
                                                                                        // generator
        }

        listPanel.remove(1);
        listPanel.revalidate();
        listPanel.add(new pagePane(toDisplay.size() / perpage));
    }

    private void addListPanelListeners()
    {
        // updateFinishedListener updateFinished=new updateFinishedListener();
    }

    class pagePane extends JPanel
    {

        pagePane(int maxPage)
        {
            super();
            JButton first = new JButton("<<");
            JButton pre = new JButton("<");
            JLabel cur = new JLabel("Page: " + (page + 1));
            JButton next = new JButton(">");
            JButton last = new JButton(">>");
            this.add(first);
            this.add(pre);
            this.add(cur);
            this.add(next);
            this.add(last);
            this.setBorder(new MatteBorder(0, 2, 0, 0, Color.BLACK));

            if (page == 0)
            {
                pre.setEnabled(false);
                first.setEnabled(false);
            }
            if (page == maxPage)
            {
                next.setEnabled(false);
                last.setEnabled(false);
            }

            first.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("to first page: 1");
                    page = 0;
                    displaytoList();
                }
            });

            pre.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("to previous page: " + page);
                    --page;
                    displaytoList();
                }
            });

            next.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("to next page: " + (page + 2));
                    ++page;
                    displaytoList();
                }
            });

            last.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("to last page: " + (maxPage + 1));
                    page = maxPage;
                    displaytoList();
                }
            });

        }
    }

    class updateFinishedListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {

        }
    }

    private void updateVehicle() throws IOException
    {       
        
        URL imgURL = new URL("http://inventory-dmg.assets-cdk.com/chrome_jpgs/2016/24174x90.jpg");
        Image temp = ImageIO.read(imgURL);
        for (Vehicle v : invsAPI.getVehicles())
        {
            
            cache.put(v, temp);
            toDisplay.add(v);
        }
        return;

        /*
         * updateThread t=new updateThread(); t.execute(); while(t.isDone()) {}
         * // this waits until update finish
         */
    }

    protected ImageIcon createTestImageIcon(Image img, String description)
    {
        return new ImageIcon(img, description);
    }

    protected ImageIcon createImageIcon(URL imgURL, String description) throws IOException
    {
        Image img;

        try
        {
            img = ImageIO.read(imgURL);
        }
        catch (Exception e)
        {
            img = ImageIO.read(new File("src/com/neuSep17/ui/consumer/imagenotfound.jpg"));
        }

        return new ImageIcon(img, description);
    }

    class updateThread extends SwingWorker<Void, Void>
    {

        @Override
        protected Void doInBackground() throws Exception
        {
            Image img;
            for (Vehicle v : invsAPI.getVehicles())
            {
                try
                {
                    img = ImageIO.read(v.getPhotoUrl());
                }
                catch (Exception e)
                {
                    img = ImageIO.read(new File("src/com/neuSep17/ui/consumer/imagenotfound.jpg"));
                }
                cache.put(v, img);
            }
            return null;
        }

    }

    private void makeThisVisible()
    {
        this.setVisible(true);
    }

    class FilterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateFilter();
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
        for (int i = 0; i < filters.length; i++)
        {
            filters[i].removeAllItems();
            filters[i].addItem("all");
            List<String> comboBoxItems = comboBoxItemsMap.get(filters[i].getName());
            for (String item : comboBoxItems)
            {
                filters[i].addItem(item);
            }
        }
    }

    private void doSearch(String search)
    {
        searchedVehicles = InventoryServiceAPI_Test.vehiclesSearchAndFilter(searchedVehicles, null, null, null, null, null, search);
        toDisplay = (ArrayList<Vehicle>) searchedVehicles;
        comboBoxItemsMap = InventoryServiceAPI_Test.getComboBoxItemsMap(toDisplay);
        updateFilterComboboxItems();
    }

    private void doFilter(String category, String year, String make, String price, String type)
    {
        toDisplay = (ArrayList<Vehicle>) InventoryServiceAPI_Test.vehiclesSearchAndFilter(searchedVehicles, category, year, make, price,
                type, null);
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
