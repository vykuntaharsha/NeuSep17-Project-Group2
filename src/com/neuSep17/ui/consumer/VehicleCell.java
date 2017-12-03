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

public class VehicleCell extends JPanel {

    /**
     * Create the panel.
     */
    public VehicleCell(Vehicle v, ImageIcon icon) {
        super();
        this.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {181, 150, 50, 0};
        gridBagLayout.rowHeights = new int[]{50, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        
        JLabel image = new JLabel(icon);
        GridBagConstraints gbc_image = new GridBagConstraints();
        gbc_image.fill = GridBagConstraints.BOTH;
        gbc_image.gridheight = 2;
        gbc_image.insets = new Insets(0, 0, 0, 5);
        gbc_image.gridx = 0;
        gbc_image.gridy = 0;
        add(image, gbc_image);
        
        LinkLabel nameLabel = new LinkLabel(v.getYear() + " " + v.getMake() + " " + v.getModel(),v.getId());            
        nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.BOLD, 24));
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.anchor = GridBagConstraints.WEST;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 1;
        gbc_nameLabel.gridy = 0;
        add(nameLabel, gbc_nameLabel);
        
        this.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));        
                
        JLabel salePrice = new JLabel("put sales price here");
        GridBagConstraints gbc_salePrice = new GridBagConstraints();
        gbc_salePrice.anchor = GridBagConstraints.WEST;
        gbc_salePrice.insets = new Insets(0, 0, 5, 0);
        gbc_salePrice.gridx = 2;
        gbc_salePrice.gridy = 0;
        add(salePrice, gbc_salePrice);

        JPanel specPane = new JPanel();
        GridBagLayout gbl_specPane = new GridBagLayout();
        gbl_specPane.columnWidths = new int[] { 110, 110, 0 };
        gbl_specPane.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_specPane.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        gbl_specPane.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
        specPane.setLayout(gbl_specPane);

        JLabel priceLabel = new JLabel("price: " + v.getPrice().toString());
        GridBagConstraints gbc_priceLabel = new GridBagConstraints();
        gbc_priceLabel.anchor = GridBagConstraints.SOUTHWEST;
        gbc_priceLabel.insets = new Insets(0, 0, 5, 5);
        gbc_priceLabel.gridx = 0;
        gbc_priceLabel.gridy = 0;
        specPane.add(priceLabel, gbc_priceLabel);

        JLabel categoryLabel = new JLabel("category: " + v.getCategory().toString());
        GridBagConstraints gbc_categoryLabel = new GridBagConstraints();
        gbc_categoryLabel.anchor = GridBagConstraints.SOUTHWEST;
        gbc_categoryLabel.insets = new Insets(0, 0, 5, 0);
        gbc_categoryLabel.gridx = 1;
        gbc_categoryLabel.gridy = 0;
        specPane.add(categoryLabel, gbc_categoryLabel);

        JLabel trimLabel = new JLabel("trim: " + v.getTrim());
        GridBagConstraints gbc_trimLabel = new GridBagConstraints();
        gbc_trimLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_trimLabel.insets = new Insets(0, 0, 5, 5);
        gbc_trimLabel.gridx = 0;
        gbc_trimLabel.gridy = 1;
        specPane.add(trimLabel, gbc_trimLabel);

        GridBagConstraints gbc_specPane = new GridBagConstraints();
        gbc_specPane.anchor = GridBagConstraints.SOUTH;
        gbc_specPane.fill = GridBagConstraints.HORIZONTAL;
        gbc_specPane.insets = new Insets(0, 0, 0, 5);
        gbc_specPane.gridx = 1;
        gbc_specPane.gridy = 1;
        add(specPane, gbc_specPane);

        JLabel typeLabel = new JLabel("type: " + v.getBodyType());
        GridBagConstraints gbc_typeLabel = new GridBagConstraints();
        gbc_typeLabel.insets = new Insets(0, 0, 5, 0);
        gbc_typeLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_typeLabel.gridx = 1;
        gbc_typeLabel.gridy = 1;
        specPane.add(typeLabel, gbc_typeLabel);

        JLabel description = new JLabel("description");
        GridBagConstraints gbc_description = new GridBagConstraints();
        gbc_description.anchor = GridBagConstraints.WEST;
        gbc_description.gridx = 2;
        gbc_description.gridy = 1;
        add(description, gbc_description);

    }

}
