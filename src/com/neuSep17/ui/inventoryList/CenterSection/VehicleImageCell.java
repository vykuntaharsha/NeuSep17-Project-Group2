package CenterSection;

import com.neuSep17.dto.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class VehicleImageCell extends JPanel {
    public VehicleImageCell(Vehicle v, ImageIcon icon, float discount,CardLayout cardLayout, JPanel cardPanel) throws IOException{
        super();
        setSize(800,600);
        setLayout(null);
        JLabel image = new JLabel(icon);
        image.setVerticalAlignment(SwingConstants.TOP);
        image.setHorizontalAlignment(SwingConstants.LEFT);
        image.setBounds(0,0,600,600);
        ImageIcon imageIcon = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/background.png");
        JLabel background = new JLabel(imageIcon);
        background.setVerticalAlignment(SwingConstants.TOP);
        background.setHorizontalAlignment(SwingConstants.LEFT);
        background.setBounds(0,0,300,80);

        StringBuilder title= new StringBuilder("<html>"+"<body style=\"padding:10px;\" >"+"<font color='white'>"+(v.getCategory().toString()+
                " "+v.getYear() + " " + v.getMake())+"<br></font>");
        if (discount > 0 ) title.append("<font color='white'> On Sale: "+Math.max(v.getPrice()-discount,0)+"!</font>");
        title.append("</body><html>");
        JLabel nameLabel = new JLabel(title.toString()+v.getId());
        nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.BOLD, 24));
        nameLabel.setVerticalAlignment(SwingConstants.TOP);
        nameLabel.setBounds(0,0,600,200);
        ImageIcon preImageEnter = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/right80%.png");
        ImageIcon preImageExit = new ImageIcon("/Users/boqunzhang/Downloads/NeuSep17-Project-Group2-master/src/com/neuSep17/ui/newInventoryList/material/right50%.png");
        JLabel next = new JLabel(preImageExit);
        next.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.next(cardPanel);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                next.setIcon(preImageEnter);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                next.setIcon(preImageExit);
            }
        });
        next.setBounds(250,0,50,300);
        add(nameLabel);
        add(background);
        add(next);
        add(image);
    }
}
