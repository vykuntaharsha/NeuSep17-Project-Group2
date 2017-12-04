package com.neuSep17.ui.manageIncentives;

import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.IncentiveTableModel;
import com.neuSep17.service.IncentiveServiceAPI_Test;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by starhaotian on 01/12/2017.
 */
public class incentive_test {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        IncentiveServiceAPI_Test service = new IncentiveServiceAPI_Test("data/IncentiveSample.txt");
        myTableModel incentive_model = new myTableModel(service);
        JTable incentive_list = new JTable(incentive_model);

        JPanel panel = new listpanel(incentive_list);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);


    }
}


class listpanel extends JPanel{
    JButton button,add,refresh;
    JTextField text;

    public listpanel(JTable incentive_list){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        button = new JButton("edit");
        add = new JButton("add");
        refresh = new JButton("refresh");
        text = new JTextField();
        incentive_list.addMouseListener(new MouseAdapter(){    //鼠标事件
            public void mouseClicked(MouseEvent e){
                int selectedRow = incentive_list.getSelectedRow(); //获得选中行索引
                Object oa = incentive_list.getValueAt(selectedRow, 0);
                Object ob = incentive_list.getValueAt(selectedRow, 1);
                Object oc = incentive_list.getValueAt(selectedRow, 2);
                Object od = incentive_list.getValueAt(selectedRow, 3);
                Object oe = incentive_list.getValueAt(selectedRow, 4);
                Object of = incentive_list.getValueAt(selectedRow, 5);
                Object og = incentive_list.getValueAt(selectedRow, 6);

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        text.setText(oa.toString() + ob.toString()+ oc.toString() + od.toString() + oe.toString() + of.toString()+og.toString());

                    }
                });  //给文本框赋值
            }
        });
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                incentive_list.setValueAt("104400",5,0);
                incentive_list.repaint();
                //((AbstractTableModel) incentive_list.getModel()).fireTableDataChanged();

            }
        });
        refresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                incentive_list.setValueAt("100000",3,0);
                incentive_list.setValueAt("104400",5,0);
                incentive_list.updateUI();
            }
        });
        add(incentive_list);
        add(button);
        add(refresh);
        add(add);
        add(text);

    }

}

class table_listen implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}


class myTableModel extends AbstractTableModel {

    private IncentiveServiceAPI_Test incentiveAPI;
    private String[] columnTitles = {"ID", "Title", "Discount", "Start Date", "End Date", "Criterion", "Description"};

    public myTableModel(IncentiveServiceAPI_Test incentiveAPI) {
        this.incentiveAPI = incentiveAPI;
    }

    @Override
    public int getRowCount() {
        return incentiveAPI.getTotalIncentiveAmount();
    }

    @Override
    public int getColumnCount() {
        return columnTitles.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnTitles[columnIndex];

    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if ((columnIndex >= 0) && (columnIndex < getColumnCount())) {
            return getValueAt(0, columnIndex).getClass();
        } else {
            return Object.class;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<Incentive> incentives = incentiveAPI.getIncentives();
        Incentive incentive = incentives.get(rowIndex);
        Object incentiveData = null;
        switch (columnIndex) {
            case 0:
                incentiveData = incentive.getId();
                break;
            case 1:
                incentiveData = incentive.getTitle();
                break;
            case 2:
                incentiveData = incentive.getDiscount();
                break;
            case 3:
                incentiveData = incentive.getStartDate();
                break;
            case 4:
                incentiveData = incentive.getEndDate();
                break;
            case 5:
                incentiveData = incentive.getCriterion();
                break;
            case 6:
                incentiveData = incentive.getDescription();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }
        return incentiveData;

    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        // TODO Auto-generated method stub

    }

}


