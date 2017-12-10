package com.neuSep17.ui.dealerScreen;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DealerLogin extends JFrame {

    private JLabel screenTitle, selectDealerLabel, passwordLabel;
    private JComboBox dealerComboBox;
    private JTextField passwordTextField;
    private JButton loginButton, exitButton;
    private DealerAPI dealerAPI = new DealerAPI("data/dealers");
    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
    private ArrayList<String> dealerNameList = new ArrayList<>();
    
    public DealerLogin() throws HeadlessException {

        super();
        setTitle("Dealer Login");
        createComponents();
        addComponentsUsingGridBagLayout();
        addListener();
        makeVisible();
    }

    private void createComponents() {

        screenTitle = new JLabel("Welcome to Dealer Login!", JLabel.CENTER);
        screenTitle.setFont(new Font("Default", Font.BOLD, 55));
        screenTitle.setForeground(Color.DARK_GRAY);

        selectDealerLabel = new JLabel("Dealer Name", JLabel.RIGHT);
        selectDealerLabel.setFont(new Font("Default", Font.PLAIN, 20));
        selectDealerLabel.setForeground(Color.DARK_GRAY);

        for (Dealer d : dealerList) {
            dealerNameList.add(d.getId());
        }

        dealerComboBox = new JComboBox(dealerNameList.toArray());
        dealerComboBox.setSelectedItem(null);
        dealerComboBox.setForeground(Color.DARK_GRAY);

        passwordLabel = new JLabel("Password", JLabel.RIGHT);
        passwordLabel.setFont(new Font("Default", Font.PLAIN, 20));
        passwordLabel.setForeground(Color.DARK_GRAY);

        passwordTextField = new JPasswordField();
        passwordTextField.setEditable(true);
        passwordTextField.setForeground(Color.DARK_GRAY);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Default",Font.PLAIN,20));
        loginButton.setForeground(Color.DARK_GRAY);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Default", Font.PLAIN, 20));
        exitButton.setForeground(Color.DARK_GRAY);
    }

    private void addComponentsUsingGridBagLayout() {

        ImageIcon backgroundImage = new ImageIcon("src/com/neuSep17/ui/dealerScreen/DealerLoginBackground.jpg");

        JLabel backgroundLabel = new JLabel(backgroundImage);
        this.add(backgroundLabel);

        backgroundLabel.setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        backgroundLabel.setLocation(0, 0);
        backgroundLabel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(50, 0, 200, 0);
        backgroundLabel.add(screenTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 400, 10, 0);
        backgroundLabel.add(selectDealerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 10, 300);
        backgroundLabel.add(dealerComboBox, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 400, 10, 0);
        backgroundLabel.add(passwordLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 10, 300);
        backgroundLabel.add(passwordTextField, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.ipady = 3;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(200, 200, 50, 0);
        backgroundLabel.add(loginButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(200, 0, 50, 200);
        backgroundLabel.add(exitButton, gbc);
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
                if (passwordTextField.getText().equals(getSelectedDealer().getPassword())) {
                    JOptionPane.showMessageDialog(null, "Login Successfully", "Login Validation", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        new DealerScreen(dealerComboBox.getSelectedItem().toString());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please Re-Check Your Password", "Login Validation", JOptionPane.ERROR_MESSAGE);
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


