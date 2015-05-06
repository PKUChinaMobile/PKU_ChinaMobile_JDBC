package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

/**
 * Created by mrpen on 2015/5/5.
 */


import com.pku.cis.PKU_ChinaMobile_JDBC.Client.*;
import com.pku.cis.PKU_ChinaMobile_JDBC.examples.Test;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Font;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.*;


public class Console extends JFrame {

    private JPanel contentPane;
    static boolean flag = false; //标志文本末尾有无下滑线
    static boolean flag2 = true;
    static JTextArea textArea;
    static int startPos;
    Lock lock = new ReentrantLock();

    static String userName ="C##MYTEST";
    static String userPasswd ="123456";
    static String tableName ="person";

    static String urlPrefix = "jdbc:PKUDriver:";
    static String IP = "127.0.0.1";
    static PKUDriver d = new PKUDriver();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Console frame = new Console();
    }

    public void execute(String sql)
    {
        textArea.append("\nExecute: " + sql + "\n");
        String fullURL = urlPrefix + IP;
        textArea.append("Attempt to connect " + fullURL+"\n");
        PKUConnection con;
        try {
            con = (PKUConnection) DriverManager.getConnection(fullURL, userName, userPasswd);
            con.setDst(0); //设置为0表示普通连接，index表示对应的数据库类型
            PKUStatement stmt;
            stmt = (PKUStatement)con.createStatement();
            textArea.append("Executing " + sql);
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

            while( rs.next() ){
                for( int i = 1;i <= numColumns;i++ ){
                    if( i < numColumns )
                        temp += String.format("%-15s",new String((rs.getString(i).trim()))) + " | ";
                    else
                        temp += String.format("%-15s",new String((rs.getString(i).trim()))) + " | " + "\r\n";
                }
            }
            textArea.append("\n" + temp + "\n>");
            rs.close();
            con.close();
        } catch (SQLException e1) {
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            e1.printStackTrace(printWriter);
            textArea.append(result.toString());
        }

    }
    /**
     * Create the frame.
     */
    public Console(){
        setTitle("透明网关系统命令行控制台");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        textArea.setText("Welcome to the Transparent GateWay System monitor.\nCommands end with '\\n'.\nType 'help' or '\\h' for help. \nType "
                + "'\\c' to clear the current input statement.\n\n>");
        int rows = textArea.getLineCount()-1;

        try {
            startPos = textArea.getLineEndOffset(rows);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        textArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                flag2 = false;

            }
            public void mouseDragged(MouseEvent e) {

                flag2 = false;

            }
            public void mouseReleased(MouseEvent e){

                flag2 =false;

            }
            public void mouseWheelMoved(MouseWheelEvent e){

                flag2 = false;
            }
        });
        textArea.addKeyListener(new KeyAdapter() {
                                    public void keyPressed(KeyEvent e)
                                    {
                                        lock.lock();
                                        try {
                                            int pos = textArea.getLineEndOffset(textArea.getLineCount()-1);


                                            if(e.getKeyCode() == KeyEvent.VK_ENTER)
                                            {

                                                if(flag)
                                                    textArea.replaceRange("", pos - 1, pos);
                                                int endPos = textArea.getLineEndOffset(textArea.getLineCount()-1);
                                                String cmd = textArea.getText(startPos, endPos - startPos);
                                                execute(cmd);
                                                startPos = textArea.getLineEndOffset(textArea.getLineCount()-1);
                                                if(flag)
                                                    textArea.append("_");

                                            }
                                            else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                                            {
                                                if (flag)
                                                    textArea.replaceRange("", pos - 2, pos-1);
                                                else
                                                    textArea.replaceRange("", pos - 1, pos);

                                            }
                                            else if(e.getKeyCode() != KeyEvent.VK_CAPS_LOCK && e.getKeyCode() != KeyEvent.VK_TAB && e.getKeyCode() != KeyEvent.VK_SHIFT ){ //将来需要把所有的功能键去掉
                                                if (flag)
                                                    textArea.insert(e.getKeyChar() + "", pos - 1);
                                                else
                                                    textArea.append(e.getKeyChar() + "");
                                            }
                                        } catch (BadLocationException e1) {
                                            e1.printStackTrace();
                                        }
                                        lock.unlock();
                                    }
                                }
        );
        JScrollPane scroll = new JScrollPane(textArea); //为了设置滚动条
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        textArea.getCaret().addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                textArea.getCaret().setVisible(false);
            }
        });
        contentPane.add(scroll);
        setVisible(true);

        Timer timer = new Timer(500, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                lock.lock();
                if(flag2){

                if(flag)
                {
                    try {
                        int rows = textArea.getLineCount()-1;
                        textArea.replaceRange("", textArea.getLineEndOffset(rows) - 1,textArea.getLineEndOffset(rows));
                        flag = false;
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    textArea.append("_");
                    flag = true;
                }
                textArea.setCaretPosition(textArea.getText().length());

            }lock.unlock();}
        }
        );
        timer.start();
    }

}


