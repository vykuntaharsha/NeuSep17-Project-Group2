package com.neuSep17.ui.consumer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.neuSep17.dto.Vehicle;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class VehicleCell extends JPanel
{

    public VehicleCell(Vehicle v, ImageIcon icon, float discount)
    {
        super();
        this.setBorder(new MatteBorder(2, 0, 2, 0, Color.BLACK));
        this.setBackground(Color.WHITE);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 181, 150, 0 };
        gridBagLayout.rowHeights = new int[] { 28, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel image = new JLabel(icon);
        GridBagConstraints gbc_image = new GridBagConstraints();
        gbc_image.anchor = GridBagConstraints.CENTER;
        gbc_image.fill = GridBagConstraints.HORIZONTAL;
        gbc_image.gridheight = 2;
        gbc_image.insets = new Insets(0, 0, 0, 5);
        gbc_image.gridx = 0;
        gbc_image.gridy = 0;
        add(image, gbc_image);

        String title = v.getYear() + " " + v.getMake() + " " + v.getModel();
        LinkLabel nameLabel = new LinkLabel(title, v.getId());
        nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.BOLD, 24));
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameLabel.anchor = GridBagConstraints.SOUTH;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 0);
        gbc_nameLabel.gridx = 1;
        gbc_nameLabel.gridy = 0;
        nameLabel.setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
        add(nameLabel, gbc_nameLabel);

        JPanel specPane = new JPanel();
        GridBagLayout gbl_specPane = new GridBagLayout();
        gbl_specPane.columnWidths = new int[] { 110, 110, 0 };
        gbl_specPane.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_specPane.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        gbl_specPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        GridBagConstraints gbc_specPane = new GridBagConstraints();
        gbc_specPane.gridx = 1;
        gbc_specPane.gridy = 1;
        add(specPane, gbc_specPane);
        specPane.setLayout(gbl_specPane);

        JLabel typeLabel = new JLabel("type: " + v.getBodyType());
        GridBagConstraints gbc_typeLabel = new GridBagConstraints();
        gbc_typeLabel.insets = new Insets(0, 0, 5, 5);
        gbc_typeLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_typeLabel.gridx = 0;
        gbc_typeLabel.gridy = 0;
        specPane.add(typeLabel, gbc_typeLabel);

        String price = "price: " + v.getPrice().toString();
        if (discount > 0)
        {
            float pricenow = v.getPrice() - discount;
            if (pricenow < 0)
                price = "<html><STRIKE>" + price
                        + "</STRIKE><br /> <font color='red' size='+1'> On Sale:<br> Contact to get quote!</font><html>";
            else
                price = "<html><STRIKE>" + price + "</STRIKE><br /> <font color='red' size='+1'> On Sale:<br> "
                        + (v.getPrice() - discount) + "!</font><html>";
        }
        JLabel priceLabel = new JLabel(price);
        GridBagConstraints gbc_priceLabel = new GridBagConstraints();
        gbc_priceLabel.gridheight = 3;
        gbc_priceLabel.anchor = GridBagConstraints.NORTHEAST;
        gbc_priceLabel.insets = new Insets(0, 0, 5, 0);
        gbc_priceLabel.gridx = 1;
        gbc_priceLabel.gridy = 0;
        specPane.add(priceLabel, gbc_priceLabel);

        JLabel trimLabel = new JLabel("trim: " + v.getTrim());
        GridBagConstraints gbc_trimLabel = new GridBagConstraints();
        gbc_trimLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_trimLabel.insets = new Insets(0, 0, 5, 5);
        gbc_trimLabel.gridx = 0;
        gbc_trimLabel.gridy = 1;
        specPane.add(trimLabel, gbc_trimLabel);

        JLabel categoryLabel = new JLabel("category: " + v.getCategory().toString());
        GridBagConstraints gbc_categoryLabel = new GridBagConstraints();
        gbc_categoryLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_categoryLabel.insets = new Insets(0, 0, 5, 5);
        gbc_categoryLabel.gridx = 0;
        gbc_categoryLabel.gridy = 2;
        specPane.add(categoryLabel, gbc_categoryLabel);

        specPane.setBackground(Color.WHITE);

    }

}
