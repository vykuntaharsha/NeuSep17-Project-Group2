package com.neuSep17.ui.inventoryList.CenterSection;

import com.neuSep17.service.InventoryServiceAPI_Test;
import com.neuSep17.ui.inventoryList.CenterSection.InventoryControlPanel;
import com.neuSep17.dto.InventoryTableModel;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.ui.inventoryList.adeButton.AddFramecontrol;
import com.neuSep17.ui.inventoryList.adeButton.DeleteFrame;
import com.neuSep17.ui.inventoryList.adeButton.EditFramecontrol;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.util.List;

public class InventoryTableView extends JPanel{

    private ivTableModel ivTableModel;
    public InventoryServiceAPI_Test inventoryServiceAPI_test;
    private IncentiveServiceAPI_Test incentiveServiceAPI_test;
    private PageController<Vehicle> pageController;
    private InventoryControlPanel inventoryControlPanel;
    private JTable jTable;
    private JScrollPane sp;
    private JPanel functionPanel = new JPanel();
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;


    public InventoryTableView(IncentiveServiceAPI_Test incentiveServiceAPI_test,InventoryServiceAPI_Test inventoryServiceAPI_test){
        this.setSize(1100,1000);
        this.inventoryServiceAPI_test = inventoryServiceAPI_test;
        this.incentiveServiceAPI_test = incentiveServiceAPI_test;
        this.pageController = new PageController(inventoryServiceAPI_test.getVehicles(),15);
        this.inventoryControlPanel = new InventoryControlPanel(this);

        sp = new JScrollPane(new JTable());
        sp.setBorder(new TitledBorder("Vehicles information.."));
        sp.setPreferredSize(new Dimension(1050,700));
        creatJTable();
        createFunctionPanel();
        this.setLayout(new BorderLayout());
        this.add(inventoryControlPanel,"North");
        this.add(sp,"Center");
        this.add(functionPanel,"South");
        makeListeners();
    }

    public PageController<Vehicle> getPageController() {
        return pageController;
    }

    public void update(List<Vehicle> currentVehicleList){
        pageController = new PageController<>(currentVehicleList,15);
        creatJTable();
        this.remove(inventoryControlPanel);
        this.revalidate();
        inventoryControlPanel = new InventoryControlPanel(this);
        this.add(inventoryControlPanel,"North");
        this.updateUI();
    }

    public void update(){
        creatJTable();
        this.remove(inventoryControlPanel);
        this.revalidate();
        inventoryControlPanel = new InventoryControlPanel(this);
        this.add(inventoryControlPanel,"North");
        this.updateUI();
    }

    private void createFunctionPanel(){
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        functionPanel.add(addButton);
        functionPanel.add(editButton);
        functionPanel.add(deleteButton);
    }

    private void creatJTable(){
        ivTableModel = new ivTableModel(pageController.getSmallList(),incentiveServiceAPI_test);
        jTable = new JTable(ivTableModel);
        jTable.setShowGrid(false);
        jTable.setSelectionBackground(Color.DARK_GRAY);
        jTable.setRowHeight(30);
        jTable.setPreferredSize(new Dimension(700,700));
        jTable.setFont(new Font("Menu.font", Font.PLAIN, 16));
        jTable.getTableHeader().setFont(new Font("Menu.font", Font.PLAIN, 16));
        jTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(160);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(180);
        jTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        jTable.getColumnModel().getColumn(8).setPreferredWidth(100);
        jTable.setColumnSelectionAllowed(true);
//        jTable.getTableHeader().addMouseListener(new MouseInputListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                Point p = e.getPoint();
//                int c = jTable.columnAtPoint(p);
//                if(c == 0){
//                    boolean flag = true;
//                    for(int i=0;i<pageController.getSmallList().size();i++){
//                        if(!(boolean)jTable.getValueAt(i,0)) flag = false;
//                        jTable.setValueAt(true,i,0);
//                    }
//                    if(flag){
//                        for(int i=0;i<pageController.getSmallList().size();i++){
//                            jTable.setValueAt(false,i,0);
//                        }
//                    }
//                }
//                jTable.updateUI();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseDragged(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                jTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            }
//        });
        JCheckBox jCheckBox = new JCheckBox();
        TableColumn tableColumn = jTable.getColumnModel().getColumn(0);
        tableColumn.setCellEditor(new DefaultCellEditor(jCheckBox));

        sp.add(jTable);
        sp.setViewportView(jTable);
    }

    public void makeListeners() {
        ManageActionListener mal = new ManageActionListener();
        editButton.addActionListener(mal);
        addButton.addActionListener(mal);
        deleteButton.addActionListener(mal);

    }

    class ManageActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == editButton){
                EditFramecontrol editFrame = new EditFramecontrol(inventoryServiceAPI_test.getVehicleDetails((String)ivTableModel.getValueAt(jTable.getSelectedRow(), 1)));
                realTimeUpdateForEdit rte = new realTimeUpdateForEdit(editFrame);
                rte.start();
            }
            if (e.getSource() == addButton) {
                AddFramecontrol addFrame = new AddFramecontrol();
                realTimeUpdateForAdd rta = new realTimeUpdateForAdd(addFrame);
                rta.start();
            }
            if (e.getSource() == deleteButton) {
                DeleteFrame deleteFrame = new DeleteFrame();
                realTimeUpdateForDelete rtd = new realTimeUpdateForDelete(deleteFrame);
                rtd.start();
            }
        }

    }

    class realTimeUpdateForDelete extends Thread{
        private DeleteFrame deleteFrame;
        public realTimeUpdateForDelete(DeleteFrame deleteFrame){
            this.deleteFrame = deleteFrame;
        }
        public void validate(){
           // int temp = jTable.getSelectedRow();
            //inventoryServiceAPI_test.deleteVehicle(ivTableModel.getValueAt(temp, 1).toString());
            for(int i = 0;i<pageController.getSmallList().size();i++){
                inventoryServiceAPI_test.deleteVehicle(ivTableModel.getValueAt(i, 1).toString());
            }
            update(inventoryServiceAPI_test.getVehicles());

            deleteFrame.IsYes = false;
        }
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(1000);
                    if(deleteFrame.IsYes == true){
                        validate();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class realTimeUpdateForEdit extends Thread{
        private EditFramecontrol editFrame;
        public realTimeUpdateForEdit(EditFramecontrol editFrame){
            this.editFrame = editFrame;
        }
        public void validate() throws MalformedURLException {
            int temp = jTable.getSelectedRow();
            replaceVehicle(editFrame.tempVehicle,temp);
            int curpage = pageController.getCurentPageIndex();
            pageController = new PageController<>(inventoryServiceAPI_test.getVehicles(),15);
            pageController.setCurentPageIndex(curpage);
            update(inventoryServiceAPI_test.getVehicles());
            editFrame.editFlag = false;
        }
        private void replaceVehicle(String[] tempVehicle,int temp) throws MalformedURLException {
            String id = ivTableModel.getValueAt(temp, 1).toString();
            if(!tempVehicle[0].equals(""))
                inventoryServiceAPI_test.editVehicle(id, 2, tempVehicle[0]);
            if(!tempVehicle[1].equals(""))
                inventoryServiceAPI_test.editVehicle(id, 3, tempVehicle[1]);
            if(!tempVehicle[2].equals(""))
                inventoryServiceAPI_test.editVehicle(id, 4, tempVehicle[2]);
            if(!tempVehicle[3].equals(""))
                inventoryServiceAPI_test.editVehicle(id, 5, tempVehicle[3]);
            if(!tempVehicle[4].equals(""))
                inventoryServiceAPI_test.editVehicle(id, 6, tempVehicle[4]);
            if(!tempVehicle[5].equals(""))
                inventoryServiceAPI_test.editVehicle(id, 7, tempVehicle[5]);
            if(!tempVehicle[6].equals(""))
                inventoryServiceAPI_test.editVehicle(id, 8, tempVehicle[6]);
            if(!tempVehicle[7].equals(""))
                inventoryServiceAPI_test.editVehicle(id, 9, tempVehicle[7]);
        }
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(1000);
                    if(editFrame.editFlag == true){
                        validate();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    class realTimeUpdateForAdd extends Thread{
        private AddFramecontrol addFrame;
        public realTimeUpdateForAdd(AddFramecontrol addFrame){
            this.addFrame = addFrame;
        }
        public void validate(){
            inventoryServiceAPI_test.addVehicle(addFrame.getAddVehicle());
            pageController = new PageController<>(inventoryServiceAPI_test.getVehicles(),15);
            pageController.setLastPage();
            update();
            addFrame.addFlag = false;
        }
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(1000);
                    if(addFrame.addFlag == true){
                        validate();
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

}
