package com.neuSep17.ui.consumer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

public class BrowseInventoryFrame extends JFrame implements Runnable
{
    // service API start
    InventoryServiceAPI_Test invsAPI;
    IncentiveServiceAPI_Test incsAPI;
    // service API end

    private ArrayList<Vehicle> toDisplay;
    private List<Vehicle> searchedVehicles;

    // list
    private HashMap<Vehicle, ImageIcon> cache;
    ListPanel listpanel;
    private int perpage, page;
    private ImageIcon loadingIMG;
    // list end

    // filter start
    private FilterPanel filterPanel;
    // filter end

    // **search start***
    private SearchPanel searchPanel;
    // **search end***

    public BrowseInventoryFrame(InventoryServiceAPI_Test invsAPI)
    {
        setTitle("Browse Inventory of " + invsAPI.getFileName().split("/")[1] + " dealer");

        this.invsAPI = invsAPI;
        incsAPI = new IncentiveServiceAPI_Test("data/IncentiveSample.txt");
        this.cache = new HashMap<Vehicle, ImageIcon>();
        this.toDisplay = new ArrayList<Vehicle>();
        setPage(0);
        perpage = 15;

        getLoadingIMG();

        updateVehicle();

        createComponents();
        addComponents();
        doSearch(null);
        addListeners();
    }

    private void getLoadingIMG()
    {

        File temp = new File("data/images/loading.gif");

        try
        {
            URL url = temp.toURI().toURL();
            loadingIMG = new ImageIcon(url);
        }
        catch (MalformedURLException e)
        {
            loadingIMG = null;
            e.printStackTrace();
        }

    }

    private void createComponents()
    {
        // search panel
        searchPanel = new SearchPanel(this, invsAPI.getVehicles());
        // filter panel
        filterPanel = new FilterPanel(this);
        // list panel
        listpanel = new ListPanel(this);
    }

    private void addComponents()
    {
        Container con = getContentPane();
        con.setLayout(new BorderLayout(1, 1));

        // search panel
        searchPanel.setPreferredSize(new Dimension(1200, 200));
        con.add(searchPanel, BorderLayout.NORTH);

        // filter panel
        con.add(filterPanel, BorderLayout.WEST);
        // list panel

        con.add(listpanel, BorderLayout.CENTER);
    }

    private void addListeners()
    {
        // search panel
        searchPanel.addListeners();
        // filter panel
        filterPanel.addListeners();
        // list panel
        listpanel.addListeners();

        BrowseWindowListener m = new BrowseWindowListener();
        this.addWindowListener(m);
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
        if (cache.containsKey(v))
        { // just reading, thread safe
            return cache.get(v);
        }
        else
            return loadingIMG;
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
         * load the first page 15 images and then let the swing worker do the
         * rest
         */
        Image notfound;
        try
        {
            notfound = ImageIO.read(new File("src/com/neuSep17/ui/consumer/imagenotfound.jpg"));
        }
        catch (IOException e)
        {
            notfound = null;
            e.printStackTrace();
        }

        Image temp;
        for (int i = 0; i < perpage; ++i)
        {
            Vehicle v = invsAPI.getVehicles().get(i);
            int random = (int) Math.round(2 * Math.random());
            try
            {
                temp = InventoryServiceAPI_Test.getVehicleImage(v.getBodyType()).get(random);
            }
            catch (IOException e1)
            {
                temp = notfound;
                e1.printStackTrace();
            }
            temp = temp.getScaledInstance(181, 181, Image.SCALE_DEFAULT);
            cache.put(v, new ImageIcon(temp, "icon for vehicle " + v.getId()));
            toDisplay.add(v);
        }
        updateThread t = new updateThread();
        t.execute();
        searchedVehicles = toDisplay;
        return;
    }

    class updateThread extends SwingWorker<Void, Void>
    {
        @Override
        protected Void doInBackground() throws Exception
        {
            for (Vehicle v : invsAPI.getVehicles())
            {
                if (!cache.containsKey(v))
                {
                    int i = (int) Math.round(2 * Math.random());
                    Image temp = InventoryServiceAPI_Test.getVehicleImage(v.getBodyType()).get(i);
                    temp = temp.getScaledInstance(181, 181, Image.SCALE_DEFAULT);
                    cache.put(v, new ImageIcon(temp, "icon for vehicle " + v.getId()));
                }
            }
            return null;
        }

        protected void done()
        {
            System.out.println("update finished");
        }
    }

    private void makeThisVisible()
    {
        this.setSize(1200, 800);
        this.setMinimumSize(new Dimension(900, 600));
        this.setVisible(true);
    }

    private void resetSortItem()
    {
        searchPanel.getSortItem().setSelectedIndex(0);
    }

    public void doSort(String sortMethod)
    {
        if (sortMethod == null)
        {
            return;
        }

        boolean isAscending = true;
        if (sortMethod.contains(searchPanel.sortKeys[0]))
        {
            return;
        }

        String[] sortMethodSplit = sortMethod.split(":");
        String sortType = sortMethodSplit[0].toLowerCase().trim();

        isAscending = updateSortType(sortMethodSplit[1]);
        // System.out.println(sortMethod + " + " + sortType.toString() + " + " +
        // isAscending);

        searchedVehicles = invsAPI.sortVehicles(searchedVehicles, sortType, isAscending);
        filterPanel.updateFilterConditions("");
        displaytoList();
    }

    private boolean updateSortType(String ascendDescribe)
    {
        if (ascendDescribe.toLowerCase().contains("high to low") || ascendDescribe.toLowerCase().contains("z - a"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void doSearch(String search)
    {
        searchedVehicles = InventoryServiceAPI_Test.vehiclesSearchAndFilter(invsAPI.getVehicles(), null, null, null,
                null, null, search);
        toDisplay = (ArrayList<Vehicle>) searchedVehicles;
        setPage(0);
        filterPanel.setCheckBoxPanelsMap(InventoryServiceAPI_Test.getComboBoxItemsMap(toDisplay));
        resetSortItem();
        displaytoList();
    }

    public void doFilter(String category, String year, String make, String price, String type, String currentChecked)
    {
        toDisplay = (ArrayList<Vehicle>) InventoryServiceAPI_Test.vehiclesSearchAndFilter(searchedVehicles, category,
                year, make, price, type, null);
        setPage(0);
        filterPanel.setEnableCheckBoxMap(InventoryServiceAPI_Test.getComboBoxItemsMap(toDisplay), currentChecked);
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
            System.out.println("BrowseInventory closing, exit application.");
            System.exit(0);
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
        BrowseInventoryFrame bif = new BrowseInventoryFrame(new InventoryServiceAPI_Test("data/gmps-bresee"));
        Thread BrowseInventoryThread = new Thread(() -> bif.run());
        SwingUtilities.invokeLater(BrowseInventoryThread);
    }

    @Override
    public void run()
    {
        makeThisVisible();
    }

}
