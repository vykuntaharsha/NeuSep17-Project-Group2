package com.neuSep17.ui.dealerScreen;



import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;
import com.neuSep17.service.InventoryServiceAPI_Test;
import com.neuSep17.ui.consumer.BrowseInventoryFrame;
import com.neuSep17.ui.dealerScreen.ConsumerScreen.Browser;
import com.neuSep17.ui.dealerScreen.ConsumerScreen.BrowserAll;

public class ConsumerScreen  extends JFrame{
    //JFrame jframe = new JFrame();
    private JLabel first, second, third;
    private JButton browser, empty[], browserAll, exitButton;
    private JComboBox combobox;
    //private DealerAPI dealerAPI = new DealerAPI("https://raw.githubusercontent.com/vykuntaharsha/NeuSep17-Project-Group2/master/data/dealers");
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

//      browser = new JButton("Browse Super Good Cars here !");
  
//    	  screenTitle = new JLabel(titleText, JLabel.CENTER);
//    	  screenTitle.setFont(new Font("Default", Font.BOLD, 55));
//      screenTitle.setForeground(Color.DARK_GRAY);
    	  
      browser = new JButton("Browse selected dealer");
      browser.setFont(new Font("Default", Font.BOLD, 20));
      browser.setForeground(Color.DARK_GRAY);
      browser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

      browserAll = new JButton("Browse all vehicle");
      browserAll.setFont(new Font("Default", Font.BOLD, 20));
      browserAll.setForeground(Color.DARK_GRAY);
      browserAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

      first = new JLabel ("Please select your dealer ", JLabel.CENTER);
      first.setFont(new Font("Default",Font.BOLD, 55));
      first.setForeground(Color.DARK_GRAY);
      
      second = new JLabel("Select My Dealer", JLabel.RIGHT);
      second.setFont(new Font("Default",Font.BOLD, 20));
      second.setForeground(Color.DARK_GRAY);

      third = new JLabel("If you have no idea ", JLabel.CENTER);
      third.setFont(new Font("Default",Font.BOLD, 20));
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
      //combobox.setFont(new Font("Default",Font.ITALIC,24));

//      empty = new JButton[3];
//      for(int i = 0; i < 3; i ++) {
//          empty[i] = new JButton("");
//          empty[i].setContentAreaFilled(false);
//          empty[i].setBorderPainted(false);
//      }

  }

  public void addComponentsUsingGridBagLayout(){
	  
	  ImageIcon backgroundImage = new ImageIcon("data/images/DealerBackGround.jpg");
	  JLabel backgroundLabel = new JLabel(backgroundImage);
	  this.add(backgroundLabel);
	  
	  backgroundLabel.setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
      backgroundLabel.setLocation(0, 0);
      backgroundLabel.setLayout(new GridBagLayout());
      
      GridBagConstraints gbc = new GridBagConstraints();
      
      /*Degign 1
      gbc.gridwidth = 3;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(50, 0, 150, 0);
      backgroundLabel.add(first, gbc);

      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      gbc.gridx = 1;
      gbc.gridy = 1;
      gbc.anchor = GridBagConstraints.LINE_END;
      gbc.insets = new Insets(0, 300, 25, 0);
      backgroundLabel.add(second, gbc);

      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      gbc.gridx = 2;
      gbc.gridy = 1;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(0, 0, 25, 300);
      backgroundLabel.add(combobox, gbc);
      
      gbc.gridwidth = 3;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.insets = new Insets(0, 0, 100, 0);
      backgroundLabel.add(browser, gbc);
      
      gbc.gridwidth = 3;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.insets = new Insets(0, 0, 25, 0);
      backgroundLabel.add(third, gbc);
      
      gbc.gridwidth = 3;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 4;
      gbc.insets = new Insets(0, 0, 100, 0);
      backgroundLabel.add(browserAll, gbc);
      
      gbc.gridwidth = 3;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 5;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(0, 0, 50, 0);
      backgroundLabel.add(exitButton, gbc);
      */
      
      //Degign 2
      gbc.gridwidth = 3;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(50, 0, 200, 0);
      backgroundLabel.add(first, gbc);

      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.anchor = GridBagConstraints.LINE_END;
      gbc.insets = new Insets(0, 0, 100, 0);
      backgroundLabel.add(second, gbc);

      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      gbc.gridx = 1;
      gbc.gridy = 1;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(0, 0, 100, 0);
      backgroundLabel.add(combobox, gbc);
      
      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      gbc.gridx = 2;
      gbc.gridy = 1;
      gbc.insets = new Insets(0, 0, 100, 0);
      backgroundLabel.add(browser, gbc);
      
      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.insets = new Insets(0, 0, 100, 0);
      backgroundLabel.add(third, gbc);
      
      gbc.gridwidth = 2;
      gbc.gridheight = 1;
      gbc.gridx = 1;
      gbc.gridy = 2;
      gbc.insets = new Insets(0, 0, 100, 0);
      backgroundLabel.add(browserAll, gbc);
      
      gbc.gridwidth = 3;
      gbc.gridheight = 1;
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.insets = new Insets(0, 0, 50, 0);
      backgroundLabel.add(exitButton, gbc);
      
      
        
      // these are the past code for layout by Ziye
//      Container con = getContentPane();
//      GridBagLayout gridbag = new GridBagLayout();
//
//      con.setLayout(gridbag);
//      GImage.setLayout(new GridLayout(6,1));
//
//      JPanel pan1 = new JPanel();
//      pan1.setLayout(new GridLayout(1,2));
//      pan1.setOpaque(false);
//      pan1.add(combobox);
//      pan1.add(browser);
//
//      JPanel pan2 = new JPanel();
//      pan2.setLayout(new GridLayout(1,2));
//      pan2.setOpaque(false);
//      pan2.add(third);
//      pan2.add(browserAll);
//
//
//      GImage.add(empty[0]);
//      GImage.add(first);
//      GImage.add(pan1);
//      GImage.add(empty[1]);
//      GImage.add(pan2);


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

  class Browser implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e){
          if(combobox.getSelectedItem() != null){
              //new DealerInventoryScreen();
              System.out.println("You have choosed " + combobox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Inventory Screen");
              BrowseInventoryFrame bif = new BrowseInventoryFrame(new InventoryServiceAPI_Test("data/" + combobox.getSelectedItem()));
              Thread BrowseInventoryThread = new Thread(() -> bif.run());
              SwingUtilities.invokeLater(BrowseInventoryThread);

              dispose();
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

              dispose();

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

  public static void main(String[] args){
      new ConsumerScreen();
  }
    
}