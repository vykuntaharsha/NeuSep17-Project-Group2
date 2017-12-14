package com.neuSep17.ui.inventoryList.CenterSection;

import com.neuSep17.ui.inventoryList.CenterSection.DealerMainWindow;
import com.neuSep17.dto.Vehicle;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;

public class SearchPanel extends JPanel {
	private DealerMainWindow mainWindow;
	private List<Vehicle> vehicles = new ArrayList<>();

	private JPanel searchPanel;
	private JLabel sortLabel;
	private JLabel searchLabel;
	private JComboBox searchTextComboBox;
	private JComboBox<String> sortComboBox;

	private Vector dictionaryVector;
	private HashSet<String> dictionary;

	public String[] sortKeys = { "Select Sort By", "Price: High To Low", "Price: Low To High", "Year: High To Low",
			"Year: Low To High", "Make: A - Z", "Make: Z - A" };

	public SearchPanel(DealerMainWindow mainWindow, List<Vehicle> vehicles) {
		this.mainWindow = mainWindow;
		this.vehicles = vehicles;
		this.setLayout(new BorderLayout(0, 0));
		this.setPreferredSize(new Dimension(1200, 50));
		dictionaryVector = new Vector();
		createSearchPanelComponents();
		addSearchPanelComponents();
	}

	private void createSearchPanelComponents() {
		searchPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				ImageIcon backImage = new ImageIcon("data/images/Banner-Commodore.gif");
				g.drawImage(backImage.getImage(), 0, 0, this.getSize().width, this.getSize().height, this);
			}
		};
		searchPanel.setOpaque(true);

		searchTextComboBox = new JComboBox();
		for (Component component : searchTextComboBox.getComponents()) {
			if (component instanceof JButton) {
				searchTextComboBox.remove(component);
			}
		}
		setDictionaryVector();
		setSearchTextComboBox();
		searchLabel = new JLabel("Search :");
		searchLabel.setForeground(Color.WHITE);

		sortLabel = new JLabel("Sort by : ");
		sortLabel.setForeground(Color.WHITE);
		sortComboBox = new JComboBox();
		for (int i = 0; i < sortKeys.length; i++) {
			sortComboBox.addItem(sortKeys[i]);
		}

		setItemsLocation();
	}

	private void setItemsLocation() {
		searchLabel.setBounds(mainWindow.getSize().width - 250, 120, 80, 60);
		searchTextComboBox.setBounds(mainWindow.getSize().width - 180, 135, 180, 30);
		sortLabel.setBounds(mainWindow.getSize().width - 250, 150, 80, 60);
		sortComboBox.setBounds(mainWindow.getSize().width - 180, 150, 170, 60);
	}

	private void setSearchTextComboBox() {
		searchTextComboBox.setMaximumSize(new Dimension(1200, 30));
		searchTextComboBox.setPreferredSize(new Dimension(400, 30));

		Vector vector = new Vector();
		searchTextComboBox.setModel(new DefaultComboBoxModel(vector));
		searchTextComboBox.setSelectedIndex(-1);
		searchTextComboBox.setEditable(true);
		JTextField text = (JTextField) searchTextComboBox.getEditor().getEditorComponent();
		text.setFocusable(true);
		text.setText("");

		SearchTextComboBoxListener searchTextListener = new SearchTextComboBoxListener(searchTextComboBox, dictionary, this);
		text.addKeyListener(searchTextListener);
		searchTextComboBox.addActionListener(new SearchComboBoxActionListener(searchTextListener));
	}

	private void setDictionary() {
		dictionary = new HashSet<String>();
		for (Vehicle v : vehicles) {
			dictionary.add(v.getYear().toString().toLowerCase());
			dictionary.add(v.getMake().toLowerCase());
			dictionary.add(v.getModel().toLowerCase());
			dictionary.add(v.getBodyType().toLowerCase());
			dictionary.add(v.getTrim().toLowerCase());
			dictionary.add(v.getCategory().toString().toLowerCase());
		}
		if (dictionary.contains("")) {
			dictionary.remove("");
		}
	}

	private void setDictionaryVector() {
		setDictionary();
		Iterator<String> iterator = dictionary.iterator();
		while (iterator.hasNext()) {
			dictionaryVector.add(iterator.next());
		}
	}

	private void addSearchPanelComponents() {

		searchPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		searchPanel.setLayout(null);

		searchPanel.add(searchLabel);
		searchPanel.add(searchTextComboBox);
		searchPanel.add(sortLabel);
		searchPanel.add(sortComboBox);
		this.add(searchPanel);
	}

	public void addListeners() {
		addSearchPanelListeners();
		addSortPanelListeners();
	}

	private void addSearchPanelListeners() {
		SearchListener searchlistener = new SearchListener();
	}

	private void addSortPanelListeners() {
		SortListener sortlistener = new SortListener();
		sortComboBox.addActionListener(sortlistener);
	}

	public void updateSearch() {
		JTextField searchText = (JTextField) searchTextComboBox.getEditor().getEditorComponent();
		String searchInfo = searchText.getText();

		mainWindow.doSearch(searchInfo);
	}

	public void updateSort() {
		String sortMethod = sortComboBox.getSelectedIndex() == 0 ? null : (String) sortComboBox.getSelectedItem();
		mainWindow.doSort(sortMethod);
	}

	public JComboBox<String> getSortItem() {
		return this.sortComboBox;
	}

	public String[] getSortKeys() {
		return sortKeys;
	}

	class SearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			updateSearch();
		}
	}

	class SortListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			updateSort();
		}
	}
}

class SearchTextComboBoxListener extends KeyAdapter {
	private JComboBox searchTextComboBox;
	private HashSet<String> dictionary;
	private Vector filteredVector;
	private SearchPanel searchPanel;
	private String typedWordBfSpace;

	public SearchTextComboBoxListener(JComboBox searchTextComboBox, HashSet<String> dictionary,
			SearchPanel searchPanel) {
		this.searchTextComboBox = searchTextComboBox;
		this.dictionary = dictionary;
		this.searchPanel = searchPanel;
		this.typedWordBfSpace = "";
	}

	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == key.VK_DOWN || key.getKeyCode() == key.VK_UP) {
			return;
		}

		if (key.getKeyCode() == key.VK_ENTER) {
			searchPanel.updateSearch();
			searchTextComboBox.hidePopup();
			return;
		}
		String text = ((JTextField) key.getSource()).getText();
		String typedWorld = getCurrentTypedWord(text);

		if (typedWorld.equals("")) {
			searchTextComboBox.hidePopup();
			return;
		}

		filteredVector = getFilteredList(typedWorld);

		searchTextComboBox.setModel(new DefaultComboBoxModel(filteredVector));
		searchTextComboBox.setSelectedIndex(-1);

		typedWordBfSpace = getTypedWordBfSpace(text);

		((JTextField) searchTextComboBox.getEditor().getEditorComponent()).setText(text);
		if (filteredVector.size() != 0) {
			Object child = searchTextComboBox.getAccessibleContext().getAccessibleChild(0);
			BasicComboPopup popup = (BasicComboPopup) child;

			searchTextComboBox.showPopup();
			popup.setLocation(searchTextComboBox.getLocationOnScreen().x + 3,
					searchTextComboBox.getLocationOnScreen().y + 30);

		} else {
			searchTextComboBox.hidePopup();
		}
		if (key.getKeyCode() == key.VK_ENTER) {
			searchTextComboBox.hidePopup();
		}

	}

	public void updateAutoCompleteLabel(ActionEvent e) {
		if (e.getActionCommand().equals("comboBoxEdited"))
			return;
		String text = (String) searchTextComboBox.getSelectedItem();
		if (searchTextComboBox.getSelectedIndex() == -1) {
			return;
		}
		if (!typedWordBfSpace.equals("") && text != null) {
			searchTextComboBox.setSelectedItem(typedWordBfSpace + " " + text);
		}
	}

	public String getCurrentTypedWord(String text) {
		String typedWord = "";
		int lastIndex = text.lastIndexOf(" ");
		if (lastIndex > -1) {
			typedWord = text.substring(lastIndex);
		} else {
			typedWord = text;
		}
		return typedWord.trim();
	}

	public String getTypedWordBfSpace(String text) {
		String typedWordBfSpace = "";
		int lastIndex = text.lastIndexOf(" ");
		if (lastIndex > -1) {
			typedWordBfSpace = text.substring(0, lastIndex);
		}
		return typedWordBfSpace.trim();
	}

	private Vector getFilteredList(String text) {
		Vector newFilteredVector = new Vector();
		Iterator<String> iterator = dictionary.iterator();
		while (iterator.hasNext()) {
			String word = iterator.next();
			if (isMatchedinDic(text, word)) {
				newFilteredVector.add(word);
			}
		}
		return newFilteredVector;
	}

	private boolean isMatchedinDic(String text, String word) {
		boolean isMatched = true;
		for (int i = 0; i < text.length(); i++) {
			if (word.length() <= i) {
				continue;
			}
			if (!text.toLowerCase().startsWith(String.valueOf(word.toLowerCase().charAt(i)), i)) {
				isMatched = false;
				break;
			}
		}
		return isMatched;
	}
}

class SearchComboBoxActionListener implements ActionListener {
	private SearchTextComboBoxListener searchTextListener;

	public SearchComboBoxActionListener(SearchTextComboBoxListener searchTextListener) {
		this.searchTextListener = searchTextListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		searchTextListener.updateAutoCompleteLabel(e);
	}
}