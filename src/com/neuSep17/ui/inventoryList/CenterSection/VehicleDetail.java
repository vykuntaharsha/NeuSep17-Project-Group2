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

public class VehicleDetail extends JPanel
{
	// JLabel MSRP;

	JLabel currentOffers;
	JLabel specifications;
	JTable curTable;
	JTable specTable;
	String[] detailType = { "  ID", "  WEBID", "  CATEGORY", "  YEAR", "  MAKE", "  MODEL", "  TRIM", "  TYPE", "  PRICE" };
	String[] discountContent = { "  SALE PRICE", "  START DATE", "  END DATE", "  TOTAL SAVINGS" };
	JLabel pre, background;
	CardLayout cardLayout;
	JPanel cardPanel;

	public VehicleDetail(Vehicle vehicle, IncentiveServiceAPI_Test incentiveServiceAPI_Test, CardLayout cardLayout, JPanel cardPanel)
	{
		this.cardPanel = cardPanel;
		this.cardLayout = cardLayout;
		setSize(800,600);
		createComponents(vehicle, incentiveServiceAPI_Test);
		addComponents(vehicle, incentiveServiceAPI_Test);
		setVisible(true);
	}

	private void createComponents(Vehicle vehicle, IncentiveServiceAPI_Test incentiveServiceAPI_Test)
	{

		currentOffers = new JLabel("Current Offers");
		specifications = new JLabel("Specifications");
		curTable = new JTable(discountContent.length, 2);
		specTable = new JTable(detailType.length, 2);
		ImageIcon preImageEnter = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/left80%.png");
		ImageIcon preImageExit = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/left50%.png");
		pre = new JLabel(preImageExit);

		ImageIcon imageIcon = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/background.png");
		background = new JLabel(imageIcon);
		background.setVerticalAlignment(SwingConstants.TOP);
		background.setHorizontalAlignment(SwingConstants.LEFT);


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
		curTable.setSelectionBackground(Color.DARK_GRAY);
		specTable.setRowHeight(18);
		specTable.setDefaultEditor(Object.class, null);
		specTable.setFocusable(false);
		specTable.setShowGrid(false);
		specTable.setSelectionBackground(Color.DARK_GRAY);
		curTable.setFont(new Font("Menu.font", Font.PLAIN, 16));
		specTable.setFont(new Font("Menu.font", Font.PLAIN, 16));


		pre.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.next(cardPanel);
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				pre.setIcon(preImageEnter);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				pre.setIcon(preImageExit);
			}
		});
	}

	private void addComponents(Vehicle vehicle, IncentiveServiceAPI_Test incentiveServiceAPI_Test)
	{
		setLayout(null);

		specTable.setBounds(0,15,300,180);
		curTable.setBounds(0,210,300,85);
		pre.setBounds(0,0,50,300);
		background.setBounds(0,0,300,300);
		// add(MSRP);
		//add(specifications);
		add(background);
		add(pre);
		add(specTable);
		//add(currentOffers);
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
}
