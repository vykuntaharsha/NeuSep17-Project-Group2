package com.neuSep17.ui.consumer;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.neuSep17.dto.Vehicle;

class vehicleCell extends JPanel{
    
    vehicleCell(Vehicle v, ImageIcon icon){
        super();
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        
        JLabel image = new JLabel(icon);            
        
        
        LinkLabel nameLabel = new LinkLabel(v.getYear() + " " + v.getMake() + " " + v.getModel(),v.getId());            
        nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.BOLD, 24));
        
        
        JLabel priceLabel = new JLabel("price: " + v.getPrice().toString());
        JLabel categoryLabel = new JLabel("category: " + v.getCategory().toString());
        JLabel trimLabel = new JLabel("trim: " + v.getTrim());
        JLabel typeLabel = new JLabel("type: " + v.getBodyType());
        
        
        
        JPanel specPane = new JPanel();
        specPane.setLayout(new BoxLayout(specPane, BoxLayout.Y_AXIS));

        specPane.add(nameLabel);
        specPane.add(priceLabel);
        specPane.add(categoryLabel);
        specPane.add(trimLabel);
        specPane.add(typeLabel);
        
        this.add(image);
        this.add(specPane);
        this.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));
        
    }
}
