package com.neuSep17.ui.dealerScreen;


import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;
import com.neuSep17.service.InventoryServiceAPI_Test;
import com.neuSep17.ui.consumer.BrowseInventoryFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ConsumerScreen  extends JFrame{

    private JLabel first, second, third;
    private JButton browser, empty[], browserAll, exitButton;
    private JComboBox combobox;
    private DealerAPI dealerAPI = new DealerAPI("data/dealers");
    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
    private ArrayList<String> dealerNameList = new ArrayList<>();
    
    public ConsumerScreen() throws HeadlessException {
        super();
        setTitle("Consumer Screen");
        createComponents();
        addComponentsUsingGridBagLayout();
        addListener();
        makeThisVisible();
    }
    
    private void createComponents() {

        browser = new JButton("Browse Inventory");
        browser.setFont(new Font("Default", Font.PLAIN, 20));
        browser.setForeground(Color.DARK_GRAY);
        browser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        browserAll = new JButton("Browse All Dealers' Inventory");
        browserAll.setFont(new Font("Default", Font.PLAIN, 20));
        browserAll.setForeground(Color.DARK_GRAY);
        browserAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        first = new JLabel ("Please Select a Dealer ", JLabel.CENTER);
        first.setFont(new Font("Default",Font.BOLD, 55));
        first.setForeground(Color.DARK_GRAY);

        second = new JLabel("Dealer Name", JLabel.RIGHT);
        second.setFont(new Font("Default",Font.PLAIN, 20));
        second.setForeground(Color.DARK_GRAY);

        third = new JLabel("***** Have No Idea About Dealers? *****", JLabel.CENTER);
        third.setFont(new Font("Default",Font.ITALIC, 20));
        third.setForeground(Color.DARK_GRAY);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Default", Font.PLAIN, 20));
        exitButton.setForeground(Color.DARK_GRAY);

        for (Dealer d: dealerList){
          dealerNameList.add(d.getId());
        }

        combobox=new JComboBox(dealerNameList.toArray());
        combobox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        combobox.setSelectedItem(null);

  }

  public void addComponentsUsingGridBagLayout(){
	  
        ImageIcon backgroundImage = new ImageIcon("data/images/DealerBackGround.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        this.add(backgroundLabel);

        backgroundLabel.setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        backgroundLabel.setLocation(0, 0);
        backgroundLabel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(50, 0, 140, 0);
        backgroundLabel.add(first, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        backgroundLabel.add(second, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipadx = 200;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundLabel.add(combobox, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        backgroundLabel.add(browser, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(100, 0, 10, 0);
        backgroundLabel.add(third, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        backgroundLabel.add(browserAll, gbc);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(110, 0, 50, 0);
        backgroundLabel.add(exitButton, gbc);

  }

  private void addListener() {
        Browser browserCar = new Browser();
        browser.addActionListener(browserCar);
        BrowserAll browser = new BrowserAll();
        browserAll.addActionListener(browser);
        ExitAL exitAL = new ExitAL();
        exitButton.addActionListener(exitAL);
  }

  public Dealer getSelectedDealer(){
        String dealerID = combobox.getSelectedItem().toString();
        return dealerAPI.getDealer(dealerID);
  }

  public void showBrowseInventory()
  {
      BrowseInventoryFrame bif = new BrowseInventoryFrame(this, new InventoryServiceAPI_Test("data/" + combobox.getSelectedItem()));
      Thread BrowseInventoryThread = new Thread(() -> bif.run());
      SwingUtilities.invokeLater(BrowseInventoryThread);
      this.setVisible(false);
  }

    public void showAllBrowseInventory()
    {
        BrowseInventoryFrame bif = new BrowseInventoryFrame(this, new InventoryServiceAPI_Test());
        Thread BrowseInventoryThread = new Thread(() -> bif.run());
        SwingUtilities.invokeLater(BrowseInventoryThread);
        this.setVisible(false);
    }

  class Browser implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e){
          if(combobox.getSelectedItem() != null){
              //new DealerInventoryScreen();
              System.out.println("You have choosed " + combobox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Inventory Screen");
              showBrowseInventory();

          } else {
              JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
          }
      }
  }

  class BrowserAll implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e){
              //new DealerInventoryScreen();
              System.out.println("You have clicked the second button!");

              showAllBrowseInventory();

              //dispose();

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


  public void makeThisVisible(){
      this.setTitle("Consumer Screen");
      this.setSize(1920,1200);
      this.setVisible(true);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}