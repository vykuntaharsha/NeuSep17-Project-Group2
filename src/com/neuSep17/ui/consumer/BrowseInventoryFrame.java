package com.neuSep17.ui.consumer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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

import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Vehicle;
import java.awt.FlowLayout;

public class BrowseInventoryFrame extends JFrame implements Runnable{
    
    private String dealerID;
    private HashMap<Vehicle, Image> cache;
    private JList<Vehicle> displayList;
    private JScrollPane listScrollPane;
    private JPanel listPanel;
    
    public BrowseInventoryFrame() //(Dealer dealer)
    {
        super(); 
        setTitle("Browse Inventory of xx dealer");
        //dealerID= dealer.getID();
        
        this.cache=new HashMap<Vehicle, Image>();
        try {
            updateVehicle();
        } catch (IOException e) {
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
        // TODO Auto-generated method stub

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
    }

    private void addFilterPanelComponents(JPanel filterPanel)
    {
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.black));
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
        // TODO Auto-generated method stub

    }

    
    
    private void createListPanelComponents(){
        listPanel = new JPanel();        
        listPanel.setSize(new Dimension(this.getWidth()-300, this.getHeight()-200));
        
        displayList = new JList(cache.keySet().toArray());
        displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        displayList.setLayoutOrientation(JList.VERTICAL);
        displayList.setFixedCellHeight(100);
        
        int listPanelwidth=listPanel.getWidth();
        int listPanelheight=listPanel.getHeight();
        
        displayList.setCellRenderer(new vehicleCellRenderer());
        listScrollPane=new JScrollPane();
        listScrollPane.setViewportView(displayList);
        listScrollPane.setPreferredSize(new Dimension(listPanelwidth,listPanelheight));

    }
    
    private void addListPanelComponents()
    {
        listPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        listPanel.add(listScrollPane);
    }
    
    private void addListPanelListeners(){
        
        //updateFinishedListener updateFinished=new updateFinishedListener();       

    }
    
    class vehicleCellRenderer implements ListCellRenderer<Vehicle>{
        

        @Override
        public Component getListCellRendererComponent(JList<? extends Vehicle> list, Vehicle v, int index, 
                boolean isSelected, boolean cellHasFocus) {
            
            JPanel specPane=new JPanel();
            specPane.setLayout(new BoxLayout(specPane, BoxLayout.Y_AXIS));
            
            JLabel nameLabel=new JLabel(v.getYear()+" "+v.getMake()+" "+v.getModel());
            nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.BOLD, 24));
            
            JLabel priceLabel=new JLabel("price: "+v.getPrice().toString());
            JLabel categoryLabel=new JLabel("category: "+v.getCategory().toString());
            JLabel trimLabel=new JLabel("trim: "+v.getTrim());
            JLabel typeLabel=new JLabel("type: "+v.getBodyType());            
            
            ImageIcon icon = createTestImageIcon(cache.get(v),"test");            
            JLabel image=new JLabel(icon);
            
            specPane.add(nameLabel);
            specPane.add(priceLabel);
            specPane.add(categoryLabel);
            specPane.add(trimLabel);
            specPane.add(typeLabel);
            
            JSplitPane cell = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, image, specPane);
            cell.setDividerLocation(150);
            //cell.setSize(list.getWidth(), 100);
            return cell;
        }
        
    }
    
    class updateFinishedListener implements PropertyChangeListener{
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            
        }        
    }
    
    private void updateVehicle() throws IOException {
        
        File file = new File("D:/PROF/GIT/INFO5100/INFO5100/assignment8/q3.txt");
        ArrayList<Vehicle> cars=new ArrayList<>();
        FileReader fr=new FileReader(file);
        BufferedReader br=new BufferedReader(fr);
        String line=br.readLine();
        while(true){
            line=br.readLine();
            if(line==null) break;            
            Vehicle cur=new Vehicle(line.split("~"));
            cars.add(cur);
        }
        br.close();
        fr.close(); 
        URL imgURL=new URL("http://inventory-dmg.assets-cdk.com/chrome_jpgs/2016/24174x90.jpg");
        Image temp=ImageIO.read(imgURL);
        for(Vehicle v: cars) {
            //cache.put(v, ImageIO.read(v.getPhotoUrl()) );  
            cache.put(v, temp);
        }
        return;
        
        /*
        updateThread t=new updateThread();
        t.execute();
        while(t.isDone()) {}            // this waits until update finish
        */
    }
    
    protected ImageIcon createTestImageIcon(Image img, String description) {
        return new ImageIcon(img, description);
    }
    
    
    protected ImageIcon createImageIcon(URL imgURL, String description) throws IOException {        
        if (imgURL != null) {
            Image img=ImageIO.read(imgURL);
            img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + imgURL);
            return null;
        }
    }
    
    class updateThread extends SwingWorker<Void,Void> {

        @Override
        protected Void doInBackground() throws Exception {
            for(Vehicle v: getInventoryofDealer()) {
                cache.put(v, ImageIO.read(v.getPhotoUrl()) );   //null url situation?
            }
            return null;
        }

        private ArrayList<Vehicle> getInventoryofDealer() throws IOException {
            File file = new File("assignment8/q3.txt");
            ArrayList<Vehicle> cars=new ArrayList<>();
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            String line=br.readLine();
            while(true){
                line=br.readLine();
                if(line==null) break;            
                Vehicle cur=new Vehicle(line.split("~"));
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
    
    class BrowseWindowListener implements WindowListener {

        @Override
        public void windowActivated(WindowEvent arg0) {
            
        }

        @Override
        public void windowClosed(WindowEvent e) {
            
        }

        @Override
        public void windowClosing(WindowEvent e) {           
            System.out.println("BrowseInventory closing, back to consumer window.");
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
            
        }

        @Override
        public void windowOpened(WindowEvent e) {
        }
        
    }

    public static void main(String[] args)
    {        
        BrowseInventoryFrame bif = new BrowseInventoryFrame();
        Thread BrowseInventoryThread = new Thread(() -> bif.run() );
        SwingUtilities.invokeLater(BrowseInventoryThread);
    }

    @Override
    public void run() {        
        makeThisVisible();        
    }
}
