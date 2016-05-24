package mini.home.pqclient.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;

import mini.home.pqclient.ui.MainFrame;
import mini.home.uitl.fin.ConfClass;
import mini.home.uitl.regex.StringRegex;

/**
 * @author K.S
 * @since 2013-06-20
 */
public class SocketConnect implements ActionListener {
	private MainFrame mf;
	public SocketConnect(MainFrame mf){
		this.mf=mf;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String str=JOptionPane.showInputDialog("请输入服务器ip", ConfClass.ServerIP);
		File file=new File("ipconfig.ini");
		FileInputStream fis=null;
		
		byte[] read=new byte[20];//一次读10byte
		boolean error=false;
		try {
			fis=new FileInputStream(file);
			fis.read(read, 0, 20);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			error=true;
		} catch (IOException e) {
			e.printStackTrace();
			error=true;
		}
		if(error==false){
			str=new String(read);
			str=str.trim();
			if(StringRegex.isIP(str)){
				mf.initSocket(str);
			};
		}else{
			mf.initSocket(str);
		}
	}

}
