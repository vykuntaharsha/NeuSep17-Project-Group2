package MainFrame;//package com.neuSep17.ui.consumer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FilterCheckBoxPanel extends JPanel {
	private final int DEFAULT_CHECKBOXES_NUMBER = 4;
	private List<JCheckBox> checkBoxes;
	private String title;
	private JButton buttonHide;
	private JButton buttonShow;
	private boolean isButtonHide = true;
	private JButton buttonMore;
	private JButton buttonLess;
	private JPanel checkBoxesPanel;
	private boolean isButtonMore = true;
	private FilterCheckBoxListener fcbl;
	private FilterPanel parent;

	public FilterCheckBoxPanel(String title, FilterPanel parent) {
		this.title = title;
		this.parent = parent;
		this.checkBoxes = new ArrayList<JCheckBox>();
		this.fcbl = new FilterCheckBoxListener();
		this.setOpaque(false);
		createPanelComponents();
		addPanelComponents();
		addPanelListeners();
	}

	private void createPanelComponents() {
		createShowAndHideComponents();
		checkBoxesPanel = new JPanel();
		createLessAndMoreComponents();
	}

	private void addPanelComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addShowAndHideComponents();
		this.add(checkBoxesPanel);
		addLessAndMoreComponents();
	}

	private void addPanelListeners() {
		addButtonsListeners();
	}

	public void clearAllChecked() {
		removeCheckBoxesListeners();
		for (JCheckBox checkBox : checkBoxes) {
			checkBox.setSelected(false);
		}
		addCheckBoxesListeners();
	}

	public int getCurrentHeight() {
		if (!isButtonHide) {
			return 40;
		}

		if (!isButtonMore) {
			return 40 + 30 + checkBoxes.size() * 25;
		}

		if (checkBoxes.size() > 4) {
			return 40 + 30 + 4 * 25;
		}
		return 40 + checkBoxes.size() * 25;
	}

	public void populateCheckBoxes(Map<String, Integer> itemsMap) {
		if (itemsMap == null) {
			return;
		}

		removeCheckBoxesListeners();
		checkBoxes.clear();
		createCheckBoxesComponents(itemsMap);
		addCheckBoxesComponents();
		hideCheckBoxesFrom(DEFAULT_CHECKBOXES_NUMBER);
		resetButtons();
		addCheckBoxesListeners();
	}

	public void setEnableCheckBoxes(Map<String, Integer> enableItemsMap, String currentChecked) {
		if (enableItemsMap == null || (title.equals(currentChecked) && !isAllUnchecked())) {
			return;
		}

		Set<String> enableItems = enableItemsMap.keySet();
		for (JCheckBox checkBox : checkBoxes) {
			if (enableItems.contains(checkBox.getName())) {
				checkBox.setText(checkBox.getName() + " (" + enableItemsMap.get(checkBox.getName()) + ")");
				checkBox.setEnabled(true);
			} else {
				checkBox.setText(checkBox.getName() + " (0)");
				checkBox.setEnabled(false);
			}
		}
	}

	private boolean isAllUnchecked() {
		for (JCheckBox checkBox : checkBoxes) {
			if (checkBox.isSelected()) {
				return false;
			}
		}
		return true;
	}

	private void resetButtons() {
		isButtonHide = false;
		buttonHide.setVisible(true);
		buttonShow.setVisible(false);
		isButtonMore = true;
		if (checkBoxes.size() >= 5) {
			buttonMore.setVisible(true);
			buttonLess.setVisible(false);
		} else {
			buttonMore.setVisible(false);
			buttonLess.setVisible(false);
		}
	}

	private void removeCheckBoxesListeners() {
		for (JCheckBox checkBox : checkBoxes) {
			checkBox.removeActionListener(fcbl);
		}
	}

	private void createShowAndHideComponents() {
		ImageIcon hideIcon = new ImageIcon("data/images/hideIcon.png");
		buttonHide = new JButton("", hideIcon);
		buttonHide.setBorderPainted(false);
		buttonHide.setPreferredSize(new Dimension(20, 20));

		ImageIcon showIcon = new ImageIcon("data/images/showIcon.png");
		buttonShow = new JButton("", showIcon);
		buttonShow.setBorderPainted(false);
		buttonShow.setPreferredSize(new Dimension(20, 20));
		buttonShow.setVisible(false);
	}

	private void createCheckBoxesComponents(Map<String, Integer> itemsMap) {
		for (Entry<String, Integer> entry : itemsMap.entrySet()) {
			JCheckBox checkBox = new JCheckBox();
			checkBox.setText(entry.getKey() + " (" + entry.getValue() + ")");
			checkBox.setName(entry.getKey());
			checkBoxes.add(checkBox);
		}
	}

	private void createLessAndMoreComponents() {
		buttonMore = new JButton("More");
		buttonMore.setOpaque(false);
		buttonMore.setVisible(false);

		buttonLess = new JButton("Less");
		buttonLess.setOpaque(false);
		buttonLess.setVisible(false);
	}

	private void addShowAndHideComponents() {
		JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		linePanel.setOpaque(false);
		linePanel.add(buttonHide);
		linePanel.add(buttonShow);
		linePanel.add(new JLabel(title.toUpperCase()));
		this.add(linePanel);
	}

	private void addCheckBoxesComponents() {
		checkBoxesPanel.removeAll();
		checkBoxesPanel.setLayout(new BoxLayout(checkBoxesPanel, BoxLayout.Y_AXIS));
		checkBoxesPanel.setOpaque(false);
		for (JCheckBox checkBox : checkBoxes) {
			JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			linePanel.setOpaque(false);
			linePanel.add(checkBox);
			linePanel.setPreferredSize(
					new Dimension(checkBox.getPreferredSize().width, checkBox.getPreferredSize().height + 2));
			checkBoxesPanel.add(linePanel);
		}
	}

	private void addLessAndMoreComponents() {
		JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		linePanel.setOpaque(false);
		linePanel.add(buttonMore);
		linePanel.add(buttonLess);
		this.add(linePanel);
	}

	private void hideCheckBoxesFrom(int index) {
		for (int i = 0; i < index && i < checkBoxes.size(); i++) {
			checkBoxesPanel.getComponent(i).setVisible(true);
		}

		for (int i = index; i < checkBoxes.size(); i++) {
			checkBoxesPanel.getComponent(i).setVisible(false);
		}
	}

	private void addCheckBoxesListeners() {
		for (JCheckBox checkBox : checkBoxes) {
			checkBox.addActionListener(fcbl);
		}
	}

	private void addButtonsListeners() {
		DisplayButtonsListener dbl = new DisplayButtonsListener();
		buttonHide.addActionListener(dbl);
		buttonShow.addActionListener(dbl);
		buttonMore.addActionListener(dbl);
		buttonLess.addActionListener(dbl);
	}

	public String generateFilterCondition() {
		StringBuilder filter = new StringBuilder();
		int count = 0;
		for (JCheckBox checkBox : checkBoxes) {
			if (checkBox.isSelected()) {
				filter.append(checkBox.getName()).append(";");
				count++;
			}
		}

		if (count == 0 || count == checkBoxes.size()) {
			filter = null;
		} else {
			filter.deleteCharAt(filter.length() - 1);
		}

		return filter == null ? null : filter.toString();
	}

	class DisplayButtonsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = 0;
			if (e.getSource() == buttonHide) {
				isButtonHide = false;
				buttonHide.setVisible(false);
				buttonShow.setVisible(true);
				buttonMore.setVisible(false);
				buttonLess.setVisible(false);
			} else if (e.getSource() == buttonShow) {
				isButtonHide = true;
				buttonShow.setVisible(false);
				buttonHide.setVisible(true);

				if (isButtonMore) {
					if (checkBoxes.size() > 4) {
						buttonMore.setVisible(true);
					}
					index = DEFAULT_CHECKBOXES_NUMBER;
				} else if (!isButtonMore && checkBoxes.size() > 4) {
					if (checkBoxes.size() > 4) {
						buttonLess.setVisible(true);
					}
					index = checkBoxes.size();
				}
			} else if (e.getSource() == buttonMore) {
				buttonMore.setVisible(false);
				buttonLess.setVisible(true);
				isButtonMore = false;
				index = checkBoxes.size();
			} else if (e.getSource() == buttonLess) {
				buttonLess.setVisible(false);
				buttonMore.setVisible(true);
				isButtonMore = true;
				index = DEFAULT_CHECKBOXES_NUMBER;
			}
			hideCheckBoxesFrom(index);
			parent.resizeFilterPanel();
		}
	}

	public boolean isButtonHide() {
		return isButtonHide;
	}

	class FilterCheckBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			parent.updateFilterConditions(title);
		}
	}
}