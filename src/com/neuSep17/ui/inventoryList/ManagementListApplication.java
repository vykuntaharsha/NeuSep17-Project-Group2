
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.InventoryServiceAPI_Test;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class ManagementListApplication extends JFrame {
	private Map<String, List<String>> currentComboBoxItemsMap;
	private List<Vehicle> currentVehicleList, totalVehicleList;
	private PageController pageController;
	private JTable jTable;
	private ManagementListTableModel tableModel;
	private JButton edit, delete, add, exit, firstPage, next, pre, lastPage, shift_left, shift_right, enter,
			searchButton;
	// Ryan
	private JLabel searchLabel, vinLabel, categoryLabel, yearLabel, makeLabel, modelLabel, priceLabel, totalPageLabel,
			typeLabel, currentPageLabel;
	private JTextField searchField, vinField, categoryField, yearField, makeField, modelField, PriceField,
			jumpPageField, typeField;
	private JComboBox filterComboBox, sortComboBox;

	private final String[] FILTERSTRINGS = new String[] { "Sort", "Year", "Make", "Price" };
	private final String[] SORTSTRINGS = new String[] { "low to high", "high to low" };

	public ManagementListApplication(InventoryServiceAPI_Test inventoryServiceAPI_test) {
		this.totalVehicleList = inventoryServiceAPI_test.getVehicles();
		this.currentVehicleList = totalVehicleList;
		this.currentComboBoxItemsMap = InventoryServiceAPI_Test.getComboBoxItemsMap(currentVehicleList);
		pageController = new PageController(currentVehicleList);
		create();
		addComponents();
		makeListeners();
		display();
	}

	public void create() {
		tableModel = new ManagementListTableModel(pageController.select());
		jTable = new JTable(tableModel);

		add = new JButton("Add");
		delete = new JButton("Delete");
		edit = new JButton("Edit");
		exit = new JButton("Exit");
		currentPageLabel = new JLabel("Current Page: ");
		jumpPageField = new JFormattedTextField("" + pageController.getCurentPageIndex());
		jumpPageField.setColumns(5);
		enter = new JButton("Enter");
		firstPage = new JButton("FirstPage");
		shift_left = new JButton("<<");
		pre = new JButton("Pre");
		next = new JButton("Next");
		shift_right = new JButton(">>");
		lastPage = new JButton(pageController.getPageCount() + "");

		// Ryan
		// searchLabel = new JLabel("Search Vehicle: ");
		// vinLabel = new JLabel("VIN: ");
		categoryLabel = new JLabel("Category: ");
		yearLabel = new JLabel("Year: ");
		makeLabel = new JLabel("Make");
		typeLabel = new JLabel("Type");
		// modelLabel = new JLabel("Model");
		priceLabel = new JLabel("Price");

		categoryField = new JFormattedTextField("");
		categoryField.setColumns(5);
		setupAutoCompleteAndFilter(categoryField, new ArrayList<>(currentComboBoxItemsMap.get("category")));
		yearField = new JFormattedTextField("");
		yearField.setColumns(5);
		setupAutoCompleteAndFilter(yearField, new ArrayList<>(currentComboBoxItemsMap.get("year")));
		makeField = new JFormattedTextField("");
		makeField.setColumns(5);
		setupAutoCompleteAndFilter(makeField, new ArrayList<>(currentComboBoxItemsMap.get("make")));
		typeField = new JFormattedTextField("");
		typeField.setColumns(5);
		setupAutoCompleteAndFilter(typeField, new ArrayList<>(currentComboBoxItemsMap.get("type")));
		// modelField = new JFormattedTextField("");
		// modelField.setColumns(5);
		// setupAutoCompleteAndFilter(modelField, new
		// ArrayList<>(currentComboBoxItemsMap.get("model")));
		PriceField = new JFormattedTextField("");
		PriceField.setColumns(5);
		setupAutoCompleteAndFilter(PriceField, new ArrayList<>(currentComboBoxItemsMap.get("price")));

		searchButton = new JButton("Search");
		lastPage = new JButton("LastPage");
		totalPageLabel = new JLabel("Total Page: " + pageController.getPageCount());

		filterComboBox = new JComboBox(FILTERSTRINGS);
		sortComboBox = new JComboBox(SORTSTRINGS);
	}

	public void addComponents() {
		Container con = getContentPane();
		BorderLayout bl = new BorderLayout();
		con.setLayout(bl);
		// Add Table to the north..
		con.add(makeTablePanel(), "Center");
		// Add the screen to enter information in center - create a panel for
		// the input..
		con.add(makePageButtonPanel(), "North");
		// Add the buttons to south - create a panel from the buttons..
		con.add(makeActionButtonPanel(), "South");
		con.add(makeFilter(), "West");
	}

	private JPanel makePageButtonPanel() {
		JPanel panel = new JPanel();
		// Ryan
		// panel.add(searchLabel);
		// panel.add(searchField);
		// panel.add(searchButton);
		panel.add(currentPageLabel);
		panel.add(jumpPageField);
		panel.add(enter);
		panel.add(firstPage);
		panel.add(shift_left);
		panel.add(pre);
		panel.add(next);
		panel.add(shift_right);
		panel.add(lastPage);
		panel.add(totalPageLabel);
		panel.setBorder(new TitledBorder("Pages section.."));
		return panel;
	}

	private JPanel makeActionButtonPanel() {
		JPanel panel = new JPanel();
		JPanel abovePanel = new JPanel();
		JPanel belowPanel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		// Ryan
		// panel.add(vinLabel);
		// panel.add(vinField);
		abovePanel.add(categoryLabel);
		abovePanel.add(categoryField);
		abovePanel.add(yearLabel);
		abovePanel.add(yearField);
		abovePanel.add(makeLabel);
		abovePanel.add(makeField);
		abovePanel.add(typeLabel);
		abovePanel.add(typeField);
		// panel.add(modelLabel);
		// panel.add(modelField);
		abovePanel.add(priceLabel);
		abovePanel.add(PriceField);
		abovePanel.setBorder(new TitledBorder("Search section.."));
		// panel.add(searchButton);

		belowPanel.add(edit);
		belowPanel.add(add);
		belowPanel.add(delete);
		belowPanel.add(exit);
		belowPanel.setBorder(new TitledBorder("Actions section.."));
		panel.add(abovePanel);
		panel.add(belowPanel);
		return panel;
	}

	private JScrollPane makeTablePanel() {
		TableColumn tableColumn = jTable.getColumnModel().getColumn(8);
		LinkCellRenderer renderer = new LinkCellRenderer();
		tableColumn.setCellRenderer(renderer);
		jTable.addMouseListener(renderer);
		jTable.addMouseMotionListener(renderer);
		JScrollPane sp = new JScrollPane(jTable);
		jTable.setLocale(Locale.getDefault());
		jTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		jTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		jTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		jTable.getColumnModel().getColumn(3).setPreferredWidth(160);
		jTable.getColumnModel().getColumn(4).setPreferredWidth(180);
		jTable.getColumnModel().getColumn(5).setPreferredWidth(180);
		jTable.getColumnModel().getColumn(6).setPreferredWidth(80);
		jTable.getColumnModel().getColumn(7).setPreferredWidth(100);
		jTable.getColumnModel().getColumn(8).setPreferredWidth(260);
		sp.setSize(900, 500);
		sp.setBorder(new TitledBorder("Vehicles information.."));
		// sp.getViewport().setBackground(Color.white);
		return sp;
	}

	private JPanel makeFilter() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(filterComboBox);
		panel.add(sortComboBox);
		panel.setBorder(new TitledBorder("Sort section.."));
		return panel;
	}

	public void makeListeners() {
		ManageActionListener mal = new ManageActionListener();
		enter.addActionListener(mal);
		firstPage.addActionListener(mal);
		shift_left.addActionListener(mal);
		pre.addActionListener(mal);
		next.addActionListener(mal);
		shift_right.addActionListener(mal);
		lastPage.addActionListener(mal);

		// searchButton.addActionListener(mal);
		edit.addActionListener(mal);
		add.addActionListener(mal);
		delete.addActionListener(mal);
		exit.addActionListener(mal);

		filterComboBox.addActionListener(mal);
		sortComboBox.addActionListener(mal);
	}

	class ManageActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == filterComboBox || e.getSource() == sortComboBox) {
				String filter = FILTERSTRINGS[filterComboBox.getSelectedIndex()];
				boolean sort = sortComboBox.getSelectedIndex() == 0;
				currentVehicleList = InventoryServiceAPI_Test.sortVehicles(currentVehicleList, filter.toLowerCase(),
						sort);
				pageController = new PageController(currentVehicleList);
				tableModel.setVehicles(pageController.select());
				totalPageLabel.setText(pageController.getPageCount() + "");
			} else if (e.getSource() == edit) {
				// TODO jump to a new edit window, and find and replace the
				// vehicle i n vmb;
				// edit();
				// vmb.saveVehicle();
			} else if (e.getSource() == add) {
				// FIXME how to add ? jump to new add window, add return one
				// Vehicle Object and then add to vmb;
				pageController.getSmallList().get(jTable.getSelectedRow());
				// jTable.getSelectedRowCount();
				// vmb.saveVehicle(Vehicle);
			} else if (e.getSource() == delete) {
				// TODO delete the data in vmb(permanently delete);
				// deleteStudent(student);
			} else if (e.getSource() == exit) {
				// exit(student);
				System.exit(0);
			}

			else if (e.getSource() == enter) {
				int jump = Integer.parseInt(jumpPageField.getText().trim());
				pageController.jumpPage(jump);
			} else if (e.getSource() == firstPage) {
				tableModel.setVehicles(pageController.setFirstPage());
			} else if (e.getSource() == shift_left) {
				tableModel.setVehicles(pageController.shiftLeftPage());
			} else if (e.getSource() == pre) {
				tableModel.setVehicles(pageController.previousPage());
			} else if (e.getSource() == next) {
				tableModel.setVehicles(pageController.nextPage());
			} else if (e.getSource() == shift_right) {
				tableModel.setVehicles(pageController.shiftRightPage());
			} else if (e.getSource() == lastPage) {
				tableModel.setVehicles(pageController.setLastPage());
			}
			jumpPageField.setText(pageController.getCurentPageIndex() + "");
			// Replaces the current UI object with the latest version
			jTable.updateUI();
			// jTable.setModel(tableModel);
			// jTable.repaint();
		}

	}

	public void display() {
		setTitle("ManagementList Application..");
		setSize(1400, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// pack();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// FIXME fake data
		InventoryServiceAPI_Test inventoryServiceAPI_test = new InventoryServiceAPI_Test(
				"/Users/tingyuanzhang/Downloads/NeuSep17-Project-Group2-master/data/gmps-davis-chevrolet");
		new ManagementListApplication(inventoryServiceAPI_test);
	}

	private List<Vehicle> tmp(List<Vehicle> currentVehicleList, String filter) {
		List<Vehicle> res = new ArrayList<>();
		for (int i = 0; i < currentVehicleList.size(); i++) {
			if (currentVehicleList.get(i).toString().contains(filter)
					|| currentVehicleList.get(i).toString().contains(filter.toUpperCase()))
				res.add(currentVehicleList.get(i));
		}
		return res;
	}

	private boolean isAdjusting(JComboBox cbInput) {
		if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
			return (Boolean) cbInput.getClientProperty("is_adjusting");
		}
		return false;
	}

	private void setAdjusting(JComboBox cbInput, boolean adjusting) {
		cbInput.putClientProperty("is_adjusting", adjusting);
	}

	public void setupAutoCompleteAndFilter(final JTextField txtInput, ArrayList<String> items) {
		final DefaultComboBoxModel model = new DefaultComboBoxModel();
		final JComboBox cbInput = new JComboBox(model) {
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};
		setAdjusting(cbInput, false);
		for (String item : items) {
			model.addElement(item);
		}
		cbInput.setSelectedItem(null);
		cbInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isAdjusting(cbInput)) {
					if (cbInput.getSelectedItem() != null) {
						txtInput.setText(cbInput.getSelectedItem().toString());
					}
				}
			}
		});
		txtInput.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				cbInput.setPopupVisible(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				cbInput.setPopupVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		txtInput.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				setAdjusting(cbInput, true);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (cbInput.isPopupVisible()) {
						e.setKeyCode(KeyEvent.VK_ENTER);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.setSource(cbInput);
					cbInput.dispatchEvent(e);
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						txtInput.setText(cbInput.getSelectedItem().toString());
						cbInput.setPopupVisible(false);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cbInput.setPopupVisible(false);
				}
				setAdjusting(cbInput, false);
			}
		});
		txtInput.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				updateList();
			}

			public void removeUpdate(DocumentEvent e) {
				updateList();
			}

			public void changedUpdate(DocumentEvent e) {
				updateList();
			}

			private void updateList() {
				setAdjusting(cbInput, true);
				model.removeAllElements();
				String input = txtInput.getText();
				if (!input.isEmpty()) {
					for (String item : items) {
						if (item.toLowerCase().startsWith(input.toLowerCase())) {
							model.addElement(item);
						}
					}
				}
				cbInput.setPopupVisible(model.getSize() > 0);
				setAdjusting(cbInput, false);
				String category = categoryField.getText().trim();
				String year = yearField.getText().trim();
				String make = makeField.getText().trim();
				String type = typeField.getText().trim();
				// String model = modelField.getText().trim();
				String price = PriceField.getText().trim();
				currentVehicleList = InventoryServiceAPI_Test.vehiclesSearchAndFilter(totalVehicleList, category, year,
						make, price, type, null);
				pageController = new PageController(currentVehicleList);
				tableModel.setVehicles(pageController.setFirstPage());
				jumpPageField.setText("1");
				totalPageLabel.setText(pageController.getPageCount() + "");
				jTable.updateUI();
			}
		});
		txtInput.setLayout(new BorderLayout());
		txtInput.add(cbInput, BorderLayout.NORTH);
	}

}
