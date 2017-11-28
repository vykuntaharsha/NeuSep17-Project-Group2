package com.neuSep17.ui.dealerScreen;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by lbjfo on 11/28/2017.
 */
public class ConsumerScreen extends JFrame{
    private JLabel first, second;
    private JButton browser;
    private JComboBox combobox;
    private DealerAPI dealerAPI = new DealerAPI("/Users/kevinshi721/GitHub/NeuSep17-Project-Group2/data/dealers");
    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
    private ArrayList<String> dealerNameList = new ArrayList<>();

    public ConsumerScreen(){
        super();
        createComponents();
        addComponentsUsingGridLayout();
        addListener();
        makeThisVisible();
    }

    private void createComponents() {
        Font f1 = new Font("Arial", Font.BOLD, 36);
        Font f2 = new Font("Arial",Font.BOLD,24);

        browser = new JButton("Browser cars");
        first = new JLabel("Welcome", JLabel.CENTER);
        first.setFont(f1);
        second = new JLabel("Select Dealer", JLabel.CENTER);
        second.setFont(f2);
        combobox=new JComboBox();

        for (Dealer d: dealerList){
            dealerNameList.add(d.getId());
        }

    }

    public void addComponentsUsingGridLayout(){
        Container con = getContentPane();
        GridLayout g1 = new GridLayout(4,1,10,10);
        con.setLayout(g1);
        con.add(first);
        con.add(second);
        con.add(combobox);
        con.add(browser);
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
                System.out.println("Close Dealer Screen -> Open Dealer Inventory Screen");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void makeThisVisible(){
        this.setTitle("Consumer Screen");
        this.setSize(500,500);
        //this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
