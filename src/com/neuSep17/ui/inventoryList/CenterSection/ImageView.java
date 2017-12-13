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
    private HashMap<Vehicle, VehicleCardCell> vehicleCardCellsList = new HashMap<>();
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
        lazycell();

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
                tmp.setPreferredSize(new Dimension(300, 300));
                paneltt.add(tmp);
                if(j>=0 && j<2){
                    JPanel n = new JPanel();
                    n.setPreferredSize(new Dimension(17, 300));
                    paneltt.add(n);
                }
            }
            panel.add(paneltt);
            if(i>=0&&i<9){
                JPanel n = new JPanel();
                n.setPreferredSize(new Dimension(300,25));
                panel.add(n);
            }
        }
        jScrollPane.add(panel);
        jScrollPane.setViewportView(panel);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(20);
    }

    //每次加载清空map，生成当前list中的对应效果
    private void lazycell(){
        vehicleCardCellsList.clear();
        for (Vehicle v : pageController.getSmallList()) {
            int i = (int) Math.round(2 * Math.random());
            Image temp = null;
            try {
                temp = InventoryServiceAPI_Test.getVehicleImage(v.getBodyType()).get(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
            ImageIcon imageIcon = new ImageIcon(temp, "icon for vehicle " + v.getId());
            vehicleCardCellsList.put(v, new VehicleCardCell(v, imageIcon, 1200, invsAPI, incsApi));
        }
    }


    public void update(List<Vehicle> currentVehicleList) {
        pageController = new PageController(currentVehicleList,12);
        lazycell();
        jScrollPaneDisplay();
        jScrollPane.getVerticalScrollBar().setValue(0);
        this.remove(imageControlPanel);
        this.revalidate();
        imageControlPanel = new ImageControlPanel(pageController,this);
        this.add(imageControlPanel,"North");
        this.updateUI();
    }

    public void update() {
        lazycell();
        jScrollPaneDisplay();
        jScrollPane.getVerticalScrollBar().setValue(0);
        this.remove(imageControlPanel);
        this.revalidate();
        imageControlPanel = new ImageControlPanel(pageController,this);
        this.add(imageControlPanel,"North");
        this.updateUI();
    }

    public void setPageController(PageController<Vehicle> pageController) {
        this.pageController = pageController;
    }
//
//    class updateThread extends SwingWorker<Void, Void> {
//        @Override
//        protected Void doInBackground(){
//            vehicleCardCellsList.clear();
//            for (Vehicle v : pageController.getSmallList()) {
//                int i = (int) Math.round(2 * Math.random());
//                Image temp = null;
//                try {
//                    temp = InventoryServiceAPI_Test.getVehicleImage(v.getBodyType()).get(i);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                ;
//                ImageIcon imageIcon = new ImageIcon(temp, "icon for vehicle " + v.getId());
//                vehicleCardCellsList.put(v, new VehicleCardCell(v, imageIcon, 1200, invsAPI, incsApi));
//            }
//            return null;
//        }
//
//        protected void done() {
//            System.out.println("update finished");
//        }
//    }
}