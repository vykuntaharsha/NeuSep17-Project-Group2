package com.neuSep17.ui.consumer;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.neuSep17.dto.Vehicle;

public class ImageSlideShow extends JPanel implements ActionListener {
	private List<URL> urls = new ArrayList<>();
	private String[] buttonNames = { "first", "pre", "next", "last" };
	private int currentImage = 0;
	private int width = 600, height = 550;
	private JPanel buttonPanel = new JPanel();
	private JPanel titlePanel = new JPanel();
	private JPanel imagePanel = new JPanel();

	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints gbs = new GridBagConstraints();

	Map<JButton, String> buttons = new LinkedHashMap<>();
	JLabel imageLabel;
	JLabel title;
	Map<Integer, ImageIcon> images = new HashMap<>();

	public ImageSlideShow(Vehicle vehicle, String[] imageUrls) {

		createComponents(vehicle, imageUrls);
		addComponents();
		this.setVisible(true);
	}

	private void addComponents() {
		this.setLayout(gbl);
		title.setHorizontalAlignment(JLabel.LEFT);
		this.add(title);
//		titlePanel.add(title);
		imagePanel.add(imageLabel);
		this.add(titlePanel);
		this.add(imagePanel);
		for (JButton jButton : buttons.keySet()) {
			buttonPanel.add(jButton);
		}
		this.add(buttonPanel);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(0, 30, 10, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbl.setConstraints(title, gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(10, 0, 0, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 1;
		gbl.setConstraints(imagePanel, gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(0, 0, 0, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 2;
		gbl.setConstraints(buttonPanel, gbs);
	}

	private void createComponents(Vehicle vehicle, String[] imageUrls) {
		title = new JLabel();
		imageLabel = new JLabel();

		for (String buttonName : buttonNames) {
			JButton button = new JButton(buttonName);
			button.addActionListener(this);
			buttons.put(button, buttonName);
		}

		for (String spec : imageUrls) {
//			if (isImage(spec)) {
//
//			} else {
//				spec = null;
//			}
			if(!isImage(spec)){
				spec = null;
			}
			try {
				urls.add(new URL(spec));
			} catch (MalformedURLException e) {
				urls.add(null);
			}
		}

		loadImage();
		title.setText(vehicle.getCategory() + " " + vehicle.getYear() + " " + vehicle.getMake() + " "
				+ vehicle.getModel() + " " + vehicle.getTrim());
		title.setFont(new Font("Menu.font", Font.PLAIN, 20));
	}

	private void loadImage() {
		URL url = urls.get(currentImage);
		ImageIcon icon;
		BufferedImage img = null;
		if (url != null) {
//			icon = new ImageIcon(urls.get(currentImage));
			icon = new ImageIcon(url);
		} else {

			try {
				img = ImageIO.read(new File("src/com/neuSep17/ui/consumer/imagenotfound.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			icon = new ImageIcon(img);

		}
		// ImageIcon icon=new ImageIcon(urls.get(currentImage));

		icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));

		if (!images.containsKey(currentImage)) {
			images.put(currentImage, icon);
		}
		imageLabel.setIcon(images.get(currentImage));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = buttons.get(e.getSource());
		if (name.equals("next")) {
			this.currentImage = (this.currentImage + 1) % this.urls.size();

		} else if (name.equals("pre")) {
			this.currentImage = (this.currentImage - 1 + this.urls.size()) % this.urls.size();

		} else if (name.equals("first")) {
			this.currentImage = 0;

		} else if (name.equals("last")) {
			this.currentImage = this.urls.size() - 1;

		}
		loadImage();
	}

	private boolean isImage(String image_path) {
//		Image image = new ImageIcon(image_path).getImage();
//		if (image== null) {
//			return false;
//		} else {
//			return true;
//		}
//	}
		Image image=null;
		try {
			image = ImageIO.read(new URL(image_path));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(image != null){
		    return true;
		}else{
		    return false;
		}
	}
}
