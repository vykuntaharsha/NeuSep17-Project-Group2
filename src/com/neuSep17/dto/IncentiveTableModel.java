package com.neuSep17.dto;

import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.neuSep17.service.IncentiveServiceAPI_Test;

public class IncentiveTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IncentiveServiceAPI_Test incentiveAPI;	
  private String[] columnTitles = {"ID", "Title", "Discount", "Start Date", "End Date", "Criterion", "Description"};

	public IncentiveTableModel(IncentiveServiceAPI_Test incentiveAPI) {
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
	
	public void refreshIncentivesTable() {
		this.fireTableDataChanged();
	}
}
