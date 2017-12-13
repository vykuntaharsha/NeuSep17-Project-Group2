package com.neuSep17.ui.inventoryList.adeButton;

import com.neuSep17.ui.inventoryList.CenterSection.PageController;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class DeleteFrame extends JFrame {
	private JButton Yes,No;
	private PageController pageController;
	private JLabel DeleteSure;
	public boolean IsYes = false;

	public DeleteFrame(){
		create();
		addComponents();
        makeListeners();
        display();
        setTitle("Delete this Vehicle?");
	}
public void create() {
	        Yes = new JButton("Yes");
	        No = new JButton("No");
	        DeleteSure = new JLabel("Are you sure to delete this Vechile");
	    }
public void addComponents() {
    Container con = getContentPane();
    con.setLayout(new FlowLayout());
    con.add(DeleteSure,"North");
    con.add(makeActionButtonPanel(), "Center");
}
private JPanel makeActionButtonPanel() {
    JPanel panel = new JPanel();
    panel.add(Yes);
    panel.add(No);
    return panel;
}
public void makeListeners() {
    ManageActionListener mal = new ManageActionListener();
    Yes.addActionListener(mal);
    No.addActionListener(mal);
}
class ManageActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Yes){
        	IsYes = true;
           setVisible(false);
        }
        if (e.getSource() == No) {
          setVisible(false);
        }
      
    }

}
public void display() {
	 setSize(300,100);
     setLocation(500, 300);
     setVisible(true);
     //pack();
	    }
}
