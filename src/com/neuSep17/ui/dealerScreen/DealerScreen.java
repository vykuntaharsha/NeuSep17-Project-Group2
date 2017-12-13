package com.neuSep17.ui.dealerScreen;

import com.neuSep17.ui.inventoryList.CenterSection.DealerMainWindow;
import com.neuSep17.ui.manageIncentives.IncentiveMainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class DealerScreen extends JFrame {

    private String selectedDearlerID;
    private String screenTitleText;
    private JLabel screenTitle;
    private JButton mngInvButton, mngIncButton;


    public DealerScreen(String selectedDearlerID) throws HeadlessException, FileNotFoundException, UnsupportedEncodingException {

        super();
        this.selectedDearlerID = selectedDearlerID;
        setTitle("Dealer Screen");
        createComponents();
        addComponentsUsingGridBagLayout();
        addListener();
        makeVisible();
    }

    private void createComponents() {
        screenTitleText = "Welcome, " + selectedDearlerID + " !";
        screenTitle = new JLabel(screenTitleText, JLabel.CENTER);
        screenTitle.setFont(new Font("Default", Font.BOLD, 55));
        screenTitle.setForeground(Color.DARK_GRAY);

        mngInvButton = new JButton("Manage Inventory");
        mngInvButton.setFont(new Font("Default", Font.PLAIN, 20));
        mngInvButton.setForeground(Color.DARK_GRAY);

        mngIncButton = new JButton("Manage Incentive");
        mngIncButton.setFont(new Font("Default", Font.PLAIN, 20));
        mngIncButton.setForeground(Color.DARK_GRAY);
    }

    private void addComponentsUsingGridBagLayout() throws FileNotFoundException, UnsupportedEncodingException {

        ImageIcon backgroundImage = new ImageIcon("data/images/DealerBackground.jpg");

        JLabel backgroundLabel = new JLabel(backgroundImage);
        this.add(backgroundLabel);

        backgroundLabel.setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        backgroundLabel.setLocation(0, 0);
        backgroundLabel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 200, 0);
        backgroundLabel.add(screenTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.ipadx = 40;
        gbc.ipady = 15;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 250, 0, 250);
        backgroundLabel.add(mngInvButton, gbc);

        gbc.gridwidth =1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 2;
        backgroundLabel.add(mngIncButton, gbc);
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
//          waiting Team6's final version
            try {
                new DealerMainWindow(selectedDearlerID);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("You have choosed " + selectedDearlerID + ", Close Dealer Screen -> Open Dealer Inventory Screen");
        }
    }

    class ManageIncentive implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//          waiting Team2's final version
            try {
                new IncentiveMainFrame();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("You have choosed " + selectedDearlerID + ", Close Dealer Screen -> Open Dealer Incentive Screen");
        }
    }

    private void makeVisible() {

        setSize(1920, 1200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
