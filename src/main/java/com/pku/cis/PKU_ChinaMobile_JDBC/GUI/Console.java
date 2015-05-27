package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

/**
 * Created by mrpen on 2015/5/5.
 */

// test for git
import com.pku.cis.PKU_ChinaMobile_JDBC.Client.*;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


public class Console extends JFrame {

    private JPanel contentPane;
    static boolean flag = false; //标志文本末尾有无下滑线
    static JTextArea textArea;
    static int startPos;
    static int scrollPos; //标记scrollPos的位置
    static Timer timer;
    static JScrollPane scroll;
    static Vector<String> records = new Vector<String>();
    static int recordPos;

    static String tableName ="person";


    static PKUDriver d = new PKUDriver();

    public static void MyAppend(String str)
    {
        textArea.append(str);
        textArea.paintImmediately(textArea.getBounds()); //实时刷新textArea，否则会等主线程结束才刷新
    }
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Console frame = new Console();
    }

    public void execute(String sql)
    {
        MyAppend("\nExecute: " + sql + "\n");
        String fullURL = Global.urlPrefix + Global.IP;
        MyAppend("Attempt to connect " + fullURL+"\n");
        PKUConnection con;
        try {
            con = (PKUConnection) DriverManager.getConnection(fullURL, Global.userName, Global.userPasswd);
            con.setDst(0); //设置为0表示普通连接，index表示对应的数据库类型
            PKUStatement stmt;
            stmt = (PKUStatement)con.createStatement();
            PKUResultSet rs = (PKUResultSet)stmt.executeQuery(sql);
            PKUResultSetMetaData rmeta = (PKUResultSetMetaData) rs.getMetaData();
            int numColumns = rmeta.getColumnCount();
            String temp = "";
            for( int i = 1;i<= numColumns;i++ ) {
                if( i < numColumns )
                    temp += String.format("%-15s", rmeta.getColumnName(i))+" | ";
                else
                    temp += String.format("%-15s", rmeta.getColumnName(i))+" | " + "\r\n";
            }
            MyAppend("\n"+temp);
            textArea.paintImmediately(textArea.getBounds());
            temp = "";
            while( rs.next()) {
                for( int i = 1;i <= numColumns;i++ ){
                    if( i < numColumns )
                        temp += String.format("%-15s", new String((rs.getString(i).trim()))) + " | ";
                    else
                        temp += String.format("%-15s",new String((rs.getString(i).trim()))) + " | " + "\r\n";
                }
                MyAppend(temp);
                temp = "";
            }
            MyAppend(">");
            rs.close();
            con.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            MyAppend(e1.getMessage()+"\n>");

        }

    }
    /**
     * Create the frame.
     */
    public Console(){
        setTitle("透明网关系统——命令行控制台");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 660, 454);
        contentPane = new JPanel();
        contentPane.setForeground(Color.WHITE);
        contentPane.setBackground(Color.BLACK);
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(1, 0, 0, 0));

        textArea = new JTextArea("",20,20);
        textArea.setLineWrap(true);//设置自动换行
        textArea.setWrapStyleWord(true);//设置断行不断字
        textArea.setEditable(false);
        textArea.setFont(new Font("Terminal", Font.BOLD, 14));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(new Color(211, 211, 211));
        textArea.setText("Welcome to the Transparent GateWay System monitor.\nCommands end with '\\n'.\nType "
                + "'\\c' to clear the current input statement.\n\n>");
        int rows = textArea.getLineCount()-1;

        scroll = new JScrollPane(textArea); //设置滚动条
        contentPane.add(scroll);
        setVisible(true);

        try {
            startPos = textArea.getLineEndOffset(rows); //记录文本末尾位置
            scrollPos = scroll.getVerticalScrollBar().getValue();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        textArea.addKeyListener(new KeyAdapter() {
                                    public void keyPressed(KeyEvent e) {
                                        timer.stop();
                                        try {
                                            int pos = textArea.getLineEndOffset(textArea.getLineCount() - 1);//读取文本末尾位置
                                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                                                if (flag) //flag标记事件发生瞬间，文本末尾是‘_’，若是，替换掉
                                                    textArea.replaceRange("", pos - 1, pos);
                                                int endPos = textArea.getLineEndOffset(textArea.getLineCount() - 1);
                                                String cmd = textArea.getText(startPos, endPos - startPos); //读取输入语句
                                                /*更新执行历史*/
                                                records.add(cmd);
                                                recordPos = records.size();

                                                if (cmd.equals("\\c"))
                                                    textArea.setText(">");
                                                else
                                                    execute(cmd);
                                                startPos = textArea.getLineEndOffset(textArea.getLineCount() - 1);//记录文本末尾位置
                                                if (flag)
                                                    MyAppend("_"); //补回原来的下划线

                                            }
                                            else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                                                if (flag)
                                                    textArea.replaceRange("", pos - 2, pos - 1);
                                                else
                                                    textArea.replaceRange("", pos - 1, pos);

                                            }
                                            else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                                                if(recordPos < records.size() - 1) {
                                                    recordPos++;
                                                    String cmd = records.elementAt(recordPos);
                                                    if (flag)
                                                        textArea.replaceRange(cmd, startPos, pos - 1);
                                                    else
                                                        textArea.replaceRange(cmd, startPos, pos);
                                                }
                                                else
                                                {
                                                    recordPos = records.size();
                                                    String cmd = "";
                                                    if (flag)
                                                        textArea.replaceRange(cmd, startPos, pos - 1);
                                                    else
                                                        textArea.replaceRange(cmd, startPos, pos);
                                                }
                                            }
                                            else if(e.getKeyCode() == KeyEvent.VK_UP) {
                                                if(recordPos > 0) {
                                                    recordPos--;
                                                    String cmd = records.elementAt(recordPos);
                                                    if (flag)
                                                        textArea.replaceRange(cmd, startPos, pos - 1);
                                                    else
                                                        textArea.replaceRange(cmd, startPos, pos);
                                                }
                                            }
                                            else if (e.getKeyCode() != KeyEvent.VK_CAPS_LOCK && e.getKeyCode() != KeyEvent.VK_TAB && e.getKeyCode() != KeyEvent.VK_SHIFT) { //将来需要把所有的功能键去掉
                                                if (flag)
                                                    textArea.insert(e.getKeyChar() + "", pos - 1);
                                                else
                                                    MyAppend(e.getKeyChar() + "");
                                            }
                                        } catch (BadLocationException e1) {
                                            e1.printStackTrace();
                                        }
                                        timer.start();
                                    }
                                }
        );

        timer = new Timer(500, new ActionListener(){ //设置光标闪烁效果
            public void actionPerformed(ActionEvent e){

                if(scroll.getVerticalScrollBar().getValue() >= scrollPos)
                    scrollPos = scroll.getVerticalScrollBar().getValue(); //更新滚动条位置
                if(scroll.getVerticalScrollBar().getValue() >= scrollPos) {//当滚动条移动时，取消闪烁事件，防止自动聚焦到光标位置
                    if (flag) {
                        try {
                            int rows = textArea.getLineCount() - 1;
                            textArea.replaceRange("", textArea.getLineEndOffset(rows) - 1, textArea.getLineEndOffset(rows));
                            flag = false;
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        MyAppend("_");
                        flag = true;
                    }
                    textArea.setCaretPosition(textArea.getText().length());
                }
            }
        }
        );
        timer.start();
    }

}


