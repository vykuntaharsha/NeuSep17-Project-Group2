package MainFrame;//package com.neuSep17.ui.consumer;

import CenterSection.DealerMainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class FilterPanel extends JPanel {
	private Map<String, Map<String, Integer>> checkBoxPanelsMap;
	private DealerMainWindow parent;
	private JScrollPane filterScrollPane;
	private FilterCheckBoxPanel[] checkBoxPanels;
	private String[] filterKeys = { "category", "make", "year", "price", "type" };
	private JPanel filterPanel;
	private JButton resetFilter;
	private int northPanelHeight = 280;

	public FilterPanel(DealerMainWindow parent) {
		this.parent = parent;
		this.setLayout(new BorderLayout(0, 0));
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(210, parent.getSize().height - northPanelHeight));
		createComponents();
		addComponents();
	}

	public void setCheckBoxPanelsMap(Map<String, Map<String, Integer>> checkBoxPanelsMap) {
		this.checkBoxPanelsMap = checkBoxPanelsMap;
		filterScrollPane.getVerticalScrollBar().setValue(0);
		resetFilterCheckBox();
		updateFilterCheckBoxPanels();
	}

	public void setEnableCheckBoxMap(Map<String, Map<String, Integer>> enableCheckBoxesMap, String currentChecked) {
		for (int i = 0; i < checkBoxPanels.length; i++) {
			checkBoxPanels[i].setEnableCheckBoxes(enableCheckBoxesMap.get(filterKeys[i]), currentChecked);
		}
	}

	private void createComponents() {
		filterPanel = new JPanel();
		filterScrollPane = new JScrollPane();
		filterScrollPane.setPreferredSize(new Dimension(200, parent.getSize().height - northPanelHeight));
		filterScrollPane.setOpaque(false);
		checkBoxPanels = new FilterCheckBoxPanel[filterKeys.length];
		for (int i = 0; i < checkBoxPanels.length; i++) {
			checkBoxPanels[i] = new FilterCheckBoxPanel(filterKeys[i], this);
		}
		resetFilter = new JButton("Clear All");
	}

	private void addComponents() {
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
		filterPanel.setOpaque(false);
		filterPanel.setAutoscrolls(true);
		filterPanel.setBorder(new TitledBorder("Narrow Matches By "));
		JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		linePanel.setOpaque(false);
		linePanel.add(resetFilter);
		filterPanel.add(linePanel);
		for (int i = 0; i < checkBoxPanels.length; i++) {
			filterPanel.add(checkBoxPanels[i]);
		}
		filterPanel.add(Box.createVerticalStrut(0));
		filterScrollPane.setViewportView(filterPanel);
		this.add(filterScrollPane);
	}

	public void addListeners() {
		resetFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetFilter();
			}
		});
	}

	private void updateFilterCheckBoxPanels() {
		for (int i = 0; i < checkBoxPanels.length; i++) {
			checkBoxPanels[i].populateCheckBoxes(checkBoxPanelsMap.get(filterKeys[i]));
		}
	}

	private void resetFilterCheckBox() {
		for (int i = 0; i < checkBoxPanels.length; i++) {
			checkBoxPanels[i].clearAllChecked();
		}
	}

	public void updateFilterConditions(String currentChecked) {
		String categoriesFilter = checkBoxPanels[0].generateFilterCondition();
		if (categoriesFilter != null) {
			categoriesFilter = categoriesFilter.trim().toLowerCase();
		}
		parent.doFilter(categoriesFilter, checkBoxPanels[1].generateFilterCondition(),
				checkBoxPanels[2].generateFilterCondition(), checkBoxPanels[3].generateFilterCondition(),
				checkBoxPanels[4].generateFilterCondition(), currentChecked);
	}

	public void resizeFilterPanel() {
		int height = 30;
		for (FilterCheckBoxPanel checkBoxPanel : checkBoxPanels) {
			height += checkBoxPanel.getCurrentHeight();
		}

		if (height < parent.getSize().height - northPanelHeight) {
			filterPanel.remove(filterPanel.getComponentCount() - 1);
			filterPanel.add(Box.createVerticalStrut(parent.getSize().height - northPanelHeight - height));
		} else {
			filterPanel.remove(filterPanel.getComponentCount() - 1);
			filterPanel.add(Box.createVerticalStrut(0));
		}
	}

	private void resetFilter() {
		resetFilterCheckBox();
		updateFilterConditions("");
	}
}