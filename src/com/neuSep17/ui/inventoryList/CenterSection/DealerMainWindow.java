package com.neuSep17.ui.inventoryList.CenterSection;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DealerMainWindow extends JFrame implements Runnable {
	private InventoryServiceAPI_Test invsAPI;
	private IncentiveServiceAPI_Test incsApi;
	private List<Vehicle> totalVehicleList, currentVehicleList, searchedVehicles;
	private CenterPanel centerPanel;

	public DealerMainWindow(String selectedDealerID) throws IOException {
		invsAPI = new InventoryServiceAPI_Test("data/" + selectedDealerID);
		incsApi = new IncentiveServiceAPI_Test("data/IncentiveSample.txt");
		totalVehicleList = invsAPI.getVehicles();
		currentVehicleList = totalVehicleList;
		createMainFrame();
		createComponents();
		addComponents();

	}

	public void createMainFrame() {
		setTitle("Inventory Management");
		setSize(1920, 1200);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private void createComponents() throws IOException {
		centerPanel = new CenterPanel(invsAPI, incsApi);
	}

	private void addComponents() {
		Container con = getContentPane();
		BorderLayout bl = new BorderLayout();
		con.setLayout(bl);
		con.add(centerPanel, "Center");
	}

	@Override
	public void run() {
		makeThisVisible();
	}

	private void makeThisVisible() {
		this.setVisible(true);
	}

}
