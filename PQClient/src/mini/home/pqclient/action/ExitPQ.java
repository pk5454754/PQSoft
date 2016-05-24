package mini.home.pqclient.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mini.home.pqclient.ui.MainFrame;
import mini.home.uitl.encrypt.SimpleByteEncrypt;
import mini.home.uitl.fin.ConfClass;

public class ExitPQ implements ActionListener {
	MainFrame mf;

	public ExitPQ(MainFrame mainFrame) {
		this.mf = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (mf.getSocket() != null) {
			String[] sendout;
			sendout = new String[1];
			sendout[0]="disconnect";
			sendout[0]=SimpleByteEncrypt.encrypt(sendout[0]);

			Socket sk = mf.getSocket();
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream(sk.getOutputStream());
				out.reset();
				out.writeObject(sendout);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

}
