package com.neuSep17.dto;

import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

public class InventoryTableModel implements TableModel {
	private InventoryServiceAPI_Test inventoryAPI;
	private IncentiveServiceAPI_Test incentiveAPI;

    private String[] columnTitles = {"VIN", "Category", "Year", "Make", "Model", "Trim", "Type", "Price"};

	public InventoryTableModel(InventoryServiceAPI_Test inventoryAPI,IncentiveServiceAPI_Test incentiveAPI) {
		this.inventoryAPI = inventoryAPI;
		this.incentiveAPI = incentiveAPI;
	}

	@Override
	public int getRowCount() {
		return inventoryAPI.getTotalVehicleAmount();
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
		ArrayList<Vehicle> vehicles = inventoryAPI.getVehicles();
		Vehicle vehicle = vehicles.get(rowIndex);
		Object vehicleData = null;
		switch (columnIndex) {
        case 0:
        		vehicleData = vehicle.getId();
            break;
        case 1:
        		vehicleData = vehicle.getCategory();
            break;
        case 2:
        		vehicleData = vehicle.getYear();
            break;
        case 3:
        		vehicleData = vehicle.getMake();
            break;
        case 4:
        		vehicleData = vehicle.getModel();
            break;
        case 5:
        		vehicleData = vehicle.getTrim();
            break;
        case 6:
        		vehicleData = vehicle.getBodyType();
            break;
        case 7:
        		vehicleData = vehicle.getPrice()-incentiveAPI.getAllDiscount(vehicle);
            break;
        default:
            throw new IllegalArgumentException("Invalid column index");
		}
		return vehicleData;

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
