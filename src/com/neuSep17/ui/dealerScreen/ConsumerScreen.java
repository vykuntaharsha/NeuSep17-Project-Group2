package com.neuSep17.ui.dealerScreen;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConsumerScreen  extends JFrame{
    private JLabel first, second;
    private JButton browser;
    private JComboBox combobox;
    private DealerAPI dealerAPI = new DealerAPI("https://raw.githubusercontent.com/vykuntaharsha/NeuSep17-Project-Group2/master/data/dealers");
    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
    private ArrayList<String> dealerNameList = new ArrayList<>();

    public ConsumerScreen(){
        super();
        createComponents();
        addComponentsUsingGridBagLayout();
        addListener();
        makeThisVisible();
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

    }

    public void addComponentsUsingGridBagLayout(){
        Container con = getContentPane();
        GridBagLayout gridbag = new GridBagLayout();

        con.setLayout(gridbag);
        this.add(first);
        this.add(second);
        this.add(combobox);
        this.add(browser);

        GridBagConstraints s  = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 0.5;
        gridbag.setConstraints(first, s);
        s.gridwidth = 2;
        s.weightx = 0;
        s.weighty = 0.5;
        gridbag.setConstraints(second, s);
        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 0.5;
        gridbag.setConstraints(combobox, s);
        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 0.5;
        gridbag.setConstraints(browser, s);
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
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void makeThisVisible(){
        this.setTitle("Consumer Screen");
        this.setSize(1800,16000);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}





///**
// * Created by lbjfo on 11/28/2017.
// */
//public class ConsumerScreen extends JFrame{
//    private JLabel first, second;
//    private JButton browser;
//    private JComboBox combobox;
//    private DealerAPI dealerAPI = new DealerAPI("https://raw.githubusercontent.com/vykuntaharsha/NeuSep17-Project-Group2/master/data/dealers");
//    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
//    private ArrayList<String> dealerNameList = new ArrayList<>();
//
//    public ConsumerScreen(){
//        super();
//        createComponents();
//        addComponentsUsingGridLayout();
//        addListener();
//        makeThisVisible();
//    }
//
//    private void createComponents() {
//        Font f1 = new Font("Arial", Font.BOLD, 36);
//        Font f2 = new Font("Arial",Font.BOLD,24);
//
//        browser = new JButton("Browser cars");
//        first = new JLabel("Welcome", JLabel.CENTER);
//        first.setFont(f1);
//        second = new JLabel("Select Dealer", JLabel.CENTER);
//        second.setFont(f2);
//
//        for (Dealer d: dealerList){
//            dealerNameList.add(d.getId());
//        }
//
//        combobox=new JComboBox(dealerNameList.toArray());
//        combobox.setSelectedItem(null);
//
//    }
//
//    public void addComponentsUsingGridLayout(){
//        Container con = getContentPane();
//        GridLayout g1 = new GridLayout(4,1,10,10);
//        con.setLayout(g1);
//        con.add(first);
//        con.add(second);
//        con.add(combobox);
//        con.add(browser);
//    }
//
//    private void addListener() {
//        Browser browserCar = new Browser();
//        browser.addActionListener(browserCar);
//    }
//
//    public Dealer getSelectedDealer(){
//        String dealerID = combobox.getSelectedItem().toString();
//        return dealerAPI.getDealer(dealerID);
//    }
//
//    class Browser implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e){
//            if(combobox.getSelectedItem() != null){
//                //new DealerInventoryScreen();
//                System.out.println("You have choosed " + combobox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Inventory Screen");
//                dispose();
//            } else {
//                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//
//    public void makeThisVisible(){
//        this.setTitle("Consumer Screen");
//        this.setSize(500,500);
//        //this.setLayout(null);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//}
