package com.neuSep17.ui.inventoryList.MainFrame;//Credit to com.neuSep17.ui.consumer team;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class SearchTextComboBoxListener extends KeyAdapter {
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

		// show search result when press enter
		if (key.getKeyCode() == key.VK_ENTER) {
			searchPanel.updateSearch();
			searchTextComboBox.hidePopup();
			return;
		}
		String text = ((JTextField) key.getSource()).getText();
		String typedWorld = getCurrentTypedWord(text);
		// no word typed, hide popup window
		if (typedWorld.equals("")) {
			searchTextComboBox.hidePopup();
			return;
		}
		// get popup window list
		filteredVector = getFilteredList(typedWorld);

		searchTextComboBox.setModel(new DefaultComboBoxModel(filteredVector));
		searchTextComboBox.setSelectedIndex(-1);

		typedWordBfSpace = getTypedWordBfSpace(text);

		((JTextField) searchTextComboBox.getEditor().getEditorComponent()).setText(text);
		if (filteredVector.size() != 0) {
			Object child = searchTextComboBox.getAccessibleContext().getAccessibleChild(0);
			BasicComboPopup popup = (BasicComboPopup) child;

			searchTextComboBox.showPopup();
			// set popup window's location
			popup.setLocation(searchTextComboBox.getLocationOnScreen().x + 3,
					searchTextComboBox.getLocationOnScreen().y + 48);

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