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
    private DealerAPI dealerAPI = new DealerAPI("https://raw.githubusercontent.com/vykuntaharsha/NeuSep17-Project-Group2/DearlerScreenV2/data/dealers");
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
        screenTitle = new JLabel("Welcome to Dealer Login!", JLabel.CENTER);
        screenTitle.setFont(new Font("Default", Font.BOLD, 55));
        screenTitle.setForeground(Color.DARK_GRAY);

        selectDealerLabel = new JLabel("Dealer Name", JLabel.RIGHT);
        selectDealerLabel.setFont(new Font("Default", Font.BOLD, 20));
        selectDealerLabel.setForeground(Color.DARK_GRAY);

        for (Dealer d : dealerList) {
            dealerNameList.add(d.getId());
        }

        dealerComboBox = new JComboBox(dealerNameList.toArray());
        dealerComboBox.setSelectedItem(null);
        dealerComboBox.setForeground(Color.DARK_GRAY);

        passwordLabel = new JLabel("Password", JLabel.RIGHT);
        passwordLabel.setFont(new Font("Default", Font.BOLD, 20));
        passwordLabel.setForeground(Color.DARK_GRAY);

        passwordTextField = new JPasswordField();
        passwordTextField.setEditable(true);
        passwordTextField.setForeground(Color.DARK_GRAY);


        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Default",Font.BOLD,20));
        loginButton.setForeground(Color.DARK_GRAY);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Default", Font.BOLD, 20));
        exitButton.setForeground(Color.DARK_GRAY);
    }

    private void addComponentsUsingGridBagLayout() {
        Container con = getContentPane();


        ImageIcon backgroundImage = new ImageIcon("/Users/kevinshi721/GitHub/NeuSep17-Project-Group2/src/com/neuSep17/ui/dealerScreen/DealerLoginBackground.jpg");

        JLabel backgroundLabel = new JLabel(backgroundImage);

        this.add(backgroundLabel);

        backgroundLabel.setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        backgroundLabel.setLocation(0, 0);

        backgroundLabel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        backgroundLabel.add(screenTitle);
        backgroundLabel.add(selectDealerLabel);
        backgroundLabel.add(dealerComboBox);
        backgroundLabel.add(passwordLabel);
        backgroundLabel.add(passwordTextField);
        backgroundLabel.add(loginButton);
        backgroundLabel.add(exitButton);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.insets = new Insets(50, 0, 200, 0);
        gbc.weightx = 0.8;
//        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundLabel.add(screenTitle, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 1;
        backgroundLabel.add(selectDealerLabel, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 2;
        gbc.gridy = 1;
        backgroundLabel.add(dealerComboBox, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 2;
        backgroundLabel.add(passwordLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 0, 0, 529);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 2;
        gbc.gridy = 2;
        backgroundLabel.add(passwordTextField, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(200, 400, 50, 0);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 3;
        backgroundLabel.add(loginButton, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(200, 0, 50, 400);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 2;
        gbc.gridy = 3;
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
                    new DealerScreen(dealerComboBox.getSelectedItem().toString());
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


