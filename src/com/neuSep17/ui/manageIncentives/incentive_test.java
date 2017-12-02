package com.neuSep17.ui.manageIncentives;

import javax.swing.*;

/**
 * Created by starhaotian on 01/12/2017.
 */
public class incentive_test {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new IncentiveDisplay();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

    }
}
