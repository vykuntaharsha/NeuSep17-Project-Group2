package com.neuSep17.ui.inventoryList.adeButton;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.neuSep17.dto.Vehicle;

@SuppressWarnings("serial")
public class AddFramecontrol extends JFrame {

	private JButton Yes, No;
	Vehicle addVehicle;
	public boolean addFlag = false;
	public String[] tempVehicle = new String[10];
	private JLabel id, webId, year, make, model, trim, category, bodyType, price, photoUrl;
	private JTextField idField, webIdField, yearField, makeField, modelField, trimField, priceField, photoUrlField;
	private JComboBox<String> bodyTypeField;
	private JComboBox<String> categoryField;

	private JPanel contentPane;

	private final String[] CATEGORIES = new String[] { "USED", "NEW", "CERTIFIED" };
	private final String[] BODY_TYPE = new String[] { "Coupe", "Sedan", "SUV", "Van", "Truck", "Sport", "Convertible",
			"Crossover", "Wagon", "Hybrid", "Luxury", "Diesel Engine" };

	public AddFramecontrol() {
		create();
		addComponents();
		makeListeners();
		display();
		setTitle("Add Vehicle");
	}

	public Vehicle getAddVehicle() {
		return addVehicle;
	}

	public void create() {
		No = new JButton("× Cancel");
		No.setForeground(new Color(77, 77, 77));
		No.setBackground(new Color(228, 228, 228));
		No.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		No.setBounds(46, 576, 117, 29);
		No.setOpaque(true);
		No.setBorderPainted(false);

		Yes = new JButton("+ Save");
		Yes.setForeground(Color.white);
		Yes.setBackground(new Color(116, 147, 105));
		Yes.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		Yes.setBounds(272, 576, 117, 29);
		Yes.setOpaque(true);
		Yes.setBorderPainted(false);

		year = new JLabel("Year");
		make = new JLabel("Make");
		model = new JLabel("Model");
		trim = new JLabel("Trim");
		category = new JLabel("Category");
		bodyType = new JLabel("BodyType");
		price = new JLabel("Price");
		photoUrl = new JLabel("PhotoUrl");
		
		id = new JLabel("ID");
		webId = new JLabel("Web ID");

		idField = new JTextField();
		webIdField = new JTextField();
		yearField = new JTextField();
		makeField = new JTextField();
		modelField = new JTextField();
		trimField = new JTextField();
		priceField = new JTextField();
		photoUrlField = new JTextField("http://");
	}

	public void addComponents() {
		setBounds(100, 100, 448, 670);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(242, 242, 242));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(242, 242, 242));
		panel.setBounds(6, 6, 436, 642);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lbAddVehicle = new JLabel("ADD VEHICLE");
		lbAddVehicle.setForeground(new Color(77, 77, 77));
		lbAddVehicle.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
		lbAddVehicle.setBounds(46, 19, 175, 38);
		panel.add(lbAddVehicle);

		id.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		id.setForeground(Color.DARK_GRAY);
		id.setBounds(46, 76, 102, 16);
		panel.add(id);

		webId.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		webId.setForeground(Color.DARK_GRAY);
		webId.setBounds(46, 126, 102, 16);
		panel.add(webId);

		year.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		year.setForeground(Color.DARK_GRAY);
		year.setBounds(46, 176, 102, 16);
		panel.add(year);

		make.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		make.setForeground(Color.DARK_GRAY);
		make.setBounds(46, 226, 102, 16);
		panel.add(make);

		trim.setForeground(Color.DARK_GRAY);
		trim.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		trim.setBounds(46, 276, 102, 16);
		panel.add(trim);

		model.setForeground(Color.DARK_GRAY);
		model.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		model.setBounds(46, 326, 102, 16);
		panel.add(model);

		category.setForeground(Color.DARK_GRAY);
		category.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		category.setBounds(46, 376, 102, 19);
		panel.add(category);

		bodyType.setForeground(Color.DARK_GRAY);
		bodyType.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		bodyType.setBounds(46, 426, 102, 16);
		panel.add(bodyType);

		price.setForeground(Color.DARK_GRAY);
		price.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		price.setBounds(46, 476, 102, 16);
		panel.add(price);

		photoUrl.setForeground(Color.DARK_GRAY);
		photoUrl.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		photoUrl.setBounds(46, 526, 102, 16);
		panel.add(photoUrl);

		panel.add(No);
		panel.add(Yes);

		idField.setBounds(160, 72, 229, 26);
		panel.add(idField);
		idField.setColumns(10);

		webIdField.setBounds(160, 122, 229, 26);
		panel.add(webIdField);
		webIdField.setColumns(10);

		photoUrlField.setBounds(160, 522, 229, 26);
		panel.add(photoUrlField);
		photoUrlField.setColumns(10);

		priceField.setColumns(10);
		priceField.setBounds(160, 472, 229, 26);
		panel.add(priceField);

		modelField.setColumns(10);
		modelField.setBounds(160, 272, 229, 26);
		panel.add(modelField);

		trimField.setColumns(10);
		trimField.setBounds(160, 322, 229, 26);
		panel.add(trimField);

		makeField.setColumns(10);
		makeField.setBounds(160, 222, 229, 26);
		panel.add(makeField);

		yearField.setColumns(10);
		yearField.setBounds(160, 172, 229, 26);
		panel.add(yearField);

		categoryField = new JComboBox<>();
		for (String s : this.CATEGORIES) {
			categoryField.addItem(s);
		}
		categoryField.setBounds(160, 372, 229, 27);
		panel.add(categoryField);

		bodyTypeField = new JComboBox<>();
		for (String s : this.BODY_TYPE) {
			bodyTypeField.addItem(s);
		}
		bodyTypeField.setBounds(160, 422, 229, 27);
		panel.add(bodyTypeField);
	}

	public void makeListeners() {
		ManageActionListener mal = new ManageActionListener();
		Yes.addActionListener(mal);
		No.addActionListener(mal);
	}

	class ManageActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == Yes) {
				String[] tempVehicle = new String[10];
				tempVehicle[0] = idField.getText().trim();
				tempVehicle[1] = webIdField.getText().trim();
				tempVehicle[2] = (String) categoryField.getSelectedItem() == null ? ""
						: (String) categoryField.getSelectedItem();
				tempVehicle[3] = yearField.getText().trim();
				tempVehicle[4] = makeField.getText().trim();
				tempVehicle[5] = modelField.getText().trim();
				tempVehicle[6] = trimField.getText().trim();
				tempVehicle[7] = (String) bodyTypeField.getSelectedItem() == null ? ""
						: (String) bodyTypeField.getSelectedItem();
				tempVehicle[8] = priceField.getText().trim();
				tempVehicle[9] = photoUrlField.getText().trim();
				addVehicle = new Vehicle(tempVehicle);
				addFlag = true;
				setVisible(false);
			}
			if (e.getSource() == No) {
				setVisible(false);
			}

		}

	}

	public void display() {
		setSize(420, 650);
		setLocation(500, 200);
		setVisible(true);
	}
}
