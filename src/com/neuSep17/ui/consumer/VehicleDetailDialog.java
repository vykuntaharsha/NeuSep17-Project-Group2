package com.neuSep17.ui.consumer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

public class VehicleDetailDialog extends JDialog {
	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints gbs = new GridBagConstraints();

	public VehicleDetailDialog(JFrame owner, String id, IncentiveServiceAPI_Test incentiveServiceAPI_Test,
			InventoryServiceAPI_Test inventoryServiceAPI_Test) {
		super(owner, "VEHICLE DETAILS", false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addComponents(id, incentiveServiceAPI_Test, inventoryServiceAPI_Test);
		this.setSize(1000, 1000);
		this.setVisible(true);
	}

	private void addComponents(String id, IncentiveServiceAPI_Test incentiveServiceAPI_Test,
			InventoryServiceAPI_Test inventoryServiceAPI_Test) {
		Vehicle vehicle = inventoryServiceAPI_Test.getVehicleDetails(id);
		String[] urls = { vehicle.getPhotoUrl().toString() };              //TODO  NullPointer may happen when read from another dealer, don't know why
		ImageSlideShow imageSlideShow = new ImageSlideShow(vehicle, urls);
		DetailShow detailShow = new DetailShow(vehicle, incentiveServiceAPI_Test);
		this.setLayout(gbl);

		this.getContentPane().add(imageSlideShow);
		this.getContentPane().add(detailShow);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(50, 10, 0, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbl.setConstraints(imageSlideShow, gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 3;
		gbs.gridheight = 1;
		gbs.insets = new Insets(50, 0, 80, 100);
		gbs.weightx = 2;
		gbs.weighty = 1;
		gbs.gridx = 1;
		gbs.gridy = 0;
		gbl.setConstraints(detailShow, gbs);
		setVisible(true);
	}	
	

}
