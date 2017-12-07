package com.neuSep17.ui.manageIncentives;

import javax.swing.*;
import java.io.IOException;

public class IncentiveAPP {

    public IncentiveAPP(){
        SwingUtilities.invokeLater(() -> {
            try {
                new IncentiveMainFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static void main (String[] args) throws Exception{

        SwingUtilities.invokeLater(() -> {
            try {
                new IncentiveMainFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
