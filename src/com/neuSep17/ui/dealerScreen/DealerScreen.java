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
    private Font screenTitleFont;
    private JComboBox dealerComboBox;
    private JButton mngInvButton, mngIncButton;
    private DealerAPI dealerAPI = new DealerAPI("/Users/kevinshi721/GitHub/NeuSep17-Project-Group2/data/dealers");
    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
    private ArrayList<String> dealerNameList = new ArrayList<>();


    public DealerScreen() throws HeadlessException {

        super();
        setTitle("DealerScreen");

        createComponents();
        addComponentsUsingGridLayout();
        addListener();
        makeVisible();
    }

    private void createComponents() {
        screenTitle = new JLabel("Welcome to Dearler Screen", JLabel.CENTER);
        screenTitleFont = new Font("Default", Font.BOLD, 14);
        screenTitle.setFont(screenTitleFont);
        selectDealerLabel = new JLabel("Please Select a Dealer", JLabel.CENTER);

        for (Dealer d: dealerList){
            dealerNameList.add(d.getId());
        }
        dealerComboBox = new JComboBox(dealerNameList.toArray());
        dealerComboBox.setSelectedItem(null);

        mngInvButton = new JButton("Manage Inventory");
        mngIncButton = new JButton("Manage Incentive");
    }

    private void addComponentsUsingGridLayout() {
        Container con = getContentPane();
        GridLayout gl = new GridLayout(6, 1,1,1);
        con.setLayout(gl);
        addComponent(con);
    }

    private void addComponent(Container con) {
        con.add(screenTitle);
        con.add(selectDealerLabel);
        con.add(dealerComboBox);
        con.add(mngInvButton);
        con.add(mngIncButton);
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
        public void actionPerformed(ActionEvent e){
            if(dealerComboBox.getSelectedItem() != null){
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
            if(dealerComboBox.getSelectedItem() != null){
                //new ManageIncentiveScreen();
                System.out.println("You have choosed " + dealerComboBox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Incentive Screen");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void makeVisible() {
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
