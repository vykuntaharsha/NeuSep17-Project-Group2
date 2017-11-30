package com.neuSep17.dto;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class IncentiveListTableModel implements TableModel {
    ArrayList<Incentive> incentive_data;

    public IncentiveListTableModel(ArrayList<Incentive> incentive_data){
        this.incentive_data = incentive_data;
    }

    @Override
    public int getRowCount() {
        return incentive_data.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0)
            return "Incentive Id";
        if (columnIndex == 1)
            return "Web Id";
        if (columnIndex == 2)
            return "Title";
        if (columnIndex == 3)
            return "Discount";
        if (columnIndex == 4)
            return "Start Date";
        if (columnIndex == 5)
            return "End Date";
        if (columnIndex == 6)
            return "Criterion";
        if (columnIndex == 7)
            return "Description";

        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class trueClass = Object.class;

        if(getRowCount() > 0)
            trueClass =  getValueAt(0, columnIndex).getClass();

        return trueClass;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Incentive incentive = incentive_data.get(rowIndex);
        if (columnIndex == 0){
            return incentive.getId();
        }

        if (columnIndex == 1){
            return incentive.getWebId();
        }

        if (columnIndex == 2){
            return incentive.getTitle();
        }

        if (columnIndex == 3){
            return incentive.getDiscount();
        }

        if (columnIndex == 4){
            return incentive.getStartDate();
        }

        if (columnIndex == 5){
            return incentive.getEndDate();
        }

        if (columnIndex == 6){
            ArrayList<String> s =  incentive.getCriterion();
            ArrayList<String> result = new ArrayList<>();
            for (String a : s){
                if (a.equals("no")){
                }else {
                    result.add(a);
                }
            }
            return result;

        }

        if (columnIndex == 7){
            return incentive.getDescription();
        }

        return null;

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
