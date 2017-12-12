package CenterSection;

import CenterSection.VehicleImageCell;
import com.neuSep17.dto.Vehicle;

import java.awt.*;
import javax.swing.ImageIcon;
        import javax.swing.JPanel;

import com.neuSep17.service.IncentiveServiceAPI_Test;
import com.neuSep17.service.InventoryServiceAPI_Test;

import java.io.IOException;

public class VehicleCardCell extends JPanel {
    JPanel cardPanel;
    VehicleDetail vehicleDetail;
    VehicleImageCell vehicleImageCell;

    public VehicleCardCell(Vehicle v, ImageIcon icon, float discount, InventoryServiceAPI_Test invsAPI, IncentiveServiceAPI_Test
                           incsAPI) throws IOException {
        super();
        setSize(800,600);
        cardPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        vehicleImageCell = new VehicleImageCell(v,icon,discount,cardLayout,cardPanel);
        vehicleDetail = new VehicleDetail(v, incsAPI,cardLayout,cardPanel);
        cardPanel.add("vehicleImageCell",vehicleImageCell);
        cardPanel.add("vehicleDetail",vehicleDetail);
        this.setLayout(new GridLayout());
        this.add(cardPanel);

    }



}
