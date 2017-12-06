package com.neuSep17.ui.consumer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

class LinkLabel extends JLabel{
   
    private String link;
    
    public LinkLabel(String toDisplay, String toLink) {
        super(toDisplay);
        this.link=toLink;
        this.setHorizontalAlignment(CENTER);
        this.addMouseListener(new MouseAdapter(){
            
            public void mouseEntered(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                LinkLabel.this.setForeground(Color.GRAY);
            }
            public void mouseExited(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor.getDefaultCursor());
                LinkLabel.this.setForeground(Color.BLACK);
            }
            
            public void mouseClicked(MouseEvent e) {
                System.out.println(link);
                BrowseInventoryFrame c=(BrowseInventoryFrame) SwingUtilities.getAncestorOfClass(BrowseInventoryFrame.class,LinkLabel.this);
                new VehicleDetailDialog(c, link, c.incsAPI,c.invsAPI);
            }
            
        });
    }

}
