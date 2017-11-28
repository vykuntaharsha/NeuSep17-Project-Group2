package com.neuSep17.ui.dealerScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Initial extends JFrame{

    private JLabel screenTitle;
    private Font screenTitleFont;
    private JButton consumer, dealer;

    public Initial() throws HeadlessException {
        super();
        setTitle("Initial Screen");
        createComponents();
        addComponentsUsingGridLayout();
        addListener();
        makeVisible();
    }

    private void createComponents() {
        screenTitle = new JLabel("Initial Screen",JLabel.CENTER);
        screenTitleFont = new Font("Default", Font.BOLD, 14);
        screenTitle.setFont(screenTitleFont);
        consumer = new JButton("Consumer");
        dealer = new JButton("Dealer");
    }

    private void addComponentsUsingGridLayout() {
        Container con = getContentPane();
        GridLayout gl = new GridLayout(3,1,1,1);
        con.setLayout(gl);
        con.add(screenTitle);
        con.add(consumer);
        con.add(dealer);
    }

    private void addListener() {
        GoToDealerScreen gtds = new GoToDealerScreen();
        dealer.addActionListener(gtds);
        GoToConsumerScreen gtcs = new GoToConsumerScreen();
        consumer.addActionListener(gtcs);
    }

    class GoToDealerScreen implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            if(e.getSource() == dealer) {
                new DealerScreen();
                dispose();
            }
        }
    }

    class GoToConsumerScreen implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            if(e.getSource() == consumer) {
                new ConsumerScreen();
                dispose();
            }
        }
    }

    private void makeVisible() {
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Initial in = new Initial();
    }
}
