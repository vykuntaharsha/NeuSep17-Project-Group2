package CenterSection;

import com.neuSep17.dto.Inventory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InventoryControlPanel extends JPanel{
    private JLabel currentPageLable, totalPageLable;
    private JTextField jumpPageFileld;
    private JButton preButton, nextButton;
    private PageController pageController;
    private InventoryTableView parent;

    public InventoryControlPanel(PageController pageController, InventoryTableView parent){
        super();
        this.pageController = pageController;
        this.parent = parent;
        currentPageLable = new JLabel("Current Page: ");
        totalPageLable = new JLabel("Total Page: "+pageController.getPageCount());
        jumpPageFileld = new JFormattedTextField();
        jumpPageFileld.setColumns(5);
        jumpPageFileld.setText(pageController.getCurentPageIndex()+"");
        jumpPageFileld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE||
                        e.getKeyCode() == KeyEvent.VK_ENTER){
                    int jumpPage = Integer.parseInt(jumpPageFileld.getText().trim());
                    pageController.jumpPage(jumpPage);
                    jumpPageFileld.setText(pageController.getCurentPageIndex()+"");
                    parent.update();
                }
            }
        });
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
