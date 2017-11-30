package src.finalproject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ManagementListApplication extends JFrame{
    private JTable jTable;
    private ManagementListTableModel tableModel;
    private JButton edit, delete, add, exit, firstPage, next, pre, lastPage, shift_left, shift_right, enter, searchButton;
    private VehicleManagerBehaviors vmb;
    private PageController pageController;
    //Ryan
    private JLabel searchLabel, vinLabel, categoryLabel, yearLabel, makeLabel,totalPage, currentPage;
    private JTextField searchField, vinField, categoryField, yearField, makeField, jumpPage;


    public ManagementListApplication(VehicleManagerBehaviors vmb){
        this.vmb = vmb;
        create();
        addComponents();
        makeListeners();
        display();
        setTitle("ManagementList Application..");
    }

    public void create() {


        //FIXME 分页传入vmb

        pageController = new PageController(vmb);
        tableModel = new ManagementListTableModel(pageController.setFirstPage());
        jTable = new JTable(tableModel);
        add = new JButton("Add");
        delete = new JButton("Delete");
        edit = new JButton("Edit");
        exit = new JButton("Exit");
        currentPage = new JLabel("Current Page: ");
        jumpPage = new JFormattedTextField("" + pageController.getCurentPageIndex());
        jumpPage.setColumns(5);
        enter = new JButton("Enter");
        firstPage = new JButton("1");
        shift_left = new JButton("<<");
        pre = new JButton("Pre");
        next = new JButton("Next");
        shift_right = new JButton(">>");
        //currentPage = new JLabel("currentPage: ");
        //currentItem = new JLabel("currentItem: ");
        lastPage = new JButton(pageController.getPageCount()+"");

        //Ryan
        searchLabel = new JLabel("Search Vehicle: ");
        vinLabel = new JLabel("VIN: ");
        categoryLabel = new JLabel("Category: ");
        yearLabel = new JLabel("Year: ");
        makeLabel = new JLabel("Make");

        searchField = new JFormattedTextField("");
        searchField.setColumns(5);
        vinField = new JFormattedTextField("");
        vinField.setColumns(10);
        categoryField = new JFormattedTextField("");
        categoryField.setColumns(10);
        yearField = new JFormattedTextField("");
        yearField.setColumns(10);
        makeField = new JFormattedTextField("");
        makeField.setColumns(10);

        searchButton = new JButton("Search");
        lastPage = new JButton("LastPage");
        totalPage = new JLabel("Total Page: "+pageController.getPageCount());
    }

    public void addComponents() {
        Container con = getContentPane();
        BorderLayout bl = new BorderLayout();


        con.setLayout(bl);
        // Add Table to the north..
        con.add(makeTablePanel(), "Center");
        // Add the screen to enter information in center - create a panel for
        // the input..
        con.add(makePageButtonPanel(), "North");
        // Add the buttons to south - create a panel from the buttons..
        con.add(makeActionButtonPanel(), "South");
    }

    private JPanel makePageButtonPanel() {
        JPanel panel = new JPanel();
        //Ryan
        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);


        panel.add(currentPage);
        panel.add(jumpPage);
        panel.add(enter);
        panel.add(firstPage);
        panel.add(shift_left);
        panel.add(pre);
        panel.add(next);
        panel.add(shift_right);
        panel.add(lastPage);
        panel.add(totalPage);
//        panel.add(currentItem);
        panel.setBorder(new TitledBorder("Pages section.."));
        return panel;
    }

    private JPanel makeActionButtonPanel() {
        JPanel panel = new JPanel();
        //Ryan
        panel.add(vinLabel);
        panel.add(vinField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(yearLabel);
        panel.add(yearField);
        panel.add(makeLabel);
        panel.add(makeField);


        panel.add(edit);
        panel.add(add);
        panel.add(delete);
        panel.add(exit);
        panel.setBorder(new TitledBorder("Actions section.."));
        return panel;
    }

    private JScrollPane makeTablePanel() {
        TableColumn tableColumn = jTable.getColumnModel().getColumn(8);
        LinkCellRenderer renderer = new LinkCellRenderer();
        tableColumn.setCellRenderer(renderer);
        jTable.addMouseListener(renderer);
        jTable.addMouseMotionListener(renderer);
        JScrollPane sp=new JScrollPane(jTable);
        jTable.setLocale(Locale.getDefault());
        jTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(160);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(180);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(180);
        jTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        jTable.getColumnModel().getColumn(8).setPreferredWidth(260);
        sp.setSize(900,500);
        sp.setBorder(new TitledBorder("Vehicles information.."));
        //sp.getViewport().setBackground(Color.white);
        return sp;
    }

    public void makeListeners() {
        ManageActionListener mal = new ManageActionListener();
        edit.addActionListener(mal);
        add.addActionListener(mal);
        delete.addActionListener(mal);
        exit.addActionListener(mal);
        enter.addActionListener(mal);
        firstPage.addActionListener(mal);
        shift_left.addActionListener(mal);
        pre.addActionListener(mal);
        next.addActionListener(mal);
        shift_right.addActionListener(mal);
        lastPage.addActionListener(mal);
    }

    class ManageActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO 接受筛选数据 + add,delete,edit界面跳转
           // ArrayList<Vehicle> vehicles = getfiltrateFromInputSection();
            if(e.getSource() == edit){
                //TODO jump to a new edit window, and find and replace the vehicle i n vmb;
               // edit();
              //  vmb.saveVehicle();
            }
            if (e.getSource() == add) {
                //FIXME how to add ? jump to new add window, add return one Vehicle Object and then add to vmb;
                pageController.getSmallList().get(jTable.getSelectedRow());
               //jTable.getSelectedRowCount();
                //vmb.saveVehicle(Vehicle);
            }
            if (e.getSource() == delete) {
                //TODO delete the data in vmb(permanently delete);
               // deleteStudent(student);
            }
            if (e.getSource() == exit) {
                // exit(student);
                System.exit(0);
            }
            if(e.getSource() == enter){
                int jump = Integer.parseInt(jumpPage.getText().trim());
                pageController.jumpPage(jump);
            }
            if (e.getSource() == firstPage) {
                tableModel.setVehicles(pageController.setFirstPage());
            }
            if(e.getSource() == shift_left){
                tableModel.setVehicles(pageController.shiftLeftPage());
            }
            if (e.getSource() == pre) {
                tableModel.setVehicles(pageController.previousPage());
            }
            if (e.getSource() == next) {
                tableModel.setVehicles(pageController.nextPage());
            }
            if(e.getSource() == shift_right){
                tableModel.setVehicles(pageController.shiftRightPage());
            }
            if (e.getSource() == lastPage) {
                tableModel.setVehicles(pageController.setLastPage());
            }
            jumpPage.setText(pageController.getCurentPageIndex()+"");
            // Replaces the current UI object with the latest version
            jTable.updateUI();
            //jTable.setModel(tableModel);
            //jTable.repaint();
        }

    }

    public void display() {
        setSize(1400, 500);
        setVisible(true);
        //pack();
    }

    public static void main(String[] args)throws FileNotFoundException, IOException{
        //FIXME fake data
        char[] a;

        Map <String, Vehicle> vehicles = new HashMap<>();
        File f = new File("tmp");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String line;
        while((line=br.readLine())!=null){
            Vehicle tmp = new Vehicle(line.split("~"));
            vehicles.put(tmp.getId(),tmp);
        }
        VehicleManagerBehaviors vmb = new VehicleManagerAlternateImplementation(vehicles);
        new ManagementListApplication(vmb);
    }
}

class ManagementListTableModel implements TableModel{
    private ArrayList<Vehicle> vehicles;

    public ManagementListTableModel(ArrayList<Vehicle> vehicles){
        this.vehicles = vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
        System.out.println(vehicles.size());
    }

    @Override
    public int getRowCount() {
        return vehicles.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if(columnIndex==0) return "Id";
        if(columnIndex==1) return "Category";
        if(columnIndex==2) return "Year";
        if(columnIndex==3) return "Make";
        if(columnIndex==4) return "Model";
        if(columnIndex==5) return "Trim";
        if(columnIndex==6) return "Type";
        if(columnIndex==7) return "Price";
        if(columnIndex==8) return "Photo";
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vehicle vehicle = vehicles.get(rowIndex);
        if (columnIndex == 0) {
            return vehicle.getId();
        }
        if(columnIndex == 1){
            return vehicle.getCategory();
        }
        if(columnIndex == 2){
            return vehicle.getYear();
        }
        if(columnIndex == 3){
            return vehicle.getMake();
        }
        if(columnIndex == 4){
            return vehicle.getModel();
        }
        if(columnIndex == 5){
            return vehicle.getTrim();
        }
        if(columnIndex == 6){
            return vehicle.getBodyType();
        }
        if(columnIndex == 7){
            return vehicle.getPrice();
        }
        return vehicle.getPhotoUrl();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

}
