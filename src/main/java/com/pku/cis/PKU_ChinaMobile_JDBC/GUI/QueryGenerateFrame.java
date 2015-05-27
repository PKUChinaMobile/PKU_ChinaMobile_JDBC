package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

/**
 * Created by mrpen on 2015/5/23.
 * 查询生成器界面
 */
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;


public class QueryGenerateFrame extends JFrame {

    public BuildInFrame f;
    private JPanel contentPane;
    public static String tName; //当前选择表名
    public static int tableNo;//表数量
    public static int columnNo; //字段数
    public static String[] s;//字段名
    public static String[] s1 = {"--添加运算符--","=","+","-","*","/","(",")"}; //运算符
    public static String[] s2 = {"--添加逻辑符--","AND","OR","NOT"};
    public static String[] head = {"选择表"};
    public static String[][] tableName = {{"person"},{"users"},{"callRecords"},{"smsRecords"}};
    public static JTable table;
    public static JComboBox comboBox_6;
    public static JComboBox comboBox;
    public static JComboBox comboBox_3;
    public static JComboBox comboBox_4;
    public static JComboBox comboBox_1;
    public static JComboBox comboBox_2;
    public static JComboBox comboBox_5;
    public static JTextArea textArea_2; //显示字段文本框
    public static JTextArea textArea;//生成where条件文本框
    public static JTextArea textfield;//生成分组条件文本框
    public static JTextArea textField_1;//生成排序条件文本框
    public static JTextArea textArea_1; //查询语句面板


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    QueryGenerateFrame frame = new QueryGenerateFrame(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 加载对应表的列信息
     * */
    public String[] fetchColumn(int i)
    {
        String[] t;
        if(i == 0)
        {
            columnNo = 6;
            t = new String[columnNo + 1];
            t[0] = "--添加字段--";
            t[1] = "id";
            t[2] = "age";
            t[3] = "gender";
            t[4] = "work";
            t[5] = "home";
            t[6] = "location";
        }
        else
        {
            columnNo = 5;
            t = new String[columnNo + 1];
            t[0] = "--添加字段--";
            t[1] = "imsi";
            t[2] = "age";
            t[3] = "gender";
            t[4] = "work";
            t[5] = "home";
        }
        return t;
    }
    /**
     * 加载表数据
     * */
    public void fetchData()
    {
        tableNo = 4;
        tableName = new String[tableNo][];
        tableName[0]= new String[1];
        tableName[0][0] = "person";
        tableName[1]= new String[1];
        tableName[1][0] = "users";
        tableName[2]= new String[1];
        tableName[2][0] = "callRecords";
        tableName[3]= new String[1];
        tableName[3][0] = "smsRecords";
        tName = tableName[0][0];
        s = fetchColumn(0);
    }
    /**
     * Create the frame.
     */

    public QueryGenerateFrame(BuildInFrame _f) {
        f = _f;
        fetchData();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 600);
        setTitle("透明网关系统——预置查询窗口");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

		/*选择表面板*/
        table = new JTable(tableName,head);
        table.setRowSelectionInterval(0, 0);
        table.setBackground(Color.WHITE); //设置单元格背景为白色
        table.setBorder(null);
        table.setRowHeight(30);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //鼠标按下时bl=true释放时bl=false
                boolean bl = e.getValueIsAdjusting();
                if(bl)
                {
                    int i = table.getSelectedRow();
                    tName = tableName[i][0];
                    s = fetchColumn(i);
                    comboBox.removeAllItems();
                    comboBox_3.removeAllItems();
                    comboBox_4.removeAllItems();
                    comboBox_6.removeAllItems();
                    for(int j = 0; j < s.length; j++) //更换字段
                    {
                        comboBox.addItem(s[j]);
                        comboBox_3.addItem(s[j]);
                        comboBox_4.addItem(s[j]);
                        comboBox_6.addItem(s[j]);
                    }
                    comboBox_6.addItem("*");
                    comboBox_6.setSelectedIndex(0);
                    comboBox_4.setSelectedIndex(0);
                    comboBox_3.setSelectedIndex(0);
                    comboBox.setSelectedIndex(0);
                }
            }
        });
        JScrollPane panel_4 = new JScrollPane(table);
        panel_4.getViewport().setBackground(Color.WHITE); //设置表背景为白色
        GridBagConstraints gbc_panel_4 = new GridBagConstraints();
        gbc_panel_4.gridheight = 6;
        gbc_panel_4.gridwidth = 6;
        gbc_panel_4.insets = new Insets(0, 0, 5, 5);
        gbc_panel_4.fill = GridBagConstraints.BOTH;
        gbc_panel_4.gridx = 1;
        gbc_panel_4.gridy = 0;
        contentPane.add(panel_4, gbc_panel_4);

		/*添加显示字段面板*/
        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setOpaque(true);
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.gridheight = 6;
        gbc_panel.gridwidth = 11;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 7;
        gbc_panel.gridy = 0;
        contentPane.add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel lblNewLabel_4 = new JLabel("添加显示字段");
        lblNewLabel_4.setHorizontalTextPosition(SwingConstants.LEFT);
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_4.gridwidth = 12;
        gbc_lblNewLabel_4.gridx = 0;
        gbc_lblNewLabel_4.gridy = 0;
        panel.add(lblNewLabel_4, gbc_lblNewLabel_4);

        comboBox_6 = new JComboBox(s);
        comboBox_6.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((JComboBox)e.getSource()).getSelectedItem() != null) //更换字段时，删除所有选项会触发时间，此处防止引发null异常
                {
                    int index=((JComboBox)e.getSource()).getSelectedIndex();//获取到选中项的索引
                    if(index != 0)
                    {
                        String Item=((JComboBox)e.getSource()).getSelectedItem().toString();//获取到项
                        if(textArea_2.getText().equals(""))
                            textArea_2.append(Item);
                        else
                            textArea_2.append(","+Item);
                    }
                    comboBox_6.setSelectedIndex(0);
                }
            }

        });
        GridBagConstraints gbc_comboBox_6 = new GridBagConstraints();
        gbc_comboBox_6.gridwidth = 5;
        gbc_comboBox_6.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_6.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_6.gridx = 0;
        gbc_comboBox_6.gridy = 1;
        panel.add(comboBox_6, gbc_comboBox_6);

        JButton btnNewButton_7 = new JButton("清除");
        btnNewButton_7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea_2.setCaretPosition(0);
                textArea_2.setText("");
            }
        });
        GridBagConstraints gbc_btnNewButton_7 = new GridBagConstraints();
        gbc_btnNewButton_7.gridwidth = 5;
        gbc_btnNewButton_7.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_7.gridx = 5;
        gbc_btnNewButton_7.gridy = 1;
        panel.add(btnNewButton_7, gbc_btnNewButton_7);


        textArea_2 = new JTextArea();
        textArea_2.setFont(new Font("Times New Roman",10, 18));
        textArea_2.setLineWrap(true);
        textArea_2.setWrapStyleWord(true);
        GridBagConstraints gbc_textArea_2 = new GridBagConstraints();
        gbc_textArea_2.gridheight = 3;
        gbc_textArea_2.gridwidth = 12;
        gbc_textArea_2.fill = GridBagConstraints.BOTH;
        gbc_textArea_2.gridx = 0;
        gbc_textArea_2.gridy = 2;
        panel.add(new JScrollPane(textArea_2), gbc_textArea_2);


		/*生成条件语句面板*/
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_3.setOpaque(true);
        GridBagLayout gbl_panel_3 = new GridBagLayout();
        gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel_3.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel_3.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel_3.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        panel_3.setLayout(gbl_panel_3);
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.gridheight = 5;
        gbc_panel_3.gridwidth = 17;
        gbc_panel_3.insets = new Insets(0, 0, 5, 5);
        gbc_panel_3.fill = GridBagConstraints.BOTH;
        gbc_panel_3.gridx = 1;
        gbc_panel_3.gridy = 6;
        contentPane.add(panel_3, gbc_panel_3);

        JLabel lblNewLabel = new JLabel("添加条件：");
        lblNewLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel.gridwidth = 18;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        panel_3.add(lblNewLabel, gbc_lblNewLabel);

        comboBox = new JComboBox(s);
        comboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((JComboBox)e.getSource()).getSelectedItem() != null) //更换字段时，删除所有选项会触发时间，此处防止引发null异常
                {
                    int index=((JComboBox)e.getSource()).getSelectedIndex();//获取到选中项的索引
                    if(index != 0)
                    {
                        String Item=((JComboBox)e.getSource()).getSelectedItem().toString();//获取到项
                        textArea.append(Item);
                    }
                    comboBox.setSelectedIndex(0);
                }
            }

        });
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.gridwidth = 5;
        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 0;
        gbc_comboBox.gridy = 1;
        panel_3.add(comboBox, gbc_comboBox);


        comboBox_1 = new JComboBox(s1);
        comboBox_1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

                    int index=((JComboBox)e.getSource()).getSelectedIndex();//获取到选中项的索引
                    if(index != 0)
                    {
                      String Item=((JComboBox)e.getSource()).getSelectedItem().toString();//获取到项
                      textArea.append(" " + Item +" ");
                    }
                    comboBox_1.setSelectedIndex(0);
            }
        });
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.gridwidth = 5;
        gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_1.gridx = 5;
        gbc_comboBox_1.gridy = 1;
        panel_3.add(comboBox_1, gbc_comboBox_1);

        comboBox_2 = new JComboBox(s2);
        comboBox_2.addActionListener(new ActionListener() {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {
                                             int index = ((JComboBox) e.getSource()).getSelectedIndex();//获取到选中项的索引
                                             if (index != 0) {
                                                 String Item = ((JComboBox) e.getSource()).getSelectedItem().toString();//获取到项
                                                 textArea.append(" " + Item + " ");
                                             }
                                             comboBox_2.setSelectedIndex(0);
                                         }
                                     }
        );
        GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
        gbc_comboBox_2.gridwidth = 5;
        gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_2.gridx = 10;
        gbc_comboBox_2.gridy = 1;
        panel_3.add(comboBox_2, gbc_comboBox_2);

        JButton btnNewButton = new JButton("清除");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setCaretPosition(0);
                textArea.setText("");
            }
        });
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.gridwidth = 3;
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton.gridx = 15;
        gbc_btnNewButton.gridy = 1;
        panel_3.add(btnNewButton, gbc_btnNewButton);

        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.gridwidth = 18;
        gbc_textArea.gridheight = 3;
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 0;
        gbc_textArea.gridy = 2;
        textArea = new JTextArea();
        textArea.setFont(new Font("Times New Roman",10, 18));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        panel_3.add(new JScrollPane(textArea), gbc_textArea);


		/*生成分组语句面板*/
        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_2.setOpaque(true);
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridheight = 2;
        gbc_panel_2.gridwidth = 17;
        gbc_panel_2.insets = new Insets(0, 0, 5, 5);
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 11;
        contentPane.add(panel_2, gbc_panel_2);
        GridBagLayout gbl_panel_2 = new GridBagLayout();
        gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel_2.rowHeights = new int[]{0, 0, 0};
        gbl_panel_2.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel_2.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        panel_2.setLayout(gbl_panel_2);

        JLabel lblNewLabel_1 = new JLabel("分组按：");
        lblNewLabel_1.setHorizontalTextPosition(SwingConstants.LEFT);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.gridwidth = 18;
        gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 0;
        panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);

        comboBox_3 = new JComboBox(s);
        comboBox_3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((JComboBox)e.getSource()).getSelectedItem() != null){
                    int index=((JComboBox)e.getSource()).getSelectedIndex();//获取到选中项的索引
                    if(index != 0)
                    {
                       String Item=((JComboBox)e.getSource()).getSelectedItem().toString();//获取到项
                      if(textfield.getText().equals(""))
                           textfield.append(Item);
                      else
                          textfield.append(","+Item);
                 }
                  comboBox_3.setSelectedIndex(0);
             }
            }

        });
        GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
        gbc_comboBox_3.gridwidth = 5;
        gbc_comboBox_3.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_3.fill = GridBagConstraints.BOTH;
        gbc_comboBox_3.gridx = 0;
        gbc_comboBox_3.gridy = 1;
        panel_2.add(comboBox_3, gbc_comboBox_3);

        JButton btnNewButton_1 = new JButton("清除");
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textfield.setCaretPosition(0);
                textfield.setText("");
            }
        });
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_1.gridwidth = 2;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_1.gridx = 5;
        gbc_btnNewButton_1.gridy = 1;
        panel_2.add(btnNewButton_1, gbc_btnNewButton_1);


        GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
        gbc_textArea_1.gridwidth = 11;
        gbc_textArea_1.fill = GridBagConstraints.BOTH;
        gbc_textArea_1.gridx = 7;
        gbc_textArea_1.gridy = 1;
        textfield = new JTextArea();
        textfield.setFont(new Font("Times New Roman",10, 18));
        textfield.setLineWrap(true);
        textfield.setWrapStyleWord(true);
        panel_2.add(new JScrollPane(textfield), gbc_textArea_1);

		/*生成排序条件面板*/
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_1.setOpaque(true);
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridheight = 2;
        gbc_panel_1.gridwidth = 17;
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 13;
        contentPane.add(panel_1, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 0, 0};
        gbl_panel_1.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);

        JLabel lblNewLabel_2 = new JLabel("排序按：");
        lblNewLabel_2.setHorizontalTextPosition(SwingConstants.LEFT);
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.gridwidth = 18;
        gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 0;
        panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);

        comboBox_4 = new JComboBox(s);
        comboBox_4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((JComboBox)e.getSource()).getSelectedItem() != null){
                    int index=((JComboBox)e.getSource()).getSelectedIndex();//获取到选中项的索引
                    if(index != 0)
                    {
                        String Item=((JComboBox)e.getSource()).getSelectedItem().toString();//获取到项
                        if(textField_1.getText().equals(""))
                            textField_1.append(Item);
                        else
                           textField_1.append(","+Item);
                    }
                    comboBox_4.setSelectedIndex(0);
                }
            }

        });
        GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
        gbc_comboBox_4.gridwidth = 5;
        gbc_comboBox_4.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_4.fill = GridBagConstraints.BOTH;
        gbc_comboBox_4.gridx = 0;
        gbc_comboBox_4.gridy = 1;
        panel_1.add(comboBox_4, gbc_comboBox_4);
        String s3[] = {"--选择方向--","升序","降序"};
        comboBox_5 = new JComboBox(s3);
        comboBox_5.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=((JComboBox)e.getSource()).getSelectedIndex();//获取到选中项的索引
                if(index != 0)
                {
                    String Item=((JComboBox)e.getSource()).getSelectedItem().toString();//获取到项
                    if(Item.equals("升序"))
                        textField_1.append(" ASC");
                    else
                        textField_1.append(" DESC");
                }
                comboBox_5.setSelectedIndex(0);

            }

        });
        GridBagConstraints gbc_comboBox_5 = new GridBagConstraints();
        gbc_comboBox_5.gridwidth = 2;
        gbc_comboBox_5.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_5.fill = GridBagConstraints.BOTH;
        gbc_comboBox_5.gridx = 5;
        gbc_comboBox_5.gridy = 1;
        panel_1.add(comboBox_5, gbc_comboBox_5);

        JButton btnNewButton_2 = new JButton("清除");
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField_1.setCaretPosition(0);
                textField_1.setText("");
            }
        });
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_2.gridwidth = 2;
        gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_2.gridx = 7;
        gbc_btnNewButton_2.gridy = 1;
        panel_1.add(btnNewButton_2, gbc_btnNewButton_2);

        textField_1 = new JTextArea();
        textField_1.setLineWrap(true);
        textField_1.setWrapStyleWord(true);
        textField_1.setFont(new Font("Times New Roman",10, 18));
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.gridwidth = 9;
        gbc_textField.fill = GridBagConstraints.BOTH;
        gbc_textField.gridx = 9;
        gbc_textField.gridy = 1;
        panel_1.add(new JScrollPane(textField_1), gbc_textField);

		/*查询语句面板*/
        JPanel panel_13 = new JPanel();
        panel_13.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_13.setOpaque(true);
        GridBagConstraints gbc_panel_13 = new GridBagConstraints();
        gbc_panel_13.gridheight = 5;
        gbc_panel_13.gridwidth = 17;
        gbc_panel_13.insets = new Insets(0, 0, 5, 5);
        gbc_panel_13.fill = GridBagConstraints.BOTH;
        gbc_panel_13.gridx = 1;
        gbc_panel_13.gridy = 15;
        contentPane.add(panel_13, gbc_panel_13);
        GridBagLayout gbl_panel_13 = new GridBagLayout();
        gbl_panel_13.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel_13.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gbl_panel_13.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel_13.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        panel_13.setLayout(gbl_panel_13);

        JLabel lblNewLabel_3 = new JLabel("查询语句：");
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        lblNewLabel_3.setHorizontalTextPosition(SwingConstants.LEFT);
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
        gbc_lblNewLabel_3.gridwidth = 17;
        gbc_lblNewLabel_3.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 0;

        panel_13.add(lblNewLabel_3, gbc_lblNewLabel_3);

        textArea_1 = new JTextArea();
        textArea_1.setFont(new Font("Times New Roman",10, 18));
        textArea_1.setLineWrap(true);
        textArea_1.setWrapStyleWord(true);

        GridBagConstraints gbc_textArea_3 = new GridBagConstraints();
        gbc_textArea_3.insets = new Insets(0, 0, 5, 0);
        gbc_textArea_3.gridheight = 4;
        gbc_textArea_3.gridwidth = 18;
        gbc_textArea_3.fill = GridBagConstraints.BOTH;
        gbc_textArea_3.gridx = 0;
        gbc_textArea_3.gridy = 1;
        panel_13.add(new JScrollPane(textArea_1), gbc_textArea_3);

        JPanel panel_15 = new JPanel();
        panel_15.setLayout(new GridLayout(1, 4, 0, 0));
        JButton btnNewButton_3 = new JButton("生成");
        btnNewButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "SELECT ";
                if(textArea_2.getText().equals(""))
                    sql += "*";
                else
                    sql += textArea_2.getText();
                sql += " FROM " + tName;
                if(!textArea.getText().equals(""))
                    sql += " WHERE " + textArea.getText();
                if(!textField_1.getText().equals(""))
                    sql += " ORDER BY " + textField_1.getText();
                if(!textfield.getText().equals(""))
                    sql += " GROUP BY " + textfield.getText();
                textArea_1.setCaretPosition(0);
                textArea_1.setText(sql);
            }
        });
        panel_15.add(btnNewButton_3);
        JButton btnNewButton_4 = new JButton("清除");
        btnNewButton_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea_1.setCaretPosition(0);
                textArea_1.setText("");
                textArea_2.setCaretPosition(0);
                textArea_2.setText("");
                textArea.setCaretPosition(0);
                textArea.setText("");
                textfield.setCaretPosition(0);
                textfield.setText("");
                textField_1.setCaretPosition(0);
                textField_1.setText("");
            }
        });
        panel_15.add(btnNewButton_4);
        JButton btnNewButton_5 = new JButton("复制");
        btnNewButton_5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = getToolkit().getSystemClipboard();
                StringSelection text = new StringSelection(textArea_1.getText());
                clipboard.setContents(text,null);
            }
        });
        panel_15.add(btnNewButton_5);
        JButton btnNewButton_6 = new JButton("保存");
        btnNewButton_6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textArea_1.getText().equals(""))
                try{
                    RandomAccessFile rf=new RandomAccessFile("res/BuildinSQLs.txt","rw");
                    rf.seek(rf.length());  //将指针移动到文件末尾
                    rf.writeBytes(textArea_1.getText() + "\n"); //字符串末尾需要换行符
                    rf.close();//关闭文件流
                    if(f != null) //更新父窗口的comboBox
                        f.updateComboBox();
                    dispose();
                }catch(IOException e1){
                    JOptionPane.showMessageDialog(null, "无法写入配置文件 BuildinSQLs.txt", "添加语句失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel_15.add(btnNewButton_6);
        GridBagConstraints gbc_panel_15 = new GridBagConstraints();
        gbc_panel_15.gridwidth = 18;
        gbc_panel_15.fill = GridBagConstraints.BOTH;
        gbc_panel_15.gridx = 0;
        gbc_panel_15.gridy = 5;
        panel_13.add(panel_15, gbc_panel_15);


		/*以下均是空面板，用以填充界面*/
        JPanel panel_7 = new JPanel();
        GridBagConstraints gbc_panel_7 = new GridBagConstraints();
        gbc_panel_7.gridheight = 15;
        gbc_panel_7.insets = new Insets(0, 0, 5, 5);
        gbc_panel_7.fill = GridBagConstraints.BOTH;
        gbc_panel_7.gridx = 0;
        gbc_panel_7.gridy = 0;
        contentPane.add(panel_7, gbc_panel_7);

        JPanel panel_8 = new JPanel();
        GridBagConstraints gbc_panel_8 = new GridBagConstraints();
        gbc_panel_8.gridheight = 15;
        gbc_panel_8.insets = new Insets(0, 0, 5, 0);
        gbc_panel_8.fill = GridBagConstraints.BOTH;
        gbc_panel_8.gridx = 18;
        gbc_panel_8.gridy = 0;
        contentPane.add(panel_8, gbc_panel_8);
        JPanel panel_12 = new JPanel();
        GridBagConstraints gbc_panel_12 = new GridBagConstraints();
        gbc_panel_12.insets = new Insets(0, 0, 5, 0);
        gbc_panel_12.gridheight = 5;
        gbc_panel_12.gridwidth = 1;
        gbc_panel_12.fill = GridBagConstraints.BOTH;
        gbc_panel_12.gridx = 18;
        gbc_panel_12.gridy = 15;
        contentPane.add(panel_12, gbc_panel_12);
        JPanel panel_14 = new JPanel();
        GridBagConstraints gbc_panel_14 = new GridBagConstraints();
        gbc_panel_14.gridheight = 5;
        gbc_panel_14.gridwidth = 1;
        gbc_panel_14.insets = new Insets(0, 0, 5, 5);
        gbc_panel_14.fill = GridBagConstraints.BOTH;
        gbc_panel_14.gridx = 0;
        gbc_panel_14.gridy = 15;
        contentPane.add(panel_14, gbc_panel_14);

    }

}
