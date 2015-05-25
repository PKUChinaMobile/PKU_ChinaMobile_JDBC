package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class MetaDataManagerFrame extends JFrame {



    /*标签页1的组件*/
    public static int rowCount;
    public static int columnCount = 4;
    public static String data[][];
    public static String head[] = {"列1","列2","列3","列4"};
    public static JButton glbbutton;
    public static JButton glbbutton_1;
    public static JButton glbbutton_2;
    public static MyJTable glbtable;

    /*标签页2的组件*/
    public static int rowCount2;
    public static int columnCount2 = 6;
    public static String data2[][];
    public static String head2[] = {"UID","全局列名","LCID","Max","Min","Location"};
    public static JButton glbbutton2;
    public static JButton glbbutton2_1;
    public static JButton glbbutton2_2;
    public static MyJTable glbtable2;


    static final int FHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    static final int FWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MetaDataManagerFrame frame = new MetaDataManagerFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /*获取表格数据*/
    public void fetchData()
    {
        rowCount = 3;
        data = new String[rowCount][];
        data[0] = new String[columnCount];
        data[0][0] = "a";
        data[0][1] = "b";
        data[0][2] = "c";
        data[0][3] = "d";
        data[1] = new String[columnCount];
        data[1][0] = "a";
        data[1][1] = "b";
        data[1][2] = "c";
        data[1][3] = "d";
        data[2] = new String[columnCount];
        data[2][0] = "a";
        data[2][1] = "b";
        data[2][2] = "c";
        data[2][3] = "d";

    }
    /*更新表格*/
    public MyJTable updateTable()
    {
        fetchData();
        MyJTable t =  new MyJTable(new DefaultTableModel(data,head));
        t.setRowSelectionInterval(0, 0);
        t.setBackground(Color.WHITE);
        t.setBorder(null);
        t.setRowHeight(30);
        t.setEnabled(true);

        return t;
    }
    /*获取表格数据*/
    public void fetchData2()
    {
        rowCount2 = 2;
        data2 = new String[rowCount2][];
        data2[0] = new String[columnCount2];
        data2[0][0] = "a";
        data2[0][1] = "b";
        data2[0][2] = "c";
        data2[0][3] = "d";
        data2[0][4] = "c";
        data2[0][5] = "d";
        data2[1] = new String[columnCount2];
        data2[1][0] = "a";
        data2[1][1] = "b";
        data2[1][2] = "c";
        data2[1][3] = "d";
        data2[1][4] = "c";
        data2[1][5] = "d";


    }
    /*更新表格*/
    public MyJTable updateTable2()
    {
        fetchData2();
        MyJTable t =  new MyJTable(new DefaultTableModel(data2,head2));
        t.setRowSelectionInterval(0, 0);
        t.setBackground(Color.WHITE);
        t.setBorder(null);
        t.setRowHeight(30);
        t.setEnabled(true);

        return t;
    }
    /**
     * Create the frame.
     */
    public MetaDataManagerFrame() {

        setTitle("透明网管系统——权限管理");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 300);
        getContentPane().setLayout(new GridLayout(1,1));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);//标签页

        /*标签页1——全局映射*/
        glbtable = updateTable();
        JScrollPane panel = new JScrollPane(glbtable);
        panel.getViewport().setBackground(Color.WHITE);

        JPanel panel_1 = new JPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, panel, panel_1);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(BorderFactory.createTitledBorder("Operation"));
        panel_2.setBounds(9, 6, 146, 138);
        panel_1.add(panel_2);
        panel_2.setLayout(null);

        /*添加全局模式按钮*/
        glbbutton = new JButton("添加");
        glbbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame();
                glbbutton.setEnabled(false);
                glbbutton_1.setEnabled(false);
                glbbutton_2.setEnabled(false);
                glbtable.setEnabled(false);
                f.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        glbbutton.setEnabled(true);
                        glbbutton_1.setEnabled(true);
                        glbbutton_2.setEnabled(true);
                        try {
                            fetchData();
                        }catch(Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        glbtable.setModel(new DefaultTableModel(data,head));
                        glbtable.updateUI(); //更新表
                        glbtable.setEnabled(true);
                        super.windowClosing(e);
                    }
                });
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setBounds(100, 100, 329, 234);
                f.setLocation(FWidth / 3, FHeight / 3);
                f.setTitle("添加全局模式");

                JPanel contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                contentPane.setLayout(null);
                f.setContentPane(contentPane);
                JLabel label = new JLabel("全局表名：");
                label.setBounds(22, 28, 90, 16);
                contentPane.add(label);
                JTextField textField = new JTextField();
                textField.setBounds(100, 22, 194, 28);
                contentPane.add(textField);
                textField.setColumns(10);
                JLabel label_1 = new JLabel("列名：");
                label_1.setBounds(22, 62, 90, 16);
                contentPane.add(label_1);
                JTextField textField_1 = new JTextField();
                textField.setColumns(10);
                textField_1.setBounds(100, 53, 194, 28);
                contentPane.add(textField_1);
                JLabel label_2 = new JLabel("列类型：");
                label_2.setBounds(22, 90, 90, 16);
                contentPane.add(label_2);
                JTextField textField_2 = new JTextField();
                textField_2.setColumns(10);
                textField_2.setBounds(100, 84, 194, 28);
                contentPane.add(textField_2);


                JPanel panel = new JPanel();
                panel.setBounds(16, 16, 297, 106);
                panel.setBorder(BorderFactory.createEtchedBorder());
                contentPane.add(panel);

                JButton button_1 = new JButton("取消");
                button_1.setBounds(188, 132, 117, 29);
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        glbbutton.setEnabled(true);
                        glbbutton_1.setEnabled(true);
                        glbbutton_2.setEnabled(true);
                        glbtable.setEnabled(true);
                        f.dispose();
                    }
                });
                contentPane.add(button_1);

                JButton button = new JButton("确认");
                button.setBounds(26, 132, 117, 29);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String glbTableName = textField.getText();
                        String columnName = textField_1.getText();
                        String columnType = textField_2.getText();

                        if(true) {
                            JOptionPane.showMessageDialog(null, "", "提示", JOptionPane.ERROR_MESSAGE);
                        }/*
                        else
                        {
                            String userName = textField.getText();
                            int permission;
                            if(radioButton.isSelected())
                                permission = 1;
                            else
                                permission = 0;
                            try {
                                if(pm.insert(userName, permission, psw1))
                                {
                                    JOptionPane.showMessageDialog(null,"添加成功","添加成功", JOptionPane.ERROR_MESSAGE);
                                    glbbutton.setEnabled(true);
                                    glbbutton_1.setEnabled(true);
                                    glbbutton_2.setEnabled(true);
                                    glbtable.updateUI(); //更新表
                                    glbtable.setEnabled(true);
                                    f.dispose();
                                }
                                else
                                    JOptionPane.showMessageDialog(null,"添加失败","添加失败", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null,e1.getMessage(),"添加失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }*/
                    }
                });
                contentPane.add(button);
                f.setVisible(true);
            }
        });
        glbbutton.setBounds(6, 16, 135, 30);
        panel_2.add(glbbutton);


        /*编辑全局模式按钮*/
        glbbutton_1 = new JButton("编辑");
        glbbutton_1.setBounds(6, 58, 135, 30);
        glbbutton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String glbTableName = "s";
                String columnName = "t";
                String columnType = "h";

                JFrame f = new JFrame();
                glbbutton.setEnabled(false);
                glbbutton_1.setEnabled(false);
                glbbutton_2.setEnabled(false);
                glbtable.setEnabled(false);
                f.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        glbbutton.setEnabled(true);
                        glbbutton_1.setEnabled(true);
                        glbbutton_2.setEnabled(true);
                        try {
                            fetchData();
                        }catch(Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        glbtable.setModel(new DefaultTableModel(data,head));
                        glbtable.updateUI(); //更新表
                        glbtable.setEnabled(true);
                        super.windowClosing(e);
                    }
                });
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setBounds(100, 100, 329, 234);
                f.setLocation(FWidth / 3, FHeight / 3);
                f.setTitle("编辑全局模式");

                JPanel contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                contentPane.setLayout(null);
                f.setContentPane(contentPane);
                JLabel label = new JLabel("全局表名：");
                label.setBounds(22, 28, 90, 16);
                contentPane.add(label);
                JTextField textField = new JTextField();
                textField.setBounds(100, 22, 194, 28);
                textField.setText(glbTableName);
                contentPane.add(textField);
                textField.setColumns(10);
                JLabel label_1 = new JLabel("列名：");
                label_1.setBounds(22, 62, 90, 16);
                contentPane.add(label_1);
                JTextField textField_1 = new JTextField();
                textField.setColumns(10);
                textField_1.setBounds(100, 53, 194, 28);
                textField_1.setText(columnName);
                contentPane.add(textField_1);
                JLabel label_2 = new JLabel("列类型：");
                label_2.setBounds(22, 90, 90, 16);
                contentPane.add(label_2);
                JTextField textField_2 = new JTextField();
                textField_2.setColumns(10);
                textField_2.setBounds(100, 84, 194, 28);
                textField_2.setText(columnType);
                contentPane.add(textField_2);


                JPanel panel = new JPanel();
                panel.setBounds(16, 16, 297, 106);
                panel.setBorder(BorderFactory.createEtchedBorder());
                contentPane.add(panel);

                JButton button_1 = new JButton("取消");
                button_1.setBounds(188, 132, 117, 29);
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        glbbutton.setEnabled(true);
                        glbbutton_1.setEnabled(true);
                        glbbutton_2.setEnabled(true);
                        glbtable.setEnabled(true);
                        f.dispose();
                    }
                });
                contentPane.add(button_1);

                JButton button = new JButton("确认");
                button.setBounds(26, 132, 117, 29);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String glbTableName = textField.getText();
                        String columnName = textField_1.getText();
                        String columnType = textField_2.getText();

                        if(true) {
                            JOptionPane.showMessageDialog(null, "", "提示", JOptionPane.ERROR_MESSAGE);
                        }/*
                        else
                        {
                            String userName = textField.getText();
                            int permission;
                            if(radioButton.isSelected())
                                permission = 1;
                            else
                                permission = 0;
                            try {
                                if(pm.insert(userName, permission, psw1))
                                {
                                    JOptionPane.showMessageDialog(null,"添加成功","添加成功", JOptionPane.ERROR_MESSAGE);
                                    glbbutton.setEnabled(true);
                                    glbbutton_1.setEnabled(true);
                                    glbbutton_2.setEnabled(true);
                                    glbtable.updateUI(); //更新表
                                    glbtable.setEnabled(true);
                                    f.dispose();
                                }
                                else
                                    JOptionPane.showMessageDialog(null,"添加失败","添加失败", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null,e1.getMessage(),"添加失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }*/
                    }
                });
                contentPane.add(button);
                f.setVisible(true);
            }
        });
        panel_2.add(glbbutton_1);

        /*删除全局模式按钮*/
        glbbutton_2 = new JButton("删除");
        glbbutton_2.setBounds(6, 98, 135, 30);
        glbbutton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                glbbutton.setEnabled(false);
                glbbutton_1.setEnabled(false);
                glbbutton_2.setEnabled(false);
                glbtable.setEnabled(false);
                String userName = data[glbtable.getSelectedRowCount()][0];
                int result = JOptionPane.showConfirmDialog(null, "确定删除该用户？", "提示", JOptionPane.YES_NO_OPTION);
                if(result == 0)
                {
                    try {
                        if(true)
                            JOptionPane.showMessageDialog(null,"删除成功","提示", JOptionPane.PLAIN_MESSAGE);
                        /*else
                            JOptionPane.showMessageDialog(null,"删除失败","删除失败", JOptionPane.ERROR_MESSAGE);*/
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null,e1.getMessage(),"删除失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
                glbbutton.setEnabled(true);
                glbbutton_1.setEnabled(true);
                glbbutton_2.setEnabled(true);
                try {
                    fetchData();
                }catch(Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                }
                glbtable.setModel(new DefaultTableModel(data,head));
                glbtable.updateUI(); //更新表
                glbtable.setEnabled(true);
            }
        });
        panel_2.add(glbbutton_2);
        splitPane.setDividerLocation(366);
        splitPane.setResizeWeight(1);
        splitPane.setDividerSize(0);

        /*标签页2——映射关系*/
        glbtable2 = updateTable2();
        JScrollPane panel2 = new JScrollPane(glbtable2);
        panel2.getViewport().setBackground(Color.WHITE);

        JPanel panel2_1 = new JPanel();
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, panel2, panel2_1);
        panel2_1.setLayout(null);

        JPanel panel2_2 = new JPanel();
        panel2_2.setBorder(BorderFactory.createTitledBorder("Operation"));
        panel2_2.setBounds(9, 6, 146, 138);
        panel2_1.add(panel2_2);
        panel2_2.setLayout(null);

        /*添加按钮*/
        glbbutton2 = new JButton("添加");
        glbbutton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame();
                glbbutton2.setEnabled(false);
                glbbutton2_1.setEnabled(false);
                glbbutton2_2.setEnabled(false);
                glbtable2.setEnabled(false);
                f.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        glbbutton2.setEnabled(true);
                        glbbutton2_1.setEnabled(true);
                        glbbutton2_2.setEnabled(true);
                        try {
                            fetchData2();
                        }catch(Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        glbtable2.setModel(new DefaultTableModel(data,head));
                        glbtable2.updateUI(); //更新表
                        glbtable2.setEnabled(true);
                        super.windowClosing(e);
                    }
                });
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setBounds(100, 100, 329, 234);
                f.setLocation(FWidth / 3, FHeight / 3);
                f.setTitle("添加全局模式");

                JPanel contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                contentPane.setLayout(null);
                f.setContentPane(contentPane);
                JLabel label = new JLabel("全局表名：");
                label.setBounds(22, 28, 90, 16);
                contentPane.add(label);
                JTextField textField = new JTextField();
                textField.setBounds(100, 22, 194, 28);
                contentPane.add(textField);
                textField.setColumns(10);
                JLabel label_1 = new JLabel("列名：");
                label_1.setBounds(22, 62, 90, 16);
                contentPane.add(label_1);
                JTextField textField_1 = new JTextField();
                textField.setColumns(10);
                textField_1.setBounds(100, 53, 194, 28);
                contentPane.add(textField_1);
                JLabel label_2 = new JLabel("列类型：");
                label_2.setBounds(22, 90, 90, 16);
                contentPane.add(label_2);
                JTextField textField_2 = new JTextField();
                textField_2.setColumns(10);
                textField_2.setBounds(100, 84, 194, 28);
                contentPane.add(textField_2);


                JPanel panel = new JPanel();
                panel.setBounds(16, 16, 297, 106);
                panel.setBorder(BorderFactory.createEtchedBorder());
                contentPane.add(panel);

                JButton button_1 = new JButton("取消");
                button_1.setBounds(188, 132, 117, 29);
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        glbbutton2.setEnabled(true);
                        glbbutton2_1.setEnabled(true);
                        glbbutton2_2.setEnabled(true);
                        glbtable2.setEnabled(true);
                        f.dispose();
                    }
                });
                contentPane.add(button_1);

                JButton button = new JButton("确认");
                button.setBounds(26, 132, 117, 29);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String glbTableName = textField.getText();
                        String columnName = textField_1.getText();
                        String columnType = textField_2.getText();

                        if(true) {
                            JOptionPane.showMessageDialog(null, "", "提示", JOptionPane.ERROR_MESSAGE);
                        }/*
                        else
                        {
                            String userName = textField.getText();
                            int permission;
                            if(radioButton.isSelected())
                                permission = 1;
                            else
                                permission = 0;
                            try {
                                if(pm.insert(userName, permission, psw1))
                                {
                                    JOptionPane.showMessageDialog(null,"添加成功","添加成功", JOptionPane.ERROR_MESSAGE);
                                    glbbutton.setEnabled(true);
                                    glbbutton_1.setEnabled(true);
                                    glbbutton_2.setEnabled(true);
                                    glbtable.updateUI(); //更新表
                                    glbtable.setEnabled(true);
                                    f.dispose();
                                }
                                else
                                    JOptionPane.showMessageDialog(null,"添加失败","添加失败", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null,e1.getMessage(),"添加失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }*/
                    }
                });
                contentPane.add(button);
                f.setVisible(true);
            }
        });
        glbbutton2.setBounds(6, 16, 135, 30);
        panel2_2.add(glbbutton2);


        /*编辑按钮*/
        glbbutton2_1 = new JButton("编辑");
        glbbutton2_1.setBounds(6, 58, 135, 30);
        glbbutton2_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String glbTableName = "s";
                String columnName = "t";
                String columnType = "h";

                JFrame f = new JFrame();
                glbbutton2.setEnabled(false);
                glbbutton2_1.setEnabled(false);
                glbbutton2_2.setEnabled(false);
                glbtable2.setEnabled(false);
                f.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        glbbutton2.setEnabled(true);
                        glbbutton2_1.setEnabled(true);
                        glbbutton2_2.setEnabled(true);
                        try {
                            fetchData2();
                        }catch(Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        glbtable2.setModel(new DefaultTableModel(data,head));
                        glbtable2.updateUI(); //更新表
                        glbtable2.setEnabled(true);
                        super.windowClosing(e);
                    }
                });
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setBounds(100, 100, 329, 234);
                f.setLocation(FWidth / 3, FHeight / 3);
                f.setTitle("编辑全局模式");

                JPanel contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                contentPane.setLayout(null);
                f.setContentPane(contentPane);
                JLabel label = new JLabel("全局表名：");
                label.setBounds(22, 28, 90, 16);
                contentPane.add(label);
                JTextField textField = new JTextField();
                textField.setBounds(100, 22, 194, 28);
                textField.setText(glbTableName);
                contentPane.add(textField);
                textField.setColumns(10);
                JLabel label_1 = new JLabel("列名：");
                label_1.setBounds(22, 62, 90, 16);
                contentPane.add(label_1);
                JTextField textField_1 = new JTextField();
                textField.setColumns(10);
                textField_1.setBounds(100, 53, 194, 28);
                textField_1.setText(columnName);
                contentPane.add(textField_1);
                JLabel label_2 = new JLabel("列类型：");
                label_2.setBounds(22, 90, 90, 16);
                contentPane.add(label_2);
                JTextField textField_2 = new JTextField();
                textField_2.setColumns(10);
                textField_2.setBounds(100, 84, 194, 28);
                textField_2.setText(columnType);
                contentPane.add(textField_2);


                JPanel panel = new JPanel();
                panel.setBounds(16, 16, 297, 106);
                panel.setBorder(BorderFactory.createEtchedBorder());
                contentPane.add(panel);

                JButton button_1 = new JButton("取消");
                button_1.setBounds(188, 132, 117, 29);
                button_1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        glbbutton2.setEnabled(true);
                        glbbutton2_1.setEnabled(true);
                        glbbutton2_2.setEnabled(true);
                        glbtable2.setEnabled(true);
                        f.dispose();
                    }
                });
                contentPane.add(button_1);

                JButton button = new JButton("确认");
                button.setBounds(26, 132, 117, 29);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String glbTableName = textField.getText();
                        String columnName = textField_1.getText();
                        String columnType = textField_2.getText();

                        if(true) {
                            JOptionPane.showMessageDialog(null, "", "提示", JOptionPane.ERROR_MESSAGE);
                        }/*
                        else
                        {
                            String userName = textField.getText();
                            int permission;
                            if(radioButton.isSelected())
                                permission = 1;
                            else
                                permission = 0;
                            try {
                                if(pm.insert(userName, permission, psw1))
                                {
                                    JOptionPane.showMessageDialog(null,"添加成功","添加成功", JOptionPane.ERROR_MESSAGE);
                                    glbbutton.setEnabled(true);
                                    glbbutton_1.setEnabled(true);
                                    glbbutton_2.setEnabled(true);
                                    glbtable.updateUI(); //更新表
                                    glbtable.setEnabled(true);
                                    f.dispose();
                                }
                                else
                                    JOptionPane.showMessageDialog(null,"添加失败","添加失败", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null,e1.getMessage(),"添加失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }*/
                    }
                });
                contentPane.add(button);
                f.setVisible(true);
            }
        });
        panel2_2.add(glbbutton2_1);

        /*删除按钮*/
        glbbutton2_2 = new JButton("删除");
        glbbutton2_2.setBounds(6, 98, 135, 30);
        glbbutton2_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                glbbutton2.setEnabled(false);
                glbbutton2_1.setEnabled(false);
                glbbutton2_2.setEnabled(false);
                glbtable2.setEnabled(false);
                String userName = data2[glbtable2.getSelectedRowCount()][0];
                int result = JOptionPane.showConfirmDialog(null, "确定删除该用户？", "提示", JOptionPane.YES_NO_OPTION);
                if(result == 0)
                {
                    try {
                        if(true)
                            JOptionPane.showMessageDialog(null,"删除成功","提示", JOptionPane.PLAIN_MESSAGE);
                        /*else
                            JOptionPane.showMessageDialog(null,"删除失败","删除失败", JOptionPane.ERROR_MESSAGE);*/
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null,e1.getMessage(),"删除失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
                glbbutton2.setEnabled(true);
                glbbutton2_1.setEnabled(true);
                glbbutton2_2.setEnabled(true);
                try {
                    fetchData2();
                }catch(Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                }
                glbtable2.setModel(new DefaultTableModel(data,head));
                glbtable2.updateUI(); //更新表
                glbtable2.setEnabled(true);
            }
        });
        panel2_2.add(glbbutton2_2);
        splitPane2.setDividerLocation(366);
        splitPane2.setResizeWeight(1);
        splitPane2.setDividerSize(0);

        tabbedPane.addTab("全局模式", splitPane);
        tabbedPane.addTab("映射关系", splitPane2);
        getContentPane().add(tabbedPane);
    }
}
