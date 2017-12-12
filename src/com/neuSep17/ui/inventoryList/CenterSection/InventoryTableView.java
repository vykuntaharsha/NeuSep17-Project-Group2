package CenterSection;

import CenterSection.InventoryControlPanel;
import com.neuSep17.dto.InventoryTableModel;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class InventoryTableView extends JPanel{

    private ivTableModel ivTableModel;
    private List<Vehicle> vehicles;
    private IncentiveServiceAPI_Test incentiveServiceAPI_test;
    private PageController<Vehicle> pageController;
    private InventoryControlPanel inventoryControlPanel;
    private JTable jTable;
    private JScrollPane sp;
    private JPanel functionPanel = new JPanel();

    public InventoryTableView(List<Vehicle> vehicles, IncentiveServiceAPI_Test incentiveServiceAPI_test){
        this.setSize(1100,1000);
        this.vehicles = vehicles;
        this.incentiveServiceAPI_test = incentiveServiceAPI_test;
        this.pageController = new PageController(vehicles,15);
        this.inventoryControlPanel = new InventoryControlPanel(pageController,this);

        sp = new JScrollPane(new JTable());
        sp.setBorder(new TitledBorder("Vehicles information.."));
        sp.setPreferredSize(new Dimension(1050,700));
        creatJTable();
        createFunctionPanel();
        this.setLayout(new BorderLayout());
        this.add(inventoryControlPanel,"North");
        this.add(sp,"Center");
        this.add(functionPanel,"South");
    }

    public void update(){
        creatJTable();
        this.remove(inventoryControlPanel);
        this.revalidate();
        inventoryControlPanel = new InventoryControlPanel(pageController,this);
        this.add(inventoryControlPanel,"North");
        this.updateUI();
    }

    private void createFunctionPanel(){
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
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
        JCheckBox jCheckBox = new JCheckBox();
        TableColumn tableColumn = jTable.getColumnModel().getColumn(0);
        tableColumn.setCellEditor(new DefaultCellEditor(jCheckBox));

        sp.add(jTable);
        sp.setViewportView(jTable);
    }

    public void setPageController(PageController<Vehicle> pageController) {
        this.pageController = pageController;
    }
}
