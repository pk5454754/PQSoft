package mini.home.pqclient.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * @author K.S
 * 个人信息
 */
public class MyInfo implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "作者：王国之星  \n日期：2013-06-28 \n版本：MyMiniHome_PQ 1.0 for myminihome\nQ  Q：309618076\n");
		
	}

}
