package com.neuSep17.ui.consumer;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;

public class DetailShow extends JPanel {
	// JLabel MSRP;
	JLabel SalePrice;
	JLabel currentOffers;
	JLabel specifications;
	JTable curTable;
	JTable specTable;
	String[] detailType = { "ID", "WEBID", "CATEGORY", "YEAR", "MAKE", "MODEL", "TRIM", "TYPE", "PRICE" };
	String[] discountContent = { "MSRP", "SALE PRICE", "START DATE", "END DATE", "TOTAL SAVINGS" };
	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints gbs = new GridBagConstraints();

	public DetailShow(Vehicle vehicle, IncentiveServiceAPI_Test incentiveServiceAPI_Test) {
		createComponents(vehicle, incentiveServiceAPI_Test);
		addComponents(vehicle, incentiveServiceAPI_Test);
		setVisible(true);
	}

	private void createComponents(Vehicle vehicle, IncentiveServiceAPI_Test incentiveServiceAPI_Test) {
		// MSRP=new JLabel("MSRP: "+vehicle.getPrice());
		SalePrice = new JLabel();
		float salePrice = vehicle.getPrice() - incentiveServiceAPI_Test.getAllDiscount(vehicle);
		SalePrice.setText("Sale Price:   " + salePrice);

		currentOffers = new JLabel("CurrentOffers");
		specifications = new JLabel("Specifications");
		curTable = new JTable(discountContent.length, 2);
		specTable = new JTable(detailType.length, 2);
	}

	private void addComponents(Vehicle vehicle, IncentiveServiceAPI_Test incentiveServiceAPI_Test) {
		setLayout(gbl);
		// add(MSRP);
		add(SalePrice);
		add(specifications);
		add(specTable);
		add(currentOffers);
		add(curTable);
		String[] details = { vehicle.getId(), vehicle.getWebId(), vehicle.getCategory().toString(),
				vehicle.getYear().toString(), vehicle.getMake(), vehicle.getModel(), vehicle.getTrim(),
				vehicle.getBodyType(), vehicle.getPrice().toString() };
		float salePrice = vehicle.getPrice() - incentiveServiceAPI_Test.getAllDiscount(vehicle);
		String[] discount = { vehicle.getPrice().toString(), "" + salePrice, "TBD", "TBD",
				"" + incentiveServiceAPI_Test.getAllDiscount(vehicle) };

		setValue(curTable, discountContent, discount);
		setValue(specTable, detailType, details);
		curTable.setRowHeight(30);
		specTable.setRowHeight(30);
		// MSRP.setFont(new Font("Menu.font", Font.PLAIN, 15));
		// MSRP.setHorizontalAlignment(JLabel.RIGHT);
		SalePrice.setFont(new Font("Menu.font", Font.PLAIN, 20));
		SalePrice.setHorizontalAlignment(JLabel.RIGHT);
		curTable.setFont(new Font("Menu.font", Font.PLAIN, 15));
		specTable.setFont(new Font("Menu.font", Font.PLAIN, 15));
		currentOffers.setFont(new Font("Menu.font", Font.PLAIN, 20));
		specifications.setFont(new Font("Menu.font", Font.PLAIN, 20));

		// gbs.fill=GridBagConstraints.BOTH;
		// gbs.gridwidth=1;gbs.gridheight=1;
		// gbs.insets=new Insets(0, 0, 0, 0);
		// gbs.weightx=1;gbs.weighty=1;
		// gbs.gridx=0;gbs.gridy=0;
		// gbl.setConstraints(MSRP,gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(0, 0, 30, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbl.setConstraints(SalePrice, gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(30, 0, 0, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 1;
		gbl.setConstraints(specifications, gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(0, 0, 10, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 2;
		gbl.setConstraints(specTable, gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(5, 0, 0, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 3;
		gbl.setConstraints(currentOffers, gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(0, 0, 0, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 4;
		gbl.setConstraints(curTable, gbs);
	}

	private void setValue(JTable table, String[] column1, String[] column2) {
		for (int row = 0; row < column1.length; row++) {
			table.setValueAt(column1[row], row, 0);
			table.setValueAt(column2[row], row, 1);
		}
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

	}
}
