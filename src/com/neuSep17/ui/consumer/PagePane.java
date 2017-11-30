package com.neuSep17.ui.consumer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

class PagePane extends JPanel
{
    final int page;

    PagePane(BrowseInventoryFrame ancestor, int maxPage)
    {
        super();
        page=ancestor.getPage();
        JButton first = new JButton("<<");
        JButton pre = new JButton("<");
        JLabel cur = new JLabel("Page: " + (page + 1));
        JButton next = new JButton(">");
        JButton last = new JButton(">>");
        this.add(first);
        this.add(pre);
        this.add(cur);
        this.add(next);
        this.add(last);

        if (page == 0)
        {
            pre.setEnabled(false);
            first.setEnabled(false);
        }
        if (page == maxPage)
        {
            next.setEnabled(false);
            last.setEnabled(false);
        }

        first.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("to first page: 1");
                ancestor.setPage(0);
                ancestor.displaytoList();
            }
        });

        pre.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("to previous page: " + page);
                ancestor.setPage(page-1);
                ancestor.displaytoList();
            }
        });

        next.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("to next page: " + (page + 2));
                ancestor.setPage(page+1);
                ancestor.displaytoList();
            }
        });

        last.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("to last page: " + (maxPage + 1));
                ancestor.setPage(maxPage);
                ancestor.displaytoList();
            }
        });

    }
}