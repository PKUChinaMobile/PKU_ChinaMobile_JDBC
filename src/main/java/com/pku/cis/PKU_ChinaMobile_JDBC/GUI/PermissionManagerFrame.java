package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.PKUPermissionManager;
import com.pku.cis.PKU_ChinaMobile_JDBC.Interface.P_Users;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class PermissionManagerFrame extends JFrame {

    public static String userInfo[][];
    public static String head[] = {"用户","权限"};
    public static PKUPermissionManager pm;
    public static int userCount;
    public static int columnCount = 2;
    public static JButton glbbutton;
    public static JButton glbbutton_1;
    public static JButton glbbutton_2;
    public static MyJTable glbtable;
    public static JScrollPane panel;
    public static JSplitPane splitPane;

    static final int FHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    static final int FWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PermissionManagerFrame frame = new PermissionManagerFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void fetchData() throws Exception
    {
        userCount = pm.getUserCount();
        P_Users[] usr = (P_Users[])pm.getUsers();
        userInfo = new String[userCount][];
        for(int i = 0; i < userCount; i++)
        {
            userInfo[i] = new String[columnCount];
            userInfo[i][0] = usr[i].userName;
            if(usr[i].permission == 1)
                userInfo[i][1] = "普通用户";
            else
                userInfo[i][1] = "管理员";
        }
    }
    public static MyJTable updateTable() throws Exception
    {
        fetchData();
        MyJTable t =  new MyJTable(new DefaultTableModel(userInfo,head));
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
    public PermissionManagerFrame() {
        try {
            pm = new PKUPermissionManager();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "连接失败", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
        setTitle("透明网关系统——权限管理");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 484, 300);
        try{
        glbtable = updateTable();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
        panel = new JScrollPane(glbtable);
        panel.getViewport().setBackground(Color.WHITE);

        JPanel panel_1 = new JPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, panel, panel_1);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(BorderFactory.createTitledBorder("Operation"));
        panel_2.setBounds(9, 6, 146, 138);
        panel_1.add(panel_2);
        panel_2.setLayout(null);

        /*添加用户按钮*/
        glbbutton = new JButton("添加");
        glbbutton.addActionListener(new Adapter_PermissionManagerFrame());
        glbbutton.setBounds(6, 16, 135, 30);
        panel_2.add(glbbutton);


        /*编辑用户按钮*/
        glbbutton_1 = new JButton("编辑");
        glbbutton_1.setBounds(6, 58, 135, 30);
        glbbutton_1.addActionListener(new Adapter2_PermissionManagerFrame());
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
                String userName = userInfo[glbtable.getSelectedRow()][0];
                int result = JOptionPane.showConfirmDialog(null, "确定删除该用户？", "提示", JOptionPane.YES_NO_OPTION);
                if(result == 0)
                {
                    try {
                        if(pm.remove(userName))
                            JOptionPane.showMessageDialog(null,"删除成功","提示", JOptionPane.PLAIN_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(null,"删除失败","删除失败", JOptionPane.ERROR_MESSAGE);
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
                glbtable.setModel(new DefaultTableModel(userInfo,head));
                glbtable.updateUI(); //更新表
                glbtable.setEnabled(true);
            }
        });
        panel_2.add(glbbutton_2);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(1);
        splitPane.setDividerSize(0);
        getContentPane().add(splitPane);
    }
}
class Adapter_PermissionManagerFrame implements ActionListener {
    JFrame f;
    JTextField textField;
    JPasswordField textField_1;
    JPasswordField textField_2;
    JRadioButton radioButton;
    public void actionPerformed(ActionEvent e){
        f = new JFrame();
        PermissionManagerFrame.glbbutton.setEnabled(false);
        PermissionManagerFrame.glbbutton_1.setEnabled(false);
        PermissionManagerFrame.glbbutton_2.setEnabled(false);
        PermissionManagerFrame.glbtable.setEnabled(false);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PermissionManagerFrame.glbbutton.setEnabled(true);
                PermissionManagerFrame.glbbutton_1.setEnabled(true);
                PermissionManagerFrame.glbbutton_2.setEnabled(true);
                try {
                    PermissionManagerFrame.glbtable = PermissionManagerFrame.updateTable();
                }catch(Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                }
                PermissionManagerFrame.glbtable.updateUI(); //更新表
                PermissionManagerFrame.glbtable.setEnabled(true);
                f.dispose();
                super.windowClosing(e);
            }
        });
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setBounds(100, 100, 329, 234);
        f.setLocation(PermissionManagerFrame.FWidth / 3, PermissionManagerFrame.FHeight / 3);
        f.setTitle("添加用户");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        f.setContentPane(contentPane);
        JLabel label = new JLabel("用户名：");
        label.setBounds(22, 28, 90, 16);
        contentPane.add(label);
        textField = new JTextField();
        textField.setBounds(100, 22, 194, 28);
        contentPane.add(textField);
        textField.setColumns(10);
        JLabel label_1 = new JLabel("密码：");
        label_1.setBounds(22, 62, 90, 16);
        contentPane.add(label_1);
        textField_1 = new JPasswordField();
        textField_1.setColumns(10);
        textField_1.setBounds(100, 53, 194, 28);
        contentPane.add(textField_1);
        JLabel label_2 = new JLabel("确认密码：");
        label_2.setBounds(22, 90, 90, 16);
        contentPane.add(label_2);
        textField_2 = new JPasswordField();
        textField_2.setColumns(10);
        textField_2.setBounds(100, 84, 194, 28);
        contentPane.add(textField_2);
        JLabel label_3 = new JLabel("权限：");
        label_3.setBounds(22, 122, 77, 16);
        contentPane.add(label_3);
        radioButton = new JRadioButton("普通用户");
        radioButton.setBounds(87, 118, 103, 23);
        contentPane.add(radioButton);
        radioButton.setSelected(true);
        JRadioButton radioButton_1 = new JRadioButton("管理员");
        radioButton_1.setBounds(188, 118, 117, 23);
        contentPane.add(radioButton_1);
        ButtonGroup group = new ButtonGroup();// 创建单选按钮组
        group.add(radioButton);// 将radioButton增加到单选按钮组中
        group.add(radioButton_1);


        JPanel panel = new JPanel();
        panel.setBounds(16, 16, 297, 138);
        panel.setBorder(BorderFactory.createEtchedBorder());
        contentPane.add(panel);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(188, 164, 117, 29);
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PermissionManagerFrame.glbbutton.setEnabled(true);
                PermissionManagerFrame.glbbutton_1.setEnabled(true);
                PermissionManagerFrame.glbbutton_2.setEnabled(true);
                PermissionManagerFrame.glbtable.setEnabled(true);
                f.dispose();
            }
        });
        contentPane.add(button_1);

        JButton button = new JButton("确认");
        button.setBounds(26, 164, 117, 29);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String psw1 = new String(textField_1.getPassword());
                String psw2 = new String(textField_2.getPassword());
                System.out.println(psw1);
                if(!psw1.equals(psw2)) {
                    JOptionPane.showMessageDialog(null, "两次输入密码不一致!", "添加失败", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    String userName = textField.getText();
                    int permission;
                    if(radioButton.isSelected())
                        permission = 1;
                    else
                        permission = 0;
                    try {
                        if(PermissionManagerFrame.pm.insert(userName, permission, psw1))
                        {
                            JOptionPane.showMessageDialog(null,"添加成功","添加成功", JOptionPane.PLAIN_MESSAGE);
                            PermissionManagerFrame.glbbutton.setEnabled(true);
                            PermissionManagerFrame.glbbutton_1.setEnabled(true);
                            PermissionManagerFrame.glbbutton_2.setEnabled(true);
                            try {
                                PermissionManagerFrame.fetchData();
                            }catch(Exception e1) {
                                JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                            }
                            PermissionManagerFrame.glbtable.setModel(new DefaultTableModel(PermissionManagerFrame.userInfo, PermissionManagerFrame.head));
                            PermissionManagerFrame.glbtable.updateUI(); //更新表
                            PermissionManagerFrame.glbtable.setEnabled(true);
                            f.dispose();
                        }
                        else
                            JOptionPane.showMessageDialog(null,"添加失败","添加失败", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null,e1.getMessage(),"添加失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        contentPane.add(button);
        f.setVisible(true);

    }
}
class Adapter2_PermissionManagerFrame implements ActionListener {
    JFrame f;
    JTextField textField;
    JRadioButton radioButton;
    String userName;
    public void actionPerformed(ActionEvent e) {
        f = new JFrame();
        PermissionManagerFrame.glbbutton.setEnabled(false);
        PermissionManagerFrame.glbbutton_1.setEnabled(false);
        PermissionManagerFrame.glbbutton_2.setEnabled(false);
        PermissionManagerFrame.glbtable.setEnabled(false);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PermissionManagerFrame.glbbutton.setEnabled(true);
                PermissionManagerFrame.glbbutton_1.setEnabled(true);
                PermissionManagerFrame.glbbutton_2.setEnabled(true);
                PermissionManagerFrame.glbtable.updateUI(); //更新表
                PermissionManagerFrame.glbtable.setEnabled(true);
                super.windowClosing(e);
            }
        });
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setBounds(100, 100, 327, 166);
        f.setLocation( PermissionManagerFrame.FWidth / 3,  PermissionManagerFrame.FHeight / 3);
        f.setTitle("编辑用户");
        f.setVisible(true);
        userName =  PermissionManagerFrame.userInfo[ PermissionManagerFrame.glbtable.getSelectedRow()][0];

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        f.setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(16, 14, 285, 73);
        panel.setBorder(BorderFactory.createEtchedBorder());
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel label = new JLabel("用户名：");
        label.setBounds(17, 11, 120, 15);
        panel.add(label);

        textField = new JTextField();
        textField.setBounds(70, 8, 190, 21);
        textField.setText(userName);
        textField.setEditable(false);
        panel.add(textField);
        textField.setColumns(10);
        JLabel label_3 = new JLabel("权限：");
        label_3.setBounds(17, 39, 101, 15);
        panel.add(label_3);
        JRadioButton radioButton_1 = new JRadioButton("管理员");
        radioButton_1.setBounds(176, 35, 101, 23);
        panel.add(radioButton_1);
        radioButton = new JRadioButton("普通用户");
        radioButton.setSelected(true);
        radioButton.setBounds(70, 35, 101, 23);
        panel.add(radioButton);

        ButtonGroup group = new ButtonGroup();// 创建单选按钮组
        group.add(radioButton);// 将radioButton增加到单选按钮组中
        group.add(radioButton_1);

        JButton button_1 = new JButton("取消");
        button_1.setBounds(170, 97, 117, 29);
        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PermissionManagerFrame.glbbutton.setEnabled(true);
                PermissionManagerFrame.glbbutton_1.setEnabled(true);
                PermissionManagerFrame.glbbutton_2.setEnabled(true);
                PermissionManagerFrame.glbtable.updateUI(); //更新表
                PermissionManagerFrame.glbtable.setEnabled(true);
                f.dispose();
            }
        });
        contentPane.add(button_1);

        JButton button = new JButton("确认");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int permission;
                if(radioButton.isSelected())
                    permission = 1;
                else
                    permission = 0;
                try {
                    if( PermissionManagerFrame.pm.editPermission(userName, permission))
                    {
                        JOptionPane.showMessageDialog(null,"更改成功","更改成功", JOptionPane.PLAIN_MESSAGE);
                        PermissionManagerFrame.glbbutton.setEnabled(true);
                        PermissionManagerFrame.glbbutton_1.setEnabled(true);
                        PermissionManagerFrame.glbbutton_2.setEnabled(true);
                        try {
                            PermissionManagerFrame.fetchData();
                        }catch(Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "获取数据失败", JOptionPane.ERROR_MESSAGE);
                        }
                        PermissionManagerFrame.glbtable.setModel(new DefaultTableModel( PermissionManagerFrame.userInfo, PermissionManagerFrame.head));
                        PermissionManagerFrame.glbtable.updateUI(); //更新表
                        PermissionManagerFrame.glbtable.setEnabled(true);
                        f.dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(null,"添加失败","添加失败", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null,e1.getMessage(),"添加失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button.setBounds(26, 97, 117, 29);
        contentPane.add(button);
    }

}