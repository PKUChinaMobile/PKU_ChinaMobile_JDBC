package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;
import com.pku.cis.PKU_ChinaMobile_JDBC.Server.PermissionManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class DataSourceManagerFrame extends JFrame {

    public static int rowCount;
    public static int columnCount = 5;
    public static String data[][];
    public static String head[] = {"UID","Name","Type","IP","Port"};
    public static JButton glbbutton;
    public static JButton glbbutton_1;
    public static JButton glbbutton_2;
    public static MyJTable glbtable;

    static final int FHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    static final int FWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DataSourceManagerFrame frame = new DataSourceManagerFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void fetchData()
    {
        rowCount = 3;
        data = new String[rowCount][];
        data[0] = new String[columnCount];
        data[0][0] = "a";
        data[0][1] = "b";
        data[0][2] = "a";
        data[0][3] = "b";
        data[0][4] = "a";
        data[1] = new String[columnCount];
        data[1][0] = "a";
        data[1][1] = "b";
        data[1][2] = "a";
        data[1][3] = "b";
        data[1][4] = "a";

    }
    public static MyJTable updateTable()
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
    /**
     * Create the frame.
     */
    public DataSourceManagerFrame() {

        setTitle("透明网管系统——数据源管理");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 300);
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

        /*添加用户按钮*/
        glbbutton = new JButton("添加");
        glbbutton.addActionListener(new Adapter_DataSourceManagerFrame());
        glbbutton.setBounds(6, 16, 135, 30);
        panel_2.add(glbbutton);


        /*编辑用户按钮*/
        glbbutton_1 = new JButton("编辑");
        glbbutton_1.setBounds(6, 58, 135, 30);
        glbbutton_1.addActionListener(new Adapter2_DataSourceManagerFrame());
        panel_2.add(glbbutton_1);

        /*删除操作*/
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
                            JOptionPane.showMessageDialog(null,"删除成功","提示", JOptionPane.PLAIN_MESSAGE);/*
                        else
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
        getContentPane().add(splitPane);
    }
}
class Adapter_DataSourceManagerFrame implements ActionListener
{
    JFrame f;
    JTextField textField;
    JTextField textField_1;
    JTextField textField_2;
    JTextField textField_3;
    public void actionPerformed(ActionEvent e) {
        JFrame f = new JFrame();
        DataSourceManagerFrame.glbbutton.setEnabled(false);
        DataSourceManagerFrame.glbbutton_1.setEnabled(false);
        DataSourceManagerFrame.glbbutton_2.setEnabled(false);
        DataSourceManagerFrame.glbtable.setEnabled(false);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataSourceManagerFrame.glbbutton.setEnabled(true);
                DataSourceManagerFrame.glbbutton_1.setEnabled(true);
                DataSourceManagerFrame.glbbutton_2.setEnabled(true);
                DataSourceManagerFrame.glbtable.updateUI(); //更新表
                DataSourceManagerFrame.glbtable.setEnabled(true);
                super.windowClosing(e);
            }
        });
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setBounds(100, 100, 329, 234);
        f.setLocation(DataSourceManagerFrame.FWidth / 3, DataSourceManagerFrame.FHeight / 3);
        f.setTitle("添加数据源");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        f.setContentPane(contentPane);
        JLabel label = new JLabel("Name：");
        label.setBounds(22, 28, 90, 16);
        contentPane.add(label);
        textField = new JTextField();
        textField.setBounds(100, 22, 194, 28);
        contentPane.add(textField);
        textField.setColumns(10);
        JLabel label_1 = new JLabel("Type：");
        label_1.setBounds(22, 62, 90, 16);
        contentPane.add(label_1);
        textField_1 = new JTextField();
        textField.setColumns(10);
        textField_1.setBounds(100, 53, 194, 28);
        contentPane.add(textField_1);
        JLabel label_2 = new JLabel("IP：");
        label_2.setBounds(22, 90, 90, 16);
        contentPane.add(label_2);
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(100, 84, 194, 28);
        contentPane.add(textField_2);
        JLabel label_3 = new JLabel("port：");
        label_3.setBounds(22, 122, 77, 16);
        contentPane.add(label_3);
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(100, 115, 194, 28);
        contentPane.add(textField_3);

        JPanel panel = new JPanel();
        panel.setBounds(16, 16, 297, 138);
        panel.setBorder(BorderFactory.createEtchedBorder());
        contentPane.add(panel);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(188, 164, 117, 29);
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataSourceManagerFrame.glbbutton.setEnabled(true);
                DataSourceManagerFrame.glbbutton_1.setEnabled(true);
                DataSourceManagerFrame.glbbutton_2.setEnabled(true);
                try {
                    DataSourceManagerFrame.fetchData();
                }catch(Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                }
                DataSourceManagerFrame.glbtable.setModel(new DefaultTableModel(DataSourceManagerFrame.data,DataSourceManagerFrame.head));
                DataSourceManagerFrame.glbtable.updateUI(); //更新表
                DataSourceManagerFrame.glbtable.setEnabled(true);
                f.dispose();
            }
        });
        contentPane.add(button_1);

        JButton button = new JButton("确认");
        button.setBounds(26, 164, 117, 29);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = textField.getText();
                String name = textField_1.getText();
                String ip = textField_2.getText();
                String port = textField_3.getText();

                if(true) {
                    JOptionPane.showMessageDialog(null, "", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPane.add(button);
        f.setVisible(true);
    }
}
class Adapter2_DataSourceManagerFrame implements ActionListener
{
    String type = "a";
    String name = "b";
    String ip = "c";
    String port = "d";
    JFrame f;
    JTextField textField;
    JTextField textField_1;
    JTextField textField_2;
    JTextField textField_3;
    public void actionPerformed(ActionEvent e) {
        f = new JFrame();
        DataSourceManagerFrame.glbbutton.setEnabled(false);
        DataSourceManagerFrame.glbbutton_1.setEnabled(false);
        DataSourceManagerFrame.glbbutton_2.setEnabled(false);
        DataSourceManagerFrame.glbtable.setEnabled(false);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataSourceManagerFrame.glbbutton.setEnabled(true);
                DataSourceManagerFrame.glbbutton_1.setEnabled(true);
                DataSourceManagerFrame.glbbutton_2.setEnabled(true);
                DataSourceManagerFrame.glbtable.updateUI(); //更新表
                DataSourceManagerFrame.glbtable.setEnabled(true);
                super.windowClosing(e);
            }
        });
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setBounds(100, 100, 329, 234);
        f.setLocation(DataSourceManagerFrame.FWidth / 3, DataSourceManagerFrame.FHeight / 3);
        f.setTitle("添加数据源");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        f.setContentPane(contentPane);
        JLabel label = new JLabel("Name：");
        label.setBounds(22, 28, 90, 16);
        contentPane.add(label);
        textField = new JTextField();
        textField.setText(name);
        textField.setBounds(100, 22, 194, 28);
        contentPane.add(textField);
        textField.setColumns(10);
        JLabel label_1 = new JLabel("Type：");
        label_1.setBounds(22, 62, 90, 16);
        contentPane.add(label_1);
        textField_1 = new JTextField();
        textField.setColumns(10);
        textField_1.setBounds(100, 53, 194, 28);
        textField_1.setText(type);
        contentPane.add(textField_1);
        JLabel label_2 = new JLabel("IP：");
        label_2.setBounds(22, 90, 90, 16);
        contentPane.add(label_2);
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(100, 84, 194, 28);
        textField_2.setText(ip);
        contentPane.add(textField_2);
        JLabel label_3 = new JLabel("port：");
        label_3.setBounds(22, 122, 77, 16);
        contentPane.add(label_3);
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(100, 115, 194, 28);
        textField_3.setText(port);
        contentPane.add(textField_3);

        JPanel panel = new JPanel();
        panel.setBounds(16, 16, 297, 138);
        panel.setBorder(BorderFactory.createEtchedBorder());
        contentPane.add(panel);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(188, 164, 117, 29);
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataSourceManagerFrame.glbbutton.setEnabled(true);
                DataSourceManagerFrame.glbbutton_1.setEnabled(true);
                DataSourceManagerFrame.glbbutton_2.setEnabled(true);
                try {
                    DataSourceManagerFrame.fetchData();
                }catch(Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                }
                DataSourceManagerFrame.glbtable.setModel(new DefaultTableModel(DataSourceManagerFrame.data,DataSourceManagerFrame.head));
                DataSourceManagerFrame.glbtable.updateUI(); //更新表
                DataSourceManagerFrame.glbtable.setEnabled(true);
                f.dispose();
            }
        });
        contentPane.add(button_1);

        JButton button = new JButton("确认");
        button.setBounds(26, 164, 117, 29);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = textField.getText();
                String name = textField_1.getText();
                String ip = textField_2.getText();
                String port = textField_3.getText();

                if(true) {
                    JOptionPane.showMessageDialog(null, "", "提示", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        contentPane.add(button);
        f.setVisible(true);
    }
}
