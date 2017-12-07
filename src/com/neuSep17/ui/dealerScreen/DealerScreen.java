package com.neuSep17.ui.dealerScreen;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerAPI;
import com.neuSep17.ui.manageIncentives.IncentiveAPP;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DealerScreen extends JFrame {

    JFrame jframe = new JFrame();
    public static JPanel GImage = null;

    private JLabel screenTitle, selectDealerLabel;
    private JComboBox dealerComboBox;
    private JButton mngInvButton, mngIncButton, empty[];
    private DealerAPI dealerAPI = new DealerAPI("https://raw.githubusercontent.com/vykuntaharsha/NeuSep17-Project-Group2/master/data/dealers");
    private ArrayList<Dealer> dealerList = dealerAPI.getDealers();
    private ArrayList<String> dealerNameList = new ArrayList<>();


    public DealerScreen() throws HeadlessException {

        super();

        GImage = new JPanel() {
            protected void paintComponent (Graphics g) {
                ImageIcon icon = new ImageIcon("/Users/kevinshi721/GitHub/NeuSep17-Project-Group2/src/com/neuSep17/ui/dealerScreen/background2.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
                jframe.setSize(icon.getIconWidth(), icon.getIconHeight());
            }
        };
        jframe.add(GImage);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("DealerScreen");

        setTitle("DealerScreen");


        createComponents();
        addComponentsUsingGridBagLayout();
        addListener();
        //makeVisible();
    }

    private void createComponents() {
        screenTitle = new JLabel("Welcome , Manager !");
        screenTitle.setFont(new Font("Default", Font.BOLD, 60));
        screenTitle.setForeground(Color.BLACK);

        selectDealerLabel = new JLabel("Selcet My Car Plans");
        selectDealerLabel.setFont(new Font("Default", Font.ITALIC, 40));
        selectDealerLabel.setForeground(Color.BLACK);

        for (Dealer d: dealerList){
            dealerNameList.add(d.getId());
        }

        dealerComboBox = new JComboBox(dealerNameList.toArray());
        dealerComboBox.setSelectedItem(null);
        dealerComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        mngInvButton = new JButton("Manage Inventory");
        mngInvButton.setFont(new Font("Default", Font.PLAIN, 40));
        mngInvButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mngIncButton = new JButton("Manage Incentive");
        mngIncButton.setFont(new Font("Default", Font.PLAIN, 40));
        mngIncButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        empty = new JButton[2];
        for(int i = 0; i < 2; i ++) {
            empty[i] = new JButton("");
            empty[i].setContentAreaFilled(false);
            empty[i].setBorderPainted(false);
        }
    }

    private void addComponentsUsingGridBagLayout() {
        Container con = getContentPane();

        GridLayout layout = new GridLayout(9, 1);
        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(1,10));
        pan.setOpaque(false);
        pan.add(selectDealerLabel);
        pan.add(dealerComboBox);

        GImage.setLayout(layout);
        GImage.add(screenTitle);
        GImage.add(empty[0]);
        GImage.add(pan);
        GImage.add(empty[1]);
        GImage.add(mngInvButton);
        GImage.add(mngIncButton);

        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;

    }

    private void addListener() {
        ManageInventory mngInv = new ManageInventory();
        ManageIncentive mngInc = new ManageIncentive();
        mngInvButton.addActionListener(mngInv);
        mngIncButton.addActionListener(mngInc);

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
                new IncentiveAPP();
                System.out.println("You have choosed " + dealerComboBox.getSelectedItem() + ", Close Dealer Screen -> Open Dealer Incentive Screen");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please Select a Dealer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void makeVisible() {
        setSize(1024, 768);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
