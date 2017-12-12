package CenterSection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncentiveControlPanel extends JPanel{
    private JLabel currentPageLable, totalPageLable;
    private JTextField jumpPageFileld;
    private JButton preButton, nextButton;
    private PageController pageController;
    private IncentiveTableView parent;

    public IncentiveControlPanel(PageController pageController, IncentiveTableView parent){
        super();
        this.pageController = pageController;
        this.parent = parent;
        currentPageLable = new JLabel("Current Page: ");
        totalPageLable = new JLabel("Total Page: "+pageController.getPageCount());
        jumpPageFileld = new JFormattedTextField();
        jumpPageFileld.setColumns(5);
        jumpPageFileld.setText(pageController.getCurentPageIndex()+"");
        preButton = new JButton("Pre");
        preButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageController.previousPage();
                jumpPageFileld.setText(pageController.getCurentPageIndex()+"");
                parent.update();
            }//
        });
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageController.nextPage();
                jumpPageFileld.setText(pageController.getCurentPageIndex()+"");
                parent.update();
            }//
        });

        this.add(currentPageLable);
        this.add(jumpPageFileld);
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
