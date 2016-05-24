package mini.home.pqclient.test;
import javax.swing.*;
 import java.util.*;
 import java.awt.*;

 public class Example{

     public Example() {
     }

     public static void main(String args[]) {
        JFrame f = new JFrame("GridBag Layout Example");

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        f.setLayout(gridbag);
//��Ӱ�ť1
        c.fill = GridBagConstraints.BOTH;
        c.gridheight=2;
        c.gridwidth=1;
        c.weightx=0.0;//Ĭ��ֵΪ0.0
        c.weighty=0.0;//Ĭ��ֵΪ0.0
        c.anchor=GridBagConstraints.SOUTHWEST;
        JButton jButton1 = new JButton("��ť1");
        gridbag.setConstraints(jButton1, c);
        f.add(jButton1);
//��Ӱ�ť2        
        c.fill = GridBagConstraints.NONE;
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.gridheight=1;
        c.weightx=1.0;//Ĭ��ֵΪ0.0
        c.weighty=0.8;
        JButton jButton2 = new JButton("��ť2");
        gridbag.setConstraints(jButton2, c);
        f.add(jButton2);
//��Ӱ�ť3
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth=1;
        c.gridheight=1;
        c.weighty=0.2;
        JButton jButton3 = new JButton("��ť3");
        gridbag.setConstraints(jButton3, c);
        f.add(jButton3);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500,500);
        f.setVisible(true);
     }
 }