package com.neuSep17.ui.inventoryList.CenterSection;

import com.neuSep17.ui.inventoryList.MainFrame.FilterPanel;
import com.neuSep17.ui.inventoryList.MainFrame.SearchPanel;
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

	private FilterPanel filterPanel;
	private SearchPanel searchPanel;
	private CenterPanel centerPanel;

	public DealerMainWindow() throws IOException {
		invsAPI = new InventoryServiceAPI_Test("data/gmps-blue-ribbon");
		incsApi = new IncentiveServiceAPI_Test("data/IncentiveSample.txt");
		totalVehicleList = invsAPI.getVehicles();
		currentVehicleList = totalVehicleList;
		createMainFrame();
		createComponents();
		addComponents();
		doSearch(null);
		addEventListeners();

	}

	public void createMainFrame() {
		setTitle("Inventory Management");
		setSize(1200, 800);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void createComponents() throws IOException {
		searchPanel = new SearchPanel(this, totalVehicleList);
		filterPanel = new FilterPanel(this);
		centerPanel = new CenterPanel(invsAPI, incsApi);
	}

	private void addComponents() {
		Container con = getContentPane();
		BorderLayout bl = new BorderLayout();
		con.setLayout(bl);
		searchPanel.setPreferredSize(new Dimension(1200, 200));
		con.add(searchPanel, "North");
		con.add(filterPanel, "West");
		con.add(centerPanel, "Center");
	}

	private void addEventListeners() {
		searchPanel.addListeners();
		filterPanel.addListeners();
	}

	public void doSearch(String search) {
		searchedVehicles = InventoryServiceAPI_Test.vehiclesSearchAndFilter(totalVehicleList, null, null, null, null,
				null, search);
		currentVehicleList = (ArrayList<Vehicle>) searchedVehicles;
		// new PagePanel(0);
		filterPanel.setCheckBoxPanelsMap(InventoryServiceAPI_Test.getComboBoxItemsMap(currentVehicleList));
		searchPanel.getSortItem().setSelectedIndex(0);
		centerPanel.update(currentVehicleList);
	}

	public void doFilter(String category, String year, String make, String price, String type, String currentChecked) {
		currentVehicleList = (ArrayList<Vehicle>) InventoryServiceAPI_Test.vehiclesSearchAndFilter(searchedVehicles,
				category, year, make, price, type, null);
		/// new PagePanel(0);
		filterPanel.setEnableCheckBoxMap(InventoryServiceAPI_Test.getComboBoxItemsMap(currentVehicleList),
				currentChecked);
		centerPanel.update(currentVehicleList);
	}

	public void doSort(String sortMethod) {
		if (sortMethod == null) {
			return;
		}

		boolean isAscending = true;
		if (sortMethod.contains(searchPanel.sortKeys[0])) {
			return;
		}

		String[] sortMethodSplit = sortMethod.split(":");
		String sortType = sortMethodSplit[0].toLowerCase().trim();

		isAscending = updateSortType(sortMethodSplit[1]);
		currentVehicleList = invsAPI.sortVehicles(searchedVehicles, sortType, isAscending);

		filterPanel.updateFilterConditions("");
		centerPanel.update(currentVehicleList);
	}

	private boolean updateSortType(String ascendDescribe) {
		if (ascendDescribe.toLowerCase().contains("high to low") || ascendDescribe.toLowerCase().contains("z - a")) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void run() {
		makeThisVisible();
	}

	private void makeThisVisible() {
		this.setVisible(true);
	}

	public static void main(String[] arg) throws IOException {
		DealerMainWindow dealerMainWindow = new DealerMainWindow();
		Thread BrowseInventoryThread = new Thread(() -> dealerMainWindow.run());
		SwingUtilities.invokeLater(BrowseInventoryThread);
	}

}
