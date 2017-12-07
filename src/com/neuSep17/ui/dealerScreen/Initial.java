package com.neuSep17.ui.dealerScreen;

import com.neuSep17.ui.StretchIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Initial extends JFrame{
    JLabel screenTitle;
    Font screenTitleFont;
    JButton consumer, dealer;
    BufferedImage backgroundImage;


    public Initial() throws HeadlessException , IOException {
        super();
        this.backgroundImage = ImageIO.read(new File("/Users/kevinshi721/GitHub/NeuSep17-Project-Group2/src/com/neuSep17/ui/dealerScreen/car3.jpg"));

        setTitle("Initial Screen");//this is set title for Initial frame
        createComponents();// function to c
        addListener();
        makeVisible();
    }

    private void createComponents() throws IOException {
        // Create Stretch icon using background image. Set proportionate to false as we want the image to fill
        // the label at the cost of aspect ratio.
        StretchIcon si = new StretchIcon(backgroundImage, false);

        // Set stretch icon to the background label
        JLabel backgroundLabel = new JLabel(si);

        // Add background to current frame
        this.add(backgroundLabel);

        // We'll be using GridBagLayout to have the Title label and buttons at the center of screen.
        backgroundLabel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Set GridBag Constraints to place the label and two buttons
        gbc.gridx = 1;
        gbc.gridy = 0;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;

        // This is the title label
        screenTitle = new JLabel("Welcome to car website");

        screenTitleFont = new Font("Default", Font.BOLD, 40);
        screenTitle.setFont(screenTitleFont);
        screenTitle.setHorizontalAlignment(JLabel.CENTER);

        backgroundLabel.add(screenTitle, gbc);


        // Consumer button
        consumer = new JButton("Consumer");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipadx = 40;
        gbc.fill = GridBagConstraints.NONE;
        backgroundLabel.add(consumer, gbc);


        // Dealer
        dealer = new JButton("Dealer");
        gbc.gridx = 2;
        gbc.gridy = 1;
        backgroundLabel.add(dealer, gbc);

        // Empty label at 0,0 to take up space
        JLabel empty = new JLabel();
        //empty.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridx = 1;
        gbc.gridy = 2;
        backgroundLabel.add(empty, gbc);

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

        setSize(1920,1200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) throws IOException {
        Initial in = new Initial();
    }
}



//package com.neuSep17.ui.dealerScreen;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class Initial extends JFrame{
//
//    private JLabel screenTitle;
//    private Font screenTitleFont;
//    private JButton consumer, dealer;
//
//    public Initial() throws HeadlessException {
//        super();
//        setTitle("Initial Screen");
//        createComponents();
//        addComponentsUsingGridLayout();
//        addListener();
//        makeVisible();
//    }
//
//    private void createComponents() {
//        screenTitle = new JLabel("Initial Screen",JLabel.CENTER);
//        screenTitleFont = new Font("Default", Font.BOLD, 14);
//        screenTitle.setFont(screenTitleFont);
//        consumer = new JButton("Consumer");
//        dealer = new JButton("Dealer");
//    }
//
//    private void addComponentsUsingGridLayout() {
//        Container con = getContentPane();
//        GridLayout gl = new GridLayout(3,1,1,1);
//        con.setLayout(gl);
//        con.add(screenTitle);
//        con.add(consumer);
//        con.add(dealer);
//    }
//
//    private void addListener() {
//        GoToDealerScreen gtds = new GoToDealerScreen();
//        dealer.addActionListener(gtds);
//        GoToConsumerScreen gtcs = new GoToConsumerScreen();
//        consumer.addActionListener(gtcs);
//    }
//
//    class GoToDealerScreen implements ActionListener{
//        @Override
//        public void actionPerformed (ActionEvent e){
//            if(e.getSource() == dealer) {
//                new DealerScreen();
//                dispose();
//            }
//        }
//    }
//
//    class GoToConsumerScreen implements ActionListener{
//        @Override
//        public void actionPerformed (ActionEvent e){
//            if(e.getSource() == consumer) {
//                new ConsumerScreen();
//                dispose();
//            }
//        }
//    }
//
//    private void makeVisible() {
//        setSize(500,500);
//        setVisible(true);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//
//    public static void main(String[] args) {
//        Initial in = new Initial();
//    }
//}
