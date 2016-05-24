package mini.home.pqclient.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import mini.home.pqclient.ui.MainFrame;

public class SetName implements ActionListener {
	MainFrame mf;
	public SetName(MainFrame mainFrame) {
		this.mf=mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		mf.name=JOptionPane.showInputDialog("ÇëÊäÈëÄãµÄÃû×Ö",mf.name);
	}

}
