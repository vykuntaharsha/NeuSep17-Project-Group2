package com.neuSep17.ui.inventoryList.CenterSection;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CenterPanel extends JPanel {
    private InventoryTableView inventoryTableView;
    private IncentiveTableView incentiveTableView;
    private ImageView imageView;
    private InventoryServiceAPI_Test invsAPI;
    private IncentiveServiceAPI_Test incsApi;
    private List<Vehicle> currentVehicleList;

    public CenterPanel(InventoryServiceAPI_Test invsAPI, IncentiveServiceAPI_Test incsApi) throws IOException {
        this.invsAPI = invsAPI;
        this.incsApi = incsApi;
        this.currentVehicleList = invsAPI.getVehicles();
        JTabbedPane centerTabbedPane = new JTabbedPane();
        JTabbedPane tableTabbedPane = new JTabbedPane();
        imageView = new ImageView(currentVehicleList,invsAPI,incsApi);
        inventoryTableView = new InventoryTableView(incsApi,invsAPI);
        incentiveTableView = new IncentiveTableView(incsApi);

        this.setLayout(new BorderLayout());
        tableTabbedPane.add("Inventory", inventoryTableView);
        tableTabbedPane.add("Incentive", incentiveTableView);
        centerTabbedPane.add("TableView",tableTabbedPane);
        centerTabbedPane.add("ImageView",imageView);
        add(centerTabbedPane,"Center");
    }

    public void update(List<Vehicle> currentVehicleList){
        this.currentVehicleList = currentVehicleList;
        imageView.update(currentVehicleList);
        inventoryTableView.update(currentVehicleList);
    }
}
