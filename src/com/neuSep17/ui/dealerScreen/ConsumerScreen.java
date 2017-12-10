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
    private JButton browser, empty[], browserAll;
    private JComboBox combobox;
    private DealerAPI dealerAPI = new DealerAPI("https://raw.githubusercontent.com/vykuntaharsha/NeuSep17-Project-Group2/master/data/dealers");
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

      first = new JLabel ("Please select your dealer ");
      first.setFont(new Font("Default",Font.BOLD, 20));
      first.setForeground(Color.DARK_GRAY);

      second = new JLabel("Select My Dealer");
      second.setFont(new Font("Default",Font.BOLD, 20));
      second.setForeground(Color.DARK_GRAY);

      third = new JLabel("If you have no idea ");
      third.setFont(new Font("Default",Font.BOLD, 20));
      third.setForeground(Color.DARK_GRAY);

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
	  
	  ImageIcon backgroundImage = new ImageIcon("/Users/luoyiwei/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/dealerScreen/DealerLoginBackGround.jpg");
	  JLabel backgroundLabel = new JLabel(backgroundImage);
	  this.add(backgroundLabel);
	  
	  backgroundLabel.setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
	  backgroundLabel.setLocation(0, 0);
	  backgroundLabel.setLayout(null);
	  
	  // the postion of these components remain changed.
	 /* 
	  GridBagConstraints constraints = new GridBagConstraints();
	  constraints.gridwidth = 1;
	  constraints.gridheight = 1;
	  constraints.gridx = 1;
	  constraints.gridy = 0;
	  constraints.fill = GridBagConstraints.HORIZONTAL;
	  constraints.anchor = GridBagConstraints.CENTER;
	  constraints.insets = new Insets(0,0,200,0);
	  backgroundLabel.add(screenTitle, constraints);
	  
	  constraints.gridwidth = 1;
      constraints.gridheight = 1;
      constraints.ipadx = 40;
      constraints.ipady = 15;
      constraints.gridx = 1;
      constraints.gridy = 1;
      constraints.insets = new Insets(2, 250, 0, 250);
      backgroundLabel.add(first, constraints);
      
      constraints.gridwidth = 1;
      constraints.gridheight = 1;
      constraints.gridx = 3;
      constraints.gridy = 3;
      constraints.insets = new Insets(2, 300, 0, 300);
      backgroundLabel.add(browser, constraints);
      

      constraints.gridwidth =1;
      constraints.gridheight = 1;
      constraints.gridx = 1;
      constraints.gridy = 2;
      backgroundLabel.add(second, constraints);
      
     
      
      constraints.gridheight = 1;
      constraints.gridx = 4;
      constraints.gridy = 4;
      backgroundLabel.add(third, constraints);
      
      constraints.gridheight = 1;
      constraints.gridx = 5;
      constraints.gridy = 5;
      backgroundLabel.add(browserAll, constraints);
      
      constraints.gridheight = 1;
      constraints.gridx = 6;
      constraints.gridy = 6;
      backgroundLabel.add(combobox, constraints);
      
      
   */   
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