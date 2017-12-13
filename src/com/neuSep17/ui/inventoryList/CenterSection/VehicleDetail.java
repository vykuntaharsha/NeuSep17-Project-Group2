package com.neuSep17.ui.inventoryList.CenterSection;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

public class VehicleDetail extends JPanel
{
	private JLabel edit, delete, add, pre;
	private JTable curTable, specTable;
	private ImageIcon addIconEnter, addIconExit, editIconEnter, editIconExit, deleteIconEnter, deleteIconExit,
			preImageEnter, preImageExit;
	private String[] detailType = { "  ID", "  WEBID", "  CATEGORY", "  YEAR", "  MAKE", "  MODEL", "  TRIM", "  TYPE", "  PRICE" };
	private String[] discountContent = { "  SALE PRICE", "  START DATE", "  END DATE", "  TOTAL SAVINGS" };
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private Vehicle vehicle;
	private InventoryServiceAPI_Test inventoryServiceAPI_test;

	public VehicleDetail(Vehicle vehicle,InventoryServiceAPI_Test inventoryServiceAPI_test, IncentiveServiceAPI_Test incentiveServiceAPI_Test, CardLayout cardLayout, JPanel cardPanel)
	{
		this.vehicle = vehicle;
		this.cardPanel = cardPanel;
		this.cardLayout = cardLayout;
		this.inventoryServiceAPI_test = inventoryServiceAPI_test;
		setSize(300,300);
		createComponents(vehicle, incentiveServiceAPI_Test);
		addComponents(vehicle, incentiveServiceAPI_Test);
		makeListener();

	}

	private void makeListener(){
		IconActionListener ial = new IconActionListener();
		pre.addMouseListener(ial);
		edit.addMouseListener(ial);
		add.addMouseListener(ial);
		delete.addMouseListener(ial);

	}

	private void createComponents(Vehicle vehicle, IncentiveServiceAPI_Test incentiveServiceAPI_Test)
	{

//		currentOffers = new JLabel("Current Offers");
//		specifications = new JLabel("Specifications");
		curTable = new JTable(discountContent.length, 2);
		specTable = new JTable(detailType.length, 2);
		preImageEnter = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/left80%.png");
		preImageExit = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/left50%.png");
		editIconEnter = new ImageIcon("/Users/boqunzhang/IdeaProjects/groupB/NeuSep17-Project-Group2/src/com/neuSep17/ui/inventoryList/material/edit80%.png");
		editIconExit = new ImageIcon("/Users/boqunzhang/IdeaProjects/groupB/NeuSep17-Project-Group2/src/com/neuSep17/ui/inventoryList/material/edit50%.png");
		addIconEnter = new ImageIcon("/Users/boqunzhang/IdeaProjects/groupB/NeuSep17-Project-Group2/src/com/neuSep17/ui/inventoryList/material/add80%.png");
		addIconExit = new ImageIcon("/Users/boqunzhang/IdeaProjects/groupB/NeuSep17-Project-Group2/src/com/neuSep17/ui/inventoryList/material/add50%.png");
		deleteIconEnter = new ImageIcon("/Users/boqunzhang/IdeaProjects/groupB/NeuSep17-Project-Group2/src/com/neuSep17/ui/inventoryList/material/delete80%.png");
		deleteIconExit = new ImageIcon("/Users/boqunzhang/IdeaProjects/groupB/NeuSep17-Project-Group2/src/com/neuSep17/ui/inventoryList/material/delete50%.png");
		add = new JLabel(addIconExit);
		edit = new JLabel(editIconExit);
		delete = new JLabel(deleteIconExit);
		pre = new JLabel(preImageExit);


		float salePrice = Math.max(vehicle.getPrice() - incentiveServiceAPI_Test.getAllDiscount(vehicle), 0);
		String[] details = { vehicle.getId()+"  ", vehicle.getWebId()+"  ", vehicle.getCategory().toString()+"  ",
				vehicle.getYear().toString()+"  ", vehicle.getMake()+"  ", vehicle.getModel()+"  ", vehicle.getTrim()+"  ",
				vehicle.getBodyType()+"  ", vehicle.getPrice().toString()+"  " };
		String startDate="";
		String endDate="";
		if(incentiveServiceAPI_Test.getAllDiscount(vehicle)!=0){
			LocalDate date=LocalDate.MIN;
			ArrayList<Incentive> allIncentives=incentiveServiceAPI_Test.getAllIncentiveList(vehicle);
			for(Incentive ince:allIncentives){
				if(LocalDate.parse(ince.getStartDate()).isAfter(date)){
					date=LocalDate.parse(ince.getStartDate());
					startDate=ince.getStartDate();
				}
				if(LocalDate.parse(ince.getStartDate()).isBefore(date)){
					date=LocalDate.parse(ince.getStartDate());
					endDate=ince.getEndDate();
				}
			}
		}else{
			startDate="N/A";
			endDate="N/A";
		}
		String[] discount = { "" + salePrice+"  ", startDate+"  ", endDate+"  ",
				String.valueOf(incentiveServiceAPI_Test.getAllDiscount(vehicle))+"  " };

		setValue(curTable, discountContent, discount,0);
		setValue(specTable, detailType, details,1);
		curTable.setRowHeight(18);
		curTable.setDefaultEditor(Object.class, null);
		curTable.setFocusable(false);
		curTable.setShowGrid(false);
		curTable.setBackground(Color.LIGHT_GRAY);
		specTable.setRowHeight(18);
		specTable.setDefaultEditor(Object.class, null);
		specTable.setFocusable(false);
		specTable.setShowGrid(false);
		specTable.setBackground(Color.LIGHT_GRAY);
		curTable.setFont(new Font("Menu.font", Font.PLAIN, 16));
		specTable.setFont(new Font("Menu.font", Font.PLAIN, 16));
	}

	private void addComponents(Vehicle vehicle, IncentiveServiceAPI_Test incentiveServiceAPI_Test)
	{
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		pre.setBounds(0,0,50,300);
		add.setBounds(173,267,50,30); // 181
		edit.setBounds(211,267,50,30); //219
		delete.setBounds(249,267,50,30); //257
		specTable.setBounds(0,15,300,170);
		curTable.setBounds(0,195,300,80);

		add(pre);
		add(add);
		add(edit);
		add(delete);
		add(specTable);
		add(curTable);


	}

	private void setValue(JTable table, String[] column1, String[] column2, int key)
	{
		DefaultTableCellRenderer rightRenderer;
		for (int row = 0; row < column1.length; row++)
		{
			table.setValueAt(column1[row], row, 0);
			table.setValueAt(column2[row], row, 1);
		}
		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(140);
		table.getColumnModel().getColumn(1).setPreferredWidth(160);

	}

	class IconActionListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == pre) cardLayout.next(cardPanel);
			else if(e.getSource() == add);
			else if(e.getSource() == edit);
			else if(e.getSource() == delete);
			else return;
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if(e.getSource() == pre) pre.setIcon(preImageEnter);
			else if(e.getSource() == add) add.setIcon(addIconEnter);
			else if(e.getSource() == edit) edit.setIcon(editIconEnter);
			else if(e.getSource() == delete) delete.setIcon(deleteIconEnter);
			else return;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(e.getSource() == pre) pre.setIcon(preImageExit);
			else if(e.getSource() == add) add.setIcon(addIconExit);
			else if(e.getSource() == edit) edit.setIcon(editIconExit);
			else if(e.getSource() == delete) delete.setIcon(deleteIconExit);
			else return;
		}
	}
}
