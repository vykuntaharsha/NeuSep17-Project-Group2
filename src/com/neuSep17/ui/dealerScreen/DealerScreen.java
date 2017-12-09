package com.neuSep17.ui.dealerScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DealerScreen extends JFrame {

    private String selectedDearlerID;
    private JLabel screenTitle;
    private JButton mngInvButton, mngIncButton;


    public DealerScreen(String selectedDearlerID) throws HeadlessException {

        super();
        this.selectedDearlerID = selectedDearlerID;
        setTitle("DealerScreen");

        createComponents();
        addComponentsUsingGridBagLayout();
        addListener();
        makeVisible();
    }

    private void createComponents() {
        screenTitle = new JLabel("Welcome, " + selectedDearlerID + " !");
        screenTitle.setFont(new Font("Default", Font.BOLD, 60));
        screenTitle.setForeground(Color.BLACK);

        mngInvButton = new JButton("Manage Inventory");
        mngInvButton.setFont(new Font("Default", Font.PLAIN, 40));
        mngIncButton = new JButton("Manage Incentive");
        mngIncButton.setFont(new Font("Default", Font.PLAIN, 40));
    }

    private void addComponentsUsingGridBagLayout() {
        Container con = getContentPane();

        GridLayout layout = new GridLayout(4, 1,1,1);
        con.setLayout(layout);
        con.add(screenTitle);
        con.add(mngInvButton);
        con.add(mngIncButton);
    }

    private void addListener() {
        ManageInventory mngInv = new ManageInventory();
        ManageIncentive mngInc = new ManageIncentive();
        mngInvButton.addActionListener(mngInv);
        mngIncButton.addActionListener(mngInc);

    }

    class ManageInventory implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            new ManageInventoryScreen();
            System.out.println("You have choosed " + selectedDearlerID + ", Close Dealer Screen -> Open Dealer Inventory Screen");
            dispose();
        }
    }

    class ManageIncentive implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            new IncentiveAPP();
            System.out.println("You have choosed " + selectedDearlerID + ", Close Dealer Screen -> Open Dealer Incentive Screen");
            dispose();
        }
    }

    private void makeVisible() {
        setSize(1920, 1200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
