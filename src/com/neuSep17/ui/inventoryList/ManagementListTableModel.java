import com.neuSep17.dto.Vehicle;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class ManagementListTableModel implements TableModel {
    private List<Vehicle> vehicles;

    public ManagementListTableModel(List<Vehicle> vehicles){
        this.vehicles = vehicles;
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
