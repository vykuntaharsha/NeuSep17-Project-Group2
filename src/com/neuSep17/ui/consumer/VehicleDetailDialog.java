package com.neuSep17.ui.consumer;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class VehicleDetailDialog extends JDialog
{
    public VehicleDetailDialog(JFrame owner)
    {
        super(owner, true);
        createComponents();
        addComponents();
        addListeners();
        makeThisVisible();
    }

    private void createComponents()
    {

    }

    private void addComponents()
    {

    }

    private void addListeners()
    {

    }

    private void makeThisVisible()
    {
        this.setSize(1000, 1000);
        this.setVisible(true);
    }

}
