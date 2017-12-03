import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncentiveDeleteDialog extends JDialog {
    private JLabel explaination;
    private JButton yes, no;

    private IncentiveServiceAPI_Test incentiveAPI;
    private Incentive incentive;

    public IncentiveDeleteDialog(Incentive incentive){
        this.incentive = incentive;
        setTitle("Delete The Incentive");
        setSize(600,250);
        setVisible(true);

        create();
        makeListeners();
        display();
    }

    public void create(){
        explaination = new JLabel("Do you want to delete this incentive ?");
        explaination.setFont(new Font("Menlo", Font.PLAIN, 18));
        yes = new JButton("Yes");
        no = new JButton("Cancel");
    }

    public void display(){
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.NORTH;
        add(explaination, gc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(yes);
        buttonPanel.add(no);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel,gc);
    }

    public void makeListeners(){
        DeleteActionListener dal = new DeleteActionListener();
        yes.addActionListener(dal);
        no.addActionListener(dal);
    }

    class DeleteActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == yes){
                incentiveAPI.deleteIncentive(incentive.getId());
            }
            if(e.getSource() == no){
                setVisible(false);
            }
        }
    }
}
