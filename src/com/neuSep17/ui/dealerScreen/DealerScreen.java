package com.neuSep17.ui.dealerScreen;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DealerScreen extends JFrame {

    private JLabel screenTitle, selectDealerLabel;
    private JComboBox dealerComboBox;
    private JButton mngInvButton, mngIncButton;
    private DealerAPI dealerAPI = new DealerAPI("https://raw.githubusercontent.com/vykuntaharsha/NeuSep17-Project-Group2/master/data/dealers");
    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
    private ArrayList<String> dealerNameList = new ArrayList<>();


    public DealerScreen() throws HeadlessException {

        super();
        setTitle("DealerScreen");


        createComponents();
        addComponentsUsingGridBagLayout();
        addListener();
        makeVisible();
    }

    private void createComponents() {
        screenTitle = new JLabel("Welcome , Manager !");
        screenTitle.setFont(new Font("Default", Font.BOLD, 60));
        screenTitle.setForeground(Color.BLACK);

        selectDealerLabel = new JLabel("Selcet My Car Plans");
        selectDealerLabel.setFont(new Font("Default", Font.ITALIC, 40));
        selectDealerLabel.setForeground(Color.BLACK);

        for (Dealer d: dealerList){
            dealerNameList.add(d.getId());
        }

        dealerComboBox = new JComboBox(dealerNameList.toArray());
        dealerComboBox.setSelectedItem(null);
        dealerComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        mngInvButton = new JButton("Manage Inventory");
        mngInvButton.setFont(new Font("Default", Font.PLAIN, 40));
        mngInvButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mngIncButton = new JButton("Manage Incentive");
        mngIncButton.setFont(new Font("Default", Font.PLAIN, 40));
        mngIncButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void addComponentsUsingGridBagLayout() {

        GridBagLayout layout = new GridBagLayout();

        this.setLayout(layout);
        this.add(screenTitle);
        this.add(selectDealerLabel);
        this.add(dealerComboBox);
        this.add(mngInvButton);
        this.add(mngIncButton);

        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;

        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 1;
        layout.setConstraints(screenTitle, s);
        s.gridwidth = 2;
        s.weightx = 0;
        s.weighty = 0.5;
        layout.setConstraints(selectDealerLabel, s);
        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 0.5;
        layout.setConstraints(dealerComboBox, s);
        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 0.5;
        layout.setConstraints(mngInvButton, s);
        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 0.5;
        layout.setConstraints(mngIncButton, s);
    }

    private void addListener() {
        ManageInventory mngInv = new ManageInventory();
        ManageIncentive mngInc = new ManageIncentive();
        mngInvButton.addActionListener(mngInv);
        mngIncButton.addActionListener(mngInc);

    }

    public Dealer getSelectedDealer(){
        String dealerID = dealerComboBox.getSelectedItem().toString();
        return dealerAPI.getDealer(dealerID);
    }

    class ManageInventory implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (dealerComboBox.getSelectedItem() != null) {
                //new ManageInventoryScreen();
                System.out.println("You have choosed " + dealerComboBox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Inventory Screen");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ManageIncentive implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (dealerComboBox.getSelectedItem() != null) {
                //new ManageIncentiveScreen();
                System.out.println("You have choosed " + dealerComboBox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Incentive Screen");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void makeVisible() {
        setSize(1920, 1200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}



//    private void createComponents() {
//        screenTitle = new JLabel("Welcome to Dearler Screen", JLabel.CENTER);
//        screenTitleFont = new Font("Default", Font.BOLD, 14);
//        screenTitle.setFont(screenTitleFont);
//        selectDealerLabel = new JLabel("Please Select a Dealer", JLabel.CENTER);
//
//        for (Dealer d: dealerList){
//            dealerNameList.add(d.getId());
//        }
//        dealerComboBox = new JComboBox(dealerNameList.toArray());
//        dealerComboBox.setSelectedItem(null);
//
//        mngInvButton = new JButton("Manage Inventory");
//        mngIncButton = new JButton("Manage Incentive");
//    }
//
//    private void addComponentsUsingGridLayout() {
//        Container con = getContentPane();
//        GridLayout gl = new GridLayout(6, 1,1,1);
//        con.setLayout(gl);
//        addComponent(con);
//    }
//
//    private void addComponent(Container con) {
//        con.add(screenTitle);
//        con.add(selectDealerLabel);
//        con.add(dealerComboBox);
//        con.add(mngInvButton);
//        con.add(mngIncButton);
//    }
//
//    private void addListener() {
//        ManageInventory mngInv = new ManageInventory();
//        ManageIncentive mngInc = new ManageIncentive();
//        mngInvButton.addActionListener(mngInv);
//        mngIncButton.addActionListener(mngInc);
//
//    }
//
//    public Dealer getSelectedDealer(){
//        String dealerID = dealerComboBox.getSelectedItem().toString();
//        return dealerAPI.getDealer(dealerID);
//    }
//
//    class ManageInventory implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e){
//            if(dealerComboBox.getSelectedItem() != null){
//                //new ManageInventoryScreen();
//                System.out.println("You have choosed " + dealerComboBox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Inventory Screen");
//                dispose();
//            } else {
//                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    class ManageIncentive implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if(dealerComboBox.getSelectedItem() != null){
//                //new ManageIncentiveScreen();
//                System.out.println("You have choosed " + dealerComboBox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Incentive Screen");
//                dispose();
//            } else {
//                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    private void makeVisible() {
//        setSize(500,500);
//        setVisible(true);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//}
