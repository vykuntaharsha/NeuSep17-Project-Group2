package com.neuSep17.ui.consumer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BrowseInventoryDialog extends JDialog
{
    public BrowseInventoryDialog(JFrame owner)
    {
        super(owner, true);
        createComponents();
        addComponents();
        addListeners();
        makeThisVisible();
    }

    private void createComponents()
    {
        // search panel
        createSearchPanelComponents();
        // filter panel
        createFilterPanelComponents();
        // list panel
        createListPanelComponents();
    }

    private void createSearchPanelComponents()
    {
        // TODO Auto-generated method stub

    }

    private void createFilterPanelComponents()
    {
        // TODO Auto-generated method stub

    }

    private void createListPanelComponents()
    {
        // TODO Auto-generated method stub

    }

    private void addComponents()
    {
        Container con = getContentPane();
        con.setLayout(new BorderLayout(1, 1));
        // search panel
        JPanel searchPanel = new JPanel();
        addSearchPanelComponents(searchPanel);
        con.add(searchPanel, BorderLayout.NORTH);
        // filter panel
        JPanel filterPanel = new JPanel();
        addFilterPanelComponents(filterPanel);
        con.add(filterPanel, BorderLayout.WEST);
        // list panel
        JPanel listPanel = new JPanel();
        addListPanelComponents(listPanel);
        con.add(listPanel, BorderLayout.CENTER);
    }

    private void addSearchPanelComponents(JPanel searchPanel)
    {
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void addFilterPanelComponents(JPanel filterPanel)
    {
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void addListPanelComponents(JPanel listPanel)
    {
        listPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void addListeners()
    {
        // search panel
        addSearchPanelListeners();
        // filter panel
        addFilterPanelListeners();
        // list panel
        addListPanelListeners();
    }

    private void addSearchPanelListeners()
    {
        // TODO Auto-generated method stub

    }

    private void addFilterPanelListeners()
    {
        // TODO Auto-generated method stub

    }

    private void addListPanelListeners()
    {
        // TODO Auto-generated method stub

    }

    private void makeThisVisible()
    {
        this.setSize(1200, 700);
        this.setVisible(true);

    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        BrowseInventoryDialog bid = new BrowseInventoryDialog(frame);
    }
}
