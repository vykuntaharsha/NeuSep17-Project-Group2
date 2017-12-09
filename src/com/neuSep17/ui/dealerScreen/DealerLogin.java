package com.neuSep17.ui.dealerScreen;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DealerLogin extends JFrame {

    public static void main(String[] args) {
        new DealerLogin();
    }

    private JLabel screenTitle, selectDealerLabel, passwordLabel;
    private JComboBox dealerComboBox;
    private JButton loginButton, exitButton;
    private DealerAPI dealerAPI = new DealerAPI("https://raw.githubusercontent.com/vykuntaharsha/NeuSep17-Project-Group2/master/data/dealers");
    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
    private ArrayList<String> dealerNameList = new ArrayList<>();

    public DealerLogin() throws HeadlessException {

        super();
        setTitle("DealerLogin");

        createComponents();
        addComponentsUsingGridBagLayout();
        addListener();
        makeVisible();
    }

    private void createComponents() {
        screenTitle = new JLabel("Welcome !");
        screenTitle.setFont(new Font("Default", Font.BOLD, 60));
        screenTitle.setForeground(Color.BLACK);

        selectDealerLabel = new JLabel("Selcet Dealer");
        selectDealerLabel.setFont(new Font("Default", Font.ITALIC, 40));
        selectDealerLabel.setForeground(Color.BLACK);

        for (Dealer d : dealerList) {
            dealerNameList.add(d.getId());
        }

        dealerComboBox = new JComboBox(dealerNameList.toArray());
        dealerComboBox.setSelectedItem(null);
        dealerComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Default",Font.PLAIN,40));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Default", Font.PLAIN, 40));
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void addComponentsUsingGridBagLayout() {
        Container con = getContentPane();

        GridLayout layout = new GridLayout(5, 2,1,1);
        con.setLayout(layout);
        con.add(screenTitle);
        con.add(selectDealerLabel);
        con.add(dealerComboBox);
        con.add(loginButton);
        con.add(exitButton);
    }

    private void addListener() {
        DealerLogin.ManageInventory mngInv = new DealerLogin.ManageInventory();
        DealerLogin.ManageIncentive mngInc = new DealerLogin.ManageIncentive();
        loginButton.addActionListener(mngInv);
        exitButton.addActionListener(mngInc);

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
//                new IncentiveAPP();
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


