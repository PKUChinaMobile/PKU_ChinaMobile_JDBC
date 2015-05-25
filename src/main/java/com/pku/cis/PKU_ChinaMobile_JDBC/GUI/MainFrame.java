package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

/**
 * Created by mrpen on 2015/5/5.
 */
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.PermissionManager;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainFrame extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
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
    public MainFrame() {
        setTitle("透明网关系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 530, 280);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.gridheight = 8;
        gbc_panel.gridwidth = 5;
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 9;
        gbc_panel.gridy = 0;
        contentPane.add(panel, gbc_panel);
        panel.setLayout(new GridLayout(4, 0, 0, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Application"));

        panel.setOpaque(true);

        JButton btnNewButton_1 = new JButton("数据源管理");
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNewButton_1.setEnabled(false);
                DataSourceManagerFrame dmf = new DataSourceManagerFrame();
                dmf.setVisible(true);
                dmf.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        btnNewButton_1.setEnabled(true);
                        super.windowClosing(e);
                    }
                });

            }
        });
        panel.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("元数据管理");
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    btnNewButton_2.setEnabled(false);
                    MetaDataManagerFrame mmf = new MetaDataManagerFrame();
                    mmf.setVisible(true);
                    mmf.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            btnNewButton_2.setEnabled(true);
                            super.windowClosing(e);
                        }
                    });

            }
        });
        panel.add(btnNewButton_2);

        JButton btnNewButton = new JButton("权限管理");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Global.permission != 2)
                    JOptionPane.showMessageDialog(null, "没有执行权限", "提示", JOptionPane.ERROR_MESSAGE);
                else {
                    btnNewButton.setEnabled(false);
                    PermissionManagerFrame pmf = new PermissionManagerFrame();
                    pmf.setVisible(true);
                    pmf.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            btnNewButton.setEnabled(true);
                            super.windowClosing(e);
                        }
                    });
                }
            }
        });
        panel.add(btnNewButton);

        JButton btnNewButton_3 = new JButton("数据查询");
        panel.add(btnNewButton_3);
        btnNewButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChooseModeFrame c = new ChooseModeFrame();
                c.setVisible(true);
            }
        });

        JPanel panel_2 = new JPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridheight = 6;
        gbc_panel_2.gridwidth = 9;
        gbc_panel_2.insets = new Insets(0, 0, 5, 5);
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 0;
        gbc_panel_2.gridy = 0;
        contentPane.add(panel_2, gbc_panel_2);
        panel_2.setLayout(new GridLayout(1, 0, 0, 0));

        JLabel label = new JLabel("");
        label.setIcon(new ImageIcon("res/image/logo1.png"));
        panel_2.add(label);

        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridheight = 3;
        gbc_panel_1.gridwidth = 9;
        gbc_panel_1.insets = new Insets(0, 0, 0, 5);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 6;
        contentPane.add(panel_1, gbc_panel_1);
        panel_1.setLayout(new GridLayout(1, 0, 0, 0));

        JLabel lblNewLabel = new JLabel("<html><body><p style=\"padding:10pt\">北京大学<br>机器感知与智能教育部重点实验室<br>透明网关系统1.0</p></body></html>");
        lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(lblNewLabel);


    }
}

