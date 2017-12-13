package com.neuSep17.ui.inventoryList.CenterSection;

import com.neuSep17.dto.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class VehicleImageCell extends JPanel {
    private JLabel next,edit,delete,add,background,carImage, nameLabel;
    private ImageIcon preImageEnter,preImageExit,addIconEnter, addIconExit, editIconEnter, editIconExit, deleteIconEnter, deleteIconExit, backgroundIcon,carIcon;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Vehicle vehicle;


    public VehicleImageCell(Vehicle v, ImageIcon icon, float discount, CardLayout cardLayout, JPanel cardPanel) throws IOException{
        super();
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.carIcon = icon;
        this.vehicle = v;


        creatComponent(icon,discount);
        addListener();
        addComponent();
    }
    private void creatComponent(ImageIcon icon, float discount){

        carImage = new JLabel(carIcon);
        carImage.setVerticalAlignment(SwingConstants.TOP);
        carImage.setHorizontalAlignment(SwingConstants.LEFT);
        carImage.setBounds(0,0,600,600);

        backgroundIcon = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/background.png");
        background = new JLabel(backgroundIcon);
        background.setVerticalAlignment(SwingConstants.TOP);
        background.setHorizontalAlignment(SwingConstants.LEFT);
        background.setBounds(0,0,300,80);

        StringBuilder title= new StringBuilder("<html>"+"<body style=\"padding:10px;\" >"+"<font color='white'>"+(vehicle.getCategory().toString()+
                " "+vehicle.getYear() + " " + vehicle.getMake())+"<br></font>");
        if (discount > 0 ) title.append("<font color='white'> On Sale: "+Math.max(vehicle.getPrice()-discount,0)+"!</font>");
        title.append("</body><html>");

        nameLabel = new JLabel(title.toString()+vehicle.getId());
        nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.BOLD, 24));
        nameLabel.setVerticalAlignment(SwingConstants.TOP);
        nameLabel.setBounds(0,0,600,200);

        preImageEnter = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/add80%.png");
        preImageExit = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/add50%.png");
        editIconEnter = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/edit80%.png");
        editIconExit = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/edit50%.png");
        addIconEnter = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/add80%.png");
        addIconExit = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/add50%.png");
        deleteIconEnter = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/delete80%.png");
        deleteIconExit = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/delete50%.png");

        next = new JLabel(preImageExit);
        next.setBounds(250,0,50,300);

        add = new JLabel(addIconExit);
        add.setBounds(75,0,50,50);
        edit = new JLabel(editIconExit);
        edit.setBounds(125,0,50,50);
        delete = new JLabel(deleteIconExit);
        delete.setBounds(175,0,50,50);
    }

    private void addListener(){
        IconActionListener ial = new IconActionListener();
        next.addMouseListener(ial);
        edit.addMouseListener(ial);
        add.addMouseListener(ial);
        delete.addMouseListener(ial);
    }

    private void addComponent(){
        setBackground(Color.RED);
        setSize(800,600);
        setLayout(null);
        add(nameLabel);
        add(background);
        add(next);
        add(add);
        add(edit);
        add(delete);
        //add(carImage);
    }

    class IconActionListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() == next) cardLayout.next(cardPanel);
            else if(e.getSource() == add);
            else if(e.getSource() == edit);
            else if(e.getSource() == delete);
            else return;
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource() == next) next.setIcon(preImageEnter);
            else if(e.getSource() == add) add.setIcon(addIconEnter);
            else if(e.getSource() == edit) edit.setIcon(editIconEnter);
            else if(e.getSource() == delete) delete.setIcon(deleteIconEnter);
            else return;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource() == next) next.setIcon(preImageExit);
            else if(e.getSource() == add) add.setIcon(addIconExit);
            else if(e.getSource() == edit) edit.setIcon(editIconExit);
            else if(e.getSource() == delete) delete.setIcon(deleteIconExit);
            else return;
        }
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}



