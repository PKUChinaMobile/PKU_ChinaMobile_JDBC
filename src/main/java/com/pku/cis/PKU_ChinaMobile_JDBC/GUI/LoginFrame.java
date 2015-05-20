package com.pku.cis.PKU_ChinaMobile_JDBC.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mrpen on 2015/5/4.
 */

public class LoginFrame extends JFrame {
    static final int FHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    static final int FWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    JTextField in; //用户名文本框
    JPasswordField in2; //密码文本框

    LoginFrame()
    {
        this.setSize(FWidth / 3, FHeight / 3);
        this.setLocation(FWidth / 3, FHeight / 3);
        this.setResizable(false);//设置窗口是否可由用户调整
        this.setTitle("透明网关系统");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//按关闭可退出

        ImageIcon img = new ImageIcon("res/image/bg0.jpg");
        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight());//设置背景标签的位置
        JPanel imagePanel = (JPanel)this.getContentPane();
        imagePanel.setOpaque(false);
        imagePanel.setLayout(null);

        JLabel l = new JLabel("用户名:");
        l.setSize(80, 40);
        l.setLocation(120, 75);
        l.setFont(new Font("黑体",Font.PLAIN,19));

        in = new JTextField();
        in.setSize(140, 30);
        in.setLocation(200, 80);
        in.setFont(new Font("黑体",Font.PLAIN,19));

        JLabel l2 = new JLabel("密  码:");
        l2.setSize(80, 40);
        l2.setLocation(120, 123);
        l2.setFont(new Font("黑体",Font.PLAIN,19));

        in2 = new JPasswordField();
        in2.setSize(140,30);
        in2.setLocation(200, 130);


        JButton btn = new JButton("登录");
        btn.setSize(100, 30);
        btn.setLocation(200, 180);
        btn.setFont(new Font("黑体", Font.PLAIN, 19));
        btn.addActionListener(new ActionListener() {
                                  public void actionPerformed(ActionEvent e) {
                                      if(accessCheck())
                                      {
                                          MainFrame mf = new MainFrame();
                                          mf.show();
                                          setVisible(false);

                                      }
                                      else
                                      {
                                          JOptionPane.showMessageDialog(null,"错误的用户名密码!","登录失败", JOptionPane.ERROR_MESSAGE);
                                      }
                                  }
                              });


        imagePanel.add(l);
        imagePanel.add(l2);
        imagePanel.add(in);
        imagePanel.add(in2);
        imagePanel.add(btn);


        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));

    }

    boolean accessCheck() //查询访问权限
    {
        String usr = in.getText();
        String pwd = new String(in2.getPassword());
        System.out.println("usr: " + usr + "  pwd: " + pwd);
        if(pwd.equals("123"))
            return false;
        return true;
    }
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame frame = new LoginFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
