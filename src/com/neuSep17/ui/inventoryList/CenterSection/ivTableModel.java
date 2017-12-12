package CenterSection;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.IncentiveServiceAPI_Test;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class ivTableModel implements TableModel {
    private List<Vehicle> vehicles;
    private IncentiveServiceAPI_Test incentiveServiceAPI_test;
    private Boolean[] selects;

    public ivTableModel(List<Vehicle> vehicles, IncentiveServiceAPI_Test incentiveServiceAPI_test){
        this.vehicles = vehicles;
        this.incentiveServiceAPI_test = incentiveServiceAPI_test;
        this.selects = new Boolean[vehicles.size()];
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
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
        if(columnIndex==0) return "SelectAll";
        if(columnIndex==1) return "Vin";
        if(columnIndex==2) return "Category";
        if(columnIndex==3) return "Year";
        if(columnIndex==4) return "Make";
        if(columnIndex==5) return "Model";
        if(columnIndex==6) return "Trim";
        if(columnIndex==7) return "Type";
        if(columnIndex==8) return "Price";
        if(columnIndex==9) return "Discount";
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex==0) return Boolean.class;
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex==0) return true;
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vehicle vehicle = vehicles.get(rowIndex);
        if(columnIndex == 0){
            return selects[rowIndex];
        }
        if (columnIndex == 1) {
            return vehicle.getId();
        }
        if(columnIndex == 2){
            return vehicle.getCategory();
        }
        if(columnIndex == 3){
            return vehicle.getYear();
        }
        if(columnIndex == 4){
            return vehicle.getMake();
        }
        if(columnIndex == 5){
            return vehicle.getModel();
        }
        if(columnIndex == 6){
            return vehicle.getTrim();
        }
        if(columnIndex == 7){
            return vehicle.getBodyType();
        }
        if(columnIndex == 8) {
            return Math.max(vehicle.getPrice() - incentiveServiceAPI_test.getAllDiscount(vehicle), 0);
        }
        return incentiveServiceAPI_test.getAllDiscount(vehicle);

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        System.out.println("!!!!!!!!!!!!"+rowIndex+"--"+columnIndex);
        if(columnIndex==0){
            selects[rowIndex] = (Boolean)aValue;
            return;
        }
        return;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

}
