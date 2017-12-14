package com.neuSep17.ui.inventoryList.CenterSection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ImageControlPanel extends JPanel {
    private JLabel currentPageLable, totalPageLable;
    private JTextField jumpPageFileld;
    private JButton preButton, nextButton, jumpButton;
    private ImageView parent;

    public ImageControlPanel(ImageView parent){
        super();
        this.parent = parent;

        currentPageLable = new JLabel("Current Page: ");
        totalPageLable = new JLabel("Total Page: "+parent.getPageController().getPageCount());
        jumpPageFileld = new JFormattedTextField();
        jumpPageFileld.setColumns(5);
        jumpPageFileld.setText(parent.getPageController().getCurentPageIndex()+"");
        jumpButton = new JButton("Jump");
        jumpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int jumpPage = Integer.parseInt(jumpPageFileld.getText().trim());
                parent.getPageController().jumpPage(jumpPage);
            //    jumpPageFileld.setText(parent.getPageController().getCurentPageIndex()+"");
                parent.update();
            }
        });

        preButton = new JButton("Pre");
        preButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getPageController().previousPage();
              //  jumpPageFileld.setText(parent.getPageController().getCurentPageIndex()+"");
                parent.update();
            }
        });
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getPageController().nextPage();
                //jumpPageFileld.setText(parent.getPageController().getCurentPageIndex()+"");
                parent.update();
            }//
        });

        this.add(currentPageLable);
        this.add(jumpPageFileld);
        this.add(jumpButton);
        this.add(preButton);
        this.add(nextButton);
        this.add(totalPageLable);

    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getPreButton() {
        return preButton;
    }

    public JLabel getCurrentPageLable() {
        return currentPageLable;
    }

    public JLabel getTotalPageLable() {
        return totalPageLable;
    }

    public JTextField getJumpPageFileld() {
        return jumpPageFileld;
    }

}