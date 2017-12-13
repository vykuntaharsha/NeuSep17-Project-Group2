package com.neuSep17.ui.inventoryList.adeButton;


import com.neuSep17.dto.Vehicle;

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

@SuppressWarnings("serial")
public class EditFramecontrol extends JFrame {
	private JButton Yes, No;
	public boolean editFlag = false;
	public String[] tempVehicle = new String[10];
	private JLabel year, make, model, trim, category, bodyType, price, photoUrl;
	private JTextField yearField, makeField, modelField, trimField, priceField, photoUrlField;
	private JComboBox<String> bodyTypeField;
	private JComboBox<String> categoryField;
	private Vehicle vehicle;

	private JPanel contentPane;

	private final String[] CATEGORIES = new String[] { "", "USED", "NEW", "CERTIFIED" };
	private final String[] BODY_TYPE = new String[] { "", "Coupe", "Sedan", "SUV", "Van", "Truck", "Sport",
			"Convertible", "Crossover", "Wagon", "Hybrid", "Luxury", "Diesel Engine" };

	public EditFramecontrol(Vehicle vehicle) {
		this.vehicle = vehicle;
		create();
		addComponents();
		makeListeners();
		display();
		setTitle("Edit Vehicle");
	}

	public void create() {
		No = new JButton("× Cancel");
		No.setForeground(new Color(77, 77, 77));
		No.setBackground(new Color(228, 228, 228));
		No.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		No.setBounds(35, 484, 117, 29);
		No.setOpaque(true);
		No.setBorderPainted(false);

		Yes = new JButton("+ Save");
		Yes.setForeground(Color.white);
		Yes.setBackground(new Color(116, 147, 105));
		Yes.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		Yes.setBounds(261, 484, 117, 29);
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
		yearField = new JTextField(vehicle.getYear());
		makeField = new JTextField(vehicle.getMake());
		modelField = new JTextField(vehicle.getModel());
		trimField = new JTextField(vehicle.getTrim());
		priceField = new JTextField(vehicle.getPrice().toString());
		photoUrlField = new JTextField(vehicle.getPhotoUrl().toString());

		bodyTypeField = new JComboBox<>();
		for (String s : this.BODY_TYPE) {
			bodyTypeField.addItem(s.toLowerCase());
		}

		categoryField = new JComboBox<>();
		for (String s : this.CATEGORIES) {
			categoryField.addItem(s.toLowerCase());
		}
	}

	public void addComponents() {
		/*
		 * initComponents(); addComponents(); makeListeners(); display();
		 */
		setBounds(100, 100, 416, 597);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(242, 242, 242));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(242, 242, 242));
		panel.setBounds(6, 6, 404, 552);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lbAddVehicle = new JLabel("EDIT VEHICLE");
		lbAddVehicle.setForeground(new Color(77, 77, 77));
		lbAddVehicle.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
		lbAddVehicle.setBounds(35, 26, 175, 38);
		panel.add(lbAddVehicle);

		year.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		year.setForeground(Color.DARK_GRAY);
		year.setBounds(35, 80, 102, 16);
		panel.add(year);

		make.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		make.setForeground(Color.DARK_GRAY);
		make.setBounds(35, 130, 102, 16);
		panel.add(make);

		trim.setForeground(Color.DARK_GRAY);
		trim.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		trim.setBounds(35, 180, 102, 16);
		panel.add(trim);

		model.setForeground(Color.DARK_GRAY);
		model.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		model.setBounds(35, 230, 102, 16);
		panel.add(model);

		category.setForeground(Color.DARK_GRAY);
		category.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		category.setBounds(35, 280, 102, 16);
		panel.add(category);

		bodyType.setForeground(Color.DARK_GRAY);
		bodyType.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		bodyType.setBounds(35, 330, 102, 16);
		panel.add(bodyType);

		price.setForeground(Color.DARK_GRAY);
		price.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		price.setBounds(35, 380, 102, 16);
		panel.add(price);

		photoUrl.setForeground(Color.DARK_GRAY);
		photoUrl.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		photoUrl.setBounds(35, 430, 102, 16);
		panel.add(photoUrl);

		panel.add(No);
		panel.add(Yes);

		photoUrlField.setBounds(149, 426, 229, 26);
		panel.add(photoUrlField);
		photoUrlField.setColumns(10);

		priceField.setColumns(10);
		priceField.setBounds(149, 376, 229, 26);
		panel.add(priceField);

		modelField.setColumns(10);
		modelField.setBounds(149, 226, 229, 26);
		panel.add(modelField);

		trimField.setColumns(10);
		trimField.setBounds(149, 176, 229, 26);
		panel.add(trimField);

		makeField.setColumns(10);
		makeField.setBounds(149, 126, 229, 26);
		panel.add(makeField);

		yearField.setColumns(10);
		yearField.setBounds(149, 76, 229, 26);
		panel.add(yearField);

		categoryField.setBounds(149, 277, 229, 27);
		panel.add(categoryField);

		bodyTypeField.setBounds(149, 327, 229, 27);
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
				tempVehicle[0] = (String) categoryField.getSelectedItem() == null ? ""
						: (String) categoryField.getSelectedItem();
				// tempVehicle[0] = categoryField.getText().trim();
				tempVehicle[1] = yearField.getText().trim();
				tempVehicle[2] = makeField.getText().trim();
				tempVehicle[3] = modelField.getText().trim();
				tempVehicle[4] = trimField.getText().trim();
				tempVehicle[5] = (String) bodyTypeField.getSelectedItem() == null ? ""
						: (String) bodyTypeField.getSelectedItem();
				tempVehicle[6] = priceField.getText().trim();
				tempVehicle[7] = photoUrlField.getText().trim();
				// System.out.println(tempVehicle[0]);
				// System.out.println(tempVehicle[1]);
				// System.out.println(tempVehicle[2]);
				// System.out.println(tempVehicle[3]);
				// System.out.println(tempVehicle[4]);
				// System.out.println(tempVehicle[5]);
				// System.out.println(tempVehicle[6]);
				// System.out.println(tempVehicle[7]);
				// System.out.println(tempVehicle[8]);
				// System.out.println(tempVehicle[9]);
				editFlag = true;
				setVisible(false);
			}
			if (e.getSource() == No) {
				setVisible(false);
			}

		}
	}

	public void display() {
		setSize(420, 550);
		setLocation(500, 200);
		setVisible(true);
		// pack();
	}
//	public static void main(String[] args){
//		new EditFramecontrol();
//		new AddFramecontrol();
//	}
}
