package com.neuSep17.ui.inventoryList.CenterSection;

import com.neuSep17.ui.inventoryList.CenterSection.ImageControlPanel;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ImageView extends JPanel{
    //private List<Vehicle> currentVehicle;
    private static HashMap<Vehicle, VehicleCardCell> vehicleCardCellsList = new HashMap<>();
    private PageController<Vehicle> pageController;
    private ImageControlPanel imageControlPanel;
    InventoryServiceAPI_Test invsAPI;
    IncentiveServiceAPI_Test incsApi;
    private JScrollPane jScrollPane = new JScrollPane();

    public ImageView(java.util.List<Vehicle> currentVehicle,InventoryServiceAPI_Test invsAPI,IncentiveServiceAPI_Test
                     incsApi) throws IOException {
        this.invsAPI = invsAPI;
        this.incsApi = incsApi;
        pageController = new PageController<Vehicle>(currentVehicle, 12);
        imageControlPanel = new ImageControlPanel(pageController,this);
        lazycell(pageController.getSmallList());

        updateThread td = new updateThread();
        td.execute();

        this.setLayout(new BorderLayout());
        jScrollPaneDisplay();
        this.add(jScrollPane,"Center");
        this.add(imageControlPanel,"North");
    }

    private void jScrollPaneDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < pageController.getSmallList().size(); i = i + 3) {
            JPanel paneltt = new JPanel();
            for (int j = 0; j < 3 && i + j < pageController.getSmallList().size(); j++) {
                JPanel tmp = vehicleCardCellsList.get(pageController.getSmallList().get(i + j));
                tmp.setPreferredSize(new Dimension(320, 330));
                paneltt.add(tmp);
            }
            panel.add(paneltt);
        }
        jScrollPane.add(panel);
        jScrollPane.setViewportView(panel);
    }

    private void lazycell(List<Vehicle> currentVehicle) throws IOException {
        Image notfound = ImageIO.read(new File("data/images/imagenotfound.jpg"));
            for (Vehicle v : currentVehicle) {
                if (!vehicleCardCellsList.containsKey(v)) {
                    int i = (int) Math.round(2 * Math.random());
                    Image temp;
                    try {
                        temp = InventoryServiceAPI_Test.getVehicleImage(v.getBodyType()).get(i);
                    } catch (IOException e1) {
                        temp = notfound;
                        e1.printStackTrace();
                    }
                    ImageIcon imageIcon = new ImageIcon(temp, "icon for vehicle " + v.getId());
                    vehicleCardCellsList.put(v, new VehicleCardCell(v, imageIcon, 1200, invsAPI, incsApi));
                }
            }
    }


    public void update(){
        jScrollPaneDisplay();
        this.remove(imageControlPanel);
        this.revalidate();
        imageControlPanel = new ImageControlPanel(pageController,this);
        this.add(imageControlPanel,"North");
        this.updateUI();
    }

    public void setPageController(PageController<Vehicle> pageController) {
        this.pageController = pageController;
    }

    class updateThread extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            lazycell(pageController.getBigList());
            return null;
        }

        protected void done() {
            System.out.println("update finished");
        }
    }
}