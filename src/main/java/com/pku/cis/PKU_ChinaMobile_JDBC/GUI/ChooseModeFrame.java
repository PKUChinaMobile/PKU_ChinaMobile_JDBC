package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

/**
 * Created by mrpen on 2015/5/23.
 * 供用户选择查询模式
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.border.BevelBorder;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChooseModeFrame extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChooseModeFrame frame = new ChooseModeFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ChooseModeFrame() {
        setTitle("请选择查询模式");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel.setOpaque(true);
        panel.setBounds(10, 22, 414, 66);
        contentPane.add(panel);
        panel.setLayout(null);

        JButton btnNewButton = new JButton("可输入查询界面");
        btnNewButton.setBounds(10, 10, 142, 46);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectFrame sf = new SelectFrame();
                sf.setVisible(true);
                dispose();
            }
        });
        panel.add(btnNewButton);

        JTextArea textArea = new JTextArea();
        textArea.setBackground(new Color(240,240,240));
        textArea.setBounds(173, 10, 219, 46);
        textArea.setText("此模式提供可供用户直接输入的SQL语句查询界面");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        panel.add(textArea);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 98, 414, 66);
        panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_1.setOpaque(true);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JButton button = new JButton("命令行查询界面");
        button.setBounds(10, 10, 142, 46);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Console c = new Console();
                c.setVisible(true);
                dispose();
            }
        });
        panel_1.add(button);

        JTextArea textArea_1 = new JTextArea();
        textArea_1.setBackground(SystemColor.menu);
        textArea_1.setBounds(172, 10, 219, 46);
        textArea_1.setText("此模式提供命令行窗口式的查询接口");
        textArea_1.setLineWrap(true);
        textArea_1.setWrapStyleWord(true);
        panel_1.add(textArea_1);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(10, 174, 414, 66);
        panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_2.setOpaque(true);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        JButton button_1 = new JButton("预置查询界面");
        button_1.setBounds(10, 10, 142, 46);
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuildInFrame bf = new BuildInFrame();
                bf.setVisible(true);
                dispose();
            }
        });
        panel_2.add(button_1);

        JTextArea textArea_2 = new JTextArea();
        textArea_2.setBackground(SystemColor.menu);
        textArea_2.setBounds(171, 10, 219, 46);
        textArea_2.setText("此模式提供可供用户选择的预置查询，用户可以增删预置查询");
        textArea_2.setLineWrap(true);
        textArea_2.setWrapStyleWord(true);
        panel_2.add(textArea_2);
    }
}

