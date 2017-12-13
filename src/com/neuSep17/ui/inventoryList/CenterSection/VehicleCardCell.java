package com.neuSep17.ui.inventoryList.CenterSection;

import com.neuSep17.ui.inventoryList.CenterSection.VehicleImageCell;
import com.neuSep17.dto.Vehicle;

import java.awt.*;
import javax.swing.ImageIcon;
        import javax.swing.JPanel;

import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

import java.io.IOException;

public class VehicleCardCell extends JPanel {
    JPanel cardPanel;
    VehicleDetail vehicleDetail;
    VehicleImageCell vehicleImageCell;

    public VehicleCardCell(Vehicle v, ImageIcon icon, float discount, InventoryServiceAPI_Test invsAPI, IncentiveServiceAPI_Test
                           incsAPI)  {
        super();
        setSize(800,600);
        cardPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        try {
            vehicleImageCell = new VehicleImageCell(v,icon,discount,cardLayout,cardPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        vehicleDetail = new VehicleDetail(v,invsAPI,incsAPI,cardLayout,cardPanel);
        cardPanel.add("vehicleImageCell",vehicleImageCell);
        cardPanel.add("vehicleDetail",vehicleDetail);
        this.setLayout(new GridLayout());
        this.add(cardPanel);

    }



}
