package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;
/**
 * 用于实现按钮图标化的事件监听器
 * Created by mrpen on 2015/5/21
 * */

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class MyMouseListener implements MouseListener {

    public MyMouseListener() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        String btnName =((JButton)e.getSource()).getText();
        if ("File".equals(btnName.trim())) {
            //鼠标点击事件
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        JButton btn = (JButton) e.getComponent();
        Border etchedBorder = new EtchedBorder(EtchedBorder.RAISED);// 设置边框凸显
        btn.setBorder(etchedBorder);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        JButton btn = (JButton) e.getComponent();
        Border etchedBorder = new EtchedBorder(EtchedBorder.LOWERED);// 设置边框凹显
        btn.setBorder(etchedBorder);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        JButton btn = (JButton) e.getComponent();
        btn.setForeground(new Color(0x33, 0x66, 0xcc));// 设置字体颜色
        btn.setBorderPainted(true);// 显示边框
        Border etchedBorder = new EtchedBorder(EtchedBorder.LOWERED);// 设置边框凹显
        btn.setBorder(etchedBorder);
        btn.setRolloverEnabled(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        JButton btn = (JButton) e.getComponent();
        btn.setForeground(Color.black);// 设置字体颜色
        btn.setBorderPainted(false);// 隐藏边框
    }

}