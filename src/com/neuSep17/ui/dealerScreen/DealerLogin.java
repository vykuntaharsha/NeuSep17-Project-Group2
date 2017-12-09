package com.neuSep17.ui.dealerScreen;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class DealerLogin extends JFrame {

    public static void main(String[] args) {
        new DealerLogin();
    }

    private JLabel screenTitle, selectDealerLabel, passwordLabel;
    private JComboBox dealerComboBox;
    private JTextField passwordTextField;
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
        screenTitle = new JLabel("Welcome to Dealer Login!");
        screenTitle.setFont(new Font("Default", Font.BOLD, 60));
        screenTitle.setForeground(Color.BLACK);

        selectDealerLabel = new JLabel("Selcet Dealer");
        selectDealerLabel.setFont(new Font("Default", Font.PLAIN, 40));
        selectDealerLabel.setForeground(Color.BLACK);

        for (Dealer d : dealerList) {
            dealerNameList.add(d.getId());
        }

        dealerComboBox = new JComboBox(dealerNameList.toArray());
        dealerComboBox.setSelectedItem(null);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Default", Font.PLAIN, 40));

        passwordTextField = new JPasswordField();
        passwordTextField.setEditable(true);


        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Default",Font.PLAIN,40));

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Default", Font.PLAIN, 40));
    }

    private void addComponentsUsingGridBagLayout() {
        Container con = getContentPane();

        GridLayout layout = new GridLayout(8, 1,1,1);
        con.setLayout(layout);
        con.add(screenTitle);
        con.add(selectDealerLabel);
        con.add(dealerComboBox);
        con.add(passwordLabel);
        con.add(passwordTextField);
        con.add(loginButton);
        con.add(exitButton);
    }

    private void addListener() {
        DealerLogin.LoginAL loginAL = new DealerLogin.LoginAL();
        DealerLogin.ExitAL exitAL = new DealerLogin.ExitAL();
        loginButton.addActionListener(loginAL);
        exitButton.addActionListener(exitAL);

    }

    public Dealer getSelectedDealer(){
        String dealerID = dealerComboBox.getSelectedItem().toString();
        return dealerAPI.getDealer(dealerID);
    }

    class LoginAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            if (dealerComboBox.getSelectedItem() != null) {
                if (passwordTextField.getText() == getSelectedDealer().getPassword()) {
                    JOptionPane.showMessageDialog(null, "Login Successfully", "Login", JOptionPane.INFORMATION_MESSAGE);
                    //new ManageInventoryScreen();
                    System.out.println("You have choosed " + dealerComboBox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Inventory Screen");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please Check Your Password", "Invalid Password", JOptionPane.ERROR_MESSAGE);

                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ExitAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new Initial();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void makeVisible() {
        setSize(1920, 1200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


