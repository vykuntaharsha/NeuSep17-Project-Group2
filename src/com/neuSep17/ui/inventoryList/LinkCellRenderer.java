

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LinkCellRenderer extends DefaultTableCellRenderer implements MouseInputListener {
    private JTable jTable;
    private int col = 8;


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        jTable = table;
        super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        this.setText(value.toString());
        if (column == this.col) {
            //改变前景色(文字颜色)
            this.setForeground(Color.blue);
            //显示超链接样式
            this.setText("<html><u>" + value.toString() + "</u></html>");

        }
        return this;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        int c = jTable.columnAtPoint(p);
        if(c != 8){
            return;
        }
        int r = jTable.rowAtPoint(p);
        try {
            //取得目标单元格的值,即链接信息
            URL url = new URL(jTable.getValueAt(r, c).toString());
            //在系统默认浏览器中打开链接
            Desktop.getDesktop().browse(url.toURI());
        } catch (Exception ex) {
            Logger.getLogger(LinkCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();
        int c = jTable.columnAtPoint(p);
        if(c != 8){
            jTable.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        else
            //改变鼠标形状
            jTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }
}