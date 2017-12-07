package com.neuSep17.ui.dealerScreen;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;
import com.neuSep17.service.InventoryServiceAPI_Test;
import com.neuSep17.ui.consumer.BrowseInventoryFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static com.neuSep17.ui.dealerScreen.DealerScreen.GImage;

public class ConsumerScreen  extends JFrame{
    JFrame jframe = new JFrame();
    private JLabel first, second;
    private JButton browser, empty[];
    private JComboBox combobox;
    private DealerAPI dealerAPI = new DealerAPI("https://raw.githubusercontent.com/vykuntaharsha/NeuSep17-Project-Group2/master/data/dealers");
    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
    private ArrayList<String> dealerNameList = new ArrayList<>();

    public ConsumerScreen(){
        super();

        GImage = new JPanel() {
            protected void paintComponent (Graphics g) {
                ImageIcon icon = new ImageIcon("/Users/kevinshi721/GitHub/NeuSep17-Project-Group2/src/com/neuSep17/ui/dealerScreen/background1.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
                jframe.setSize(icon.getIconWidth(), icon.getIconHeight());
            }
        };
        jframe.add(GImage);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("ConsumerScreen");

        createComponents();
        addComponentsUsingGridBagLayout();
        addListener();
//        makeThisVisible();
    }

    private void createComponents() {

        browser = new JButton("Browse Super Good Cars here !");
        browser.setFont(new Font("Default", Font.BOLD, 40));
        browser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        first = new JLabel ("Best Price, Best Service ! ");
        first.setFont(new Font("Default",Font.BOLD, 60));
        first.setForeground(Color.BLACK);

        second = new JLabel("Select My Dealer");
        second.setFont(new Font("Default",Font.ITALIC, 40));
        second.setForeground(Color.BLACK);

        for (Dealer d: dealerList){
            dealerNameList.add(d.getId());
        }

        combobox=new JComboBox(dealerNameList.toArray());
        combobox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        combobox.setSelectedItem(null);

        empty = new JButton[2];
        for(int i = 0; i < 2; i ++) {
            empty[i] = new JButton("");
            empty[i].setContentAreaFilled(false);
            empty[i].setBorderPainted(false);
        }

    }

    public void addComponentsUsingGridBagLayout(){

        Container con = getContentPane();
        GridBagLayout gridbag = new GridBagLayout();

        con.setLayout(gridbag);
        GImage.setLayout(new GridLayout(6,1));

        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(1,2));
        pan.setOpaque(false);
        pan.add(second);
        pan.add(combobox);

        GImage.add(first);
        GImage.add(empty[0]);
        GImage.add(pan);
        GImage.add(empty[1]);
        GImage.add(browser);
    }

    private void addListener() {
        Browser browserCar = new Browser();
        browser.addActionListener(browserCar);
    }

    public Dealer getSelectedDealer(){
        String dealerID = combobox.getSelectedItem().toString();
        return dealerAPI.getDealer(dealerID);
    }

    class Browser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            if(combobox.getSelectedItem() != null){
                //new DealerInventoryScreen();
                System.out.println("You have choosed " + combobox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Inventory Screen");
                BrowseInventoryFrame bif = new BrowseInventoryFrame(new InventoryServiceAPI_Test("data/" + combobox.getSelectedItem()));
                Thread BrowseInventoryThread = new Thread(() -> bif.run());
                SwingUtilities.invokeLater(BrowseInventoryThread);

                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void makeThisVisible(){
        this.setTitle("Consumer Screen");
        this.setSize(1024,768);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}