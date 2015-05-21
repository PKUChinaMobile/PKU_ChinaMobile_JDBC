package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * Created by mrpen on 2015/5/21.
 */
public class MyJTable extends JTable{
    private Color selectionColor = new Color(207,228,249);//行选择颜色
    private Color evenRowColor = new Color(233,242,241);//奇数行颜色
    private Color oddRowColor = new Color(255,255,255);//偶数行颜色
    private Color gridColor = new Color(236,233,216);//网格颜色
    private int rowHeight = 30;//行高度

    public MyJTable(TableModel tableModel){
        super(tableModel);
        this.setGridColor(gridColor);
        this.setRowHeight(rowHeight);
    }
    public TableCellRenderer getCellRenderer(int row, int column) {
        return new MyCellRenderer();
    }
    class MyCellRenderer extends DefaultTableCellRenderer{

        public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
            Component cell = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
            this.setColor(cell, table, isSelected, hasFocus, row, column);
            return cell;
        }
        /*
         * 设置颜色
         */
        private void setColor(Component component,JTable table,boolean isSelected,boolean hasFocus,int row,int column){
            if(isSelected){
                component.setBackground(selectionColor);
                setBorder(null);//去掉边
            }else{
                if(row%2 == 0){
                    component.setBackground(evenRowColor);
                }else{
                    component.setBackground(oddRowColor);
                }
            }
        }
    }
}
