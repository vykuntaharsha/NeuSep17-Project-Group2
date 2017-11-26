package com.neuSep17.ui.consumer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.neuSep17.dto.Vehicle;

import java.awt.FlowLayout;

public class BrowseInventoryFrame extends JFrame implements Runnable
{

    private String dealerID;
    private HashMap<Vehicle, Image> cache;
    private ArrayList<Vehicle> toDisplay;
    private JScrollPane listScrollPane;
    private JPanel listPanel;
    private int page, perpage;

    // filter start
    private JCheckBox[] categories;
    private JLabel[] filterLabels;
    private JComboBox[] filters;
    private String[] data = { "all", "test1", "test2", "test3" };// Simulation
                                                                 // data
    // filter end

    public BrowseInventoryFrame() // (Dealer dealer)
    {
        super();
        setTitle("Browse Inventory of xx dealer");
        // dealerID= dealer.getID();
        page=0;
        perpage=15;
        

        this.cache = new HashMap<Vehicle, Image>();
        this.toDisplay=new ArrayList<Vehicle>();
        try
        {
            updateVehicle();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.setSize(1200, 700);

        createComponents();
        addComponents();
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
        // TODO Auto-generated method stub

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
        filterLabels[0] = new JLabel("Year");
        filterLabels[1] = new JLabel("Make");
        filterLabels[2] = new JLabel("Price");
        filterLabels[3] = new JLabel("Type");
        filters = new JComboBox[4];
        filters[0] = new JComboBox<String>();
        filters[0].setName("year");
        filters[1] = new JComboBox<String>();
        filters[1].setName("make");
        filters[2] = new JComboBox<String>();
        filters[2].setName("price");
        filters[3] = new JComboBox<String>();
        filters[3].setName("type");

        // Simulation data
        for (int i = 0; i < data.length; i++)
        {
            filters[0].addItem("0" + data[i]);
            filters[1].addItem("1" + data[i]);
            filters[2].addItem("2" + data[i]);
            filters[3].addItem("3" + data[i]);
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
        con.add(listScrollPane, BorderLayout.CENTER);
    }

    private void addSearchPanelComponents(JPanel searchPanel)
    {
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.black));
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
        listPanel.setSize(new Dimension(this.getWidth() - 300, this.getHeight() - 200));  // TODO total width-filer, total height-search
        
        listScrollPane = new JScrollPane(); 
    }

    private void addListPanelComponents()
    {
        listPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        
        Vehicle v;
        for (int i=0;i<perpage;++i) {
            v=toDisplay.get(i);
            listPanel.add(new vehicleCell(v, createTestImageIcon(cache.get(v), "test")));  //TODO use normal icon generator
            
        }
        listPanel.add(new pagePane(page, toDisplay.size()/perpage));
        listScrollPane.setViewportView(listPanel);
        
    }

    private void addListPanelListeners()
    {
        // updateFinishedListener updateFinished=new updateFinishedListener();        
    }
    
    class pagePane extends JPanel{
        
        pagePane(int curPage, int maxPage){
            super();
            JButton pre=new JButton("Previous");
            JLabel cur=new JLabel("Page: "+(curPage+1));
            JButton next=new JButton("Next");
            this.add(pre);
            this.add(cur);
            this.add(next);
            this.setBorder(new MatteBorder(3, 0, 0, 0, Color.BLACK));
            
            if (curPage==0) pre.setEnabled(false);
            if (curPage==maxPage) next.setEnabled(false);
            pre.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("to previous page: "+page);                    
                        --page;
                }
            });
            
            next.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("to next page: "+(page+2));
                    
                    ++page;
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

        File file = new File("data/gmps-bresee");
        ArrayList<Vehicle> cars = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (true)
        {
            line = br.readLine();
            if (line == null)
                break;
            Vehicle cur = new Vehicle(line.split("~"));
            cars.add(cur);
        }
        br.close();
        fr.close();
        URL imgURL = new URL("http://inventory-dmg.assets-cdk.com/chrome_jpgs/2016/24174x90.jpg");
        Image temp = ImageIO.read(imgURL);
        for (Vehicle v : cars)
        {
            // cache.put(v, ImageIO.read(v.getPhotoUrl()) );
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
        
        try {
            img = ImageIO.read(imgURL);
        }
        catch (Exception e) {
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
            for (Vehicle v : getInventoryofDealer())
            {
                try {
                    img = ImageIO.read(v.getPhotoUrl());
                }
                catch (Exception e) {
                    img = ImageIO.read(new File("src/com/neuSep17/ui/consumer/imagenotfound.jpg"));
                }
                cache.put(v, img); 
            }
            return null;
        }

        private ArrayList<Vehicle> getInventoryofDealer() throws IOException
        {
            File file = new File("assignment8/q3.txt");              //TODO test version
            ArrayList<Vehicle> cars = new ArrayList<>();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (true)
            {
                line = br.readLine();
                if (line == null)
                    break;
                Vehicle cur = new Vehicle(line.split("~"));
                cars.add(cur);
            }
            br.close();
            fr.close();
            return cars;
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
        StringBuilder sb = new StringBuilder();
        int count = 0;
        sb.append("categories:");
        for (int i = 0; i < categories.length; i++)
        {
            if (categories[i].isSelected())
            {
                sb.append(categories[i].getName()).append(",");
                count++;
            }
        }
        if (count == 0 || count == categories.length)
        {
            sb.setLength(0);
        }
        else
        {
            sb.deleteCharAt(sb.length() - 1);
            sb.append(";");
        }

        for (int i = 0; i < filters.length; i++)
        {
            if (filters[i].getSelectedIndex() == 0)
            {
                continue;
            }
            else
            {
                sb.append(filters[i].getName()).append(":").append(filters[i].getSelectedItem()).append(";");
            }
        }
        System.out.println(sb.toString());
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
