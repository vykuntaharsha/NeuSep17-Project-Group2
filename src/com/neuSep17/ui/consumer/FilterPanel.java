package com.neuSep17.ui.consumer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class FilterPanel extends JPanel
{
    private Map<String, List<String>> checkBoxPanelsMap;
    private BrowseInventoryFrame parent;
    private JScrollPane filterScrollPane;
    private FilterCheckBoxPanel[] checkBoxPanels;
    private String[] filterKeys = { "category", "year", "make", "price", "type" };
    private JPanel filterPanel;
    private JButton resetFilter;
    private int panelHeight = 595;

    public FilterPanel(BrowseInventoryFrame bif)
    {
        this.parent = bif;
        this.setLayout(new BorderLayout(0, 0));
        this.setPreferredSize(new Dimension(210, panelHeight));
        createComponents();
        addComponents();
    }

    public void setCheckBoxPanelsMap(Map<String, List<String>> checkBoxPanelsMap)
    {
        this.checkBoxPanelsMap = checkBoxPanelsMap;
        filterScrollPane.getVerticalScrollBar().setValue(0);
        resetFilterCheckBox();
        updateFilterCheckBoxPanels();
    }

    private void createComponents()
    {
        filterPanel = new JPanel();
        filterScrollPane = new JScrollPane();
        filterScrollPane.setPreferredSize(new Dimension(200, panelHeight));
        checkBoxPanels = new FilterCheckBoxPanel[filterKeys.length];
        for (int i = 0; i < checkBoxPanels.length; i++)
        {
            checkBoxPanels[i] = new FilterCheckBoxPanel(filterKeys[i], this);
        }
        resetFilter = new JButton("Clear All");

    }

    private void addComponents()
    {
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setAutoscrolls(true);
        filterPanel.setBorder(new TitledBorder("Filter"));
        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linePanel.add(resetFilter);
        filterPanel.add(linePanel);
        for (int i = 0; i < checkBoxPanels.length; i++)
        {
            filterPanel.add(checkBoxPanels[i]);
        }
        filterPanel.add(Box.createVerticalStrut(0));
        filterScrollPane.setViewportView(filterPanel);
        this.add(filterScrollPane);
    }

    public void addListeners()
    {
        resetFilter.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resetFilter();
            }
        });
    }

    private void updateFilterCheckBoxPanels()
    {
        for (int i = 0; i < checkBoxPanels.length; i++)
        {
            checkBoxPanels[i].populateCheckBoxes(checkBoxPanelsMap.get(filterKeys[i]));
        }
    }

    private void resetFilterCheckBox()
    {
        for (int i = 0; i < checkBoxPanels.length; i++)
        {
            checkBoxPanels[i].clearAllChecked();
        }
    }

    public void updateFilterConditions()
    {
        String categoriesFilter = checkBoxPanels[0].generateFilterCondition();
        if (categoriesFilter != null)
        {
            categoriesFilter = categoriesFilter.trim().toLowerCase();
        }
        parent.doFilter(categoriesFilter, checkBoxPanels[1].generateFilterCondition(),
                checkBoxPanels[2].generateFilterCondition(), checkBoxPanels[3].generateFilterCondition(),
                checkBoxPanels[4].generateFilterCondition());
    }

    public void resizeFilterPanel()
    {
        int height = 30;
        for (FilterCheckBoxPanel checkBoxPanel : checkBoxPanels)
        {
            height += checkBoxPanel.getCurrentHeight();
        }

        if (height < panelHeight)
        {
            filterPanel.remove(filterPanel.getComponentCount() - 1);
            filterPanel.add(Box.createVerticalStrut(panelHeight - 10 - height));
        }
        else
        {
            filterPanel.remove(filterPanel.getComponentCount() - 1);
            filterPanel.add(Box.createVerticalStrut(0));
        }
    }

    private void resetFilter()
    {
        resetFilterCheckBox();
        updateFilterConditions();
    }
}
