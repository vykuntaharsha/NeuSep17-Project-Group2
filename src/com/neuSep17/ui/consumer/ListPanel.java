package com.neuSep17.ui.consumer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.neuSep17.dto.Vehicle;

public class ListPanel extends JPanel{
    
    private BrowseInventoryFrame parent;
    private JScrollPane listScrollPane;
    private JPanel carList;
    
    ListPanel(BrowseInventoryFrame bif){
        this.parent=bif;
        parent.setPage(0);        
        
        this.setSize(new Dimension(this.getWidth() - 300, this.getHeight() - 200)); // TODO
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout(0, 0));
        createComponents();
        addComponents();
    }
    
    private void createComponents() {        
        carList = new JPanel();
        carList.setSize(new Dimension(this.getWidth() - 300, this.getHeight() - 300)); // TODO
        listScrollPane = new JScrollPane();

    }

    private void addComponents() {
        carList.setBorder(BorderFactory.createLineBorder(Color.black));
        carList.setLayout(new BoxLayout(carList, BoxLayout.Y_AXIS));        
        listScrollPane.setViewportView(carList);        
        this.add(listScrollPane,BorderLayout.CENTER);
        this.add(new PagePane(parent, parent.getMaxPage()), BorderLayout.SOUTH);
        
        displaytoList(parent.getVehiclestoDisplay());        
    }

    

    void displaytoList(ArrayList<Vehicle> toDisplay) {
        carList.removeAll();
        carList.revalidate();
        carList.repaint();
        listScrollPane.getVerticalScrollBar().setValue(0);
        Vehicle v;
        int n,perpage=parent.getPerPage();
        for (int i = 0; i < perpage; ++i)
        {
            n = parent.getPage() * perpage + i;
            if (n >= toDisplay.size())
                break;
            v = toDisplay.get(n);
            carList.add(new VehicleCell(v, parent.getIcon(v),parent.incsAPI.getAllDiscount(v)));
        }

        this.remove(1);
        this.revalidate();
        this.add(new PagePane(parent, toDisplay.size() / perpage), BorderLayout.SOUTH);
        
    }
    
    void addListeners()
    {
        // updateFinishedListener updateFinished=new updateFinishedListener();
    }


}
