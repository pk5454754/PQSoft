package mini.home.pqclient.listener;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import mini.home.pqclient.ui.MainFrame;
import mini.home.uitl.encrypt.SimpleByteEncrypt;
import mini.home.uitl.fin.ConfClass;
import mini.home.uitl.regex.StringRegex;

/**
 * @author KingdomStar
 * @since 2013-06-25
 * 发送按钮监听
 */
public class SendMessageListener implements MouseListener,KeyListener {
	private MainFrame mf;
	public MainFrame getMf() {
		return mf;
	}
	public void setMf(MainFrame mf) {
		this.mf = mf;
	}
	public SendMessageListener(MainFrame mf){
		this.setMf(mf);
	}
	
	/**
	 * 校验是否通过，true是满足一定条件，校验不通过。
	 * @return
	 */
	private boolean check() {
		if(mf.getSocket()==null){//请先连接服务器
			JOptionPane.showMessageDialog(null, "请先连接服务器");
			return true;
		}
		if(mf.getSendArea().getText().length()>140){//输入长度不能大于140
			JOptionPane.showMessageDialog(null, "输入长度不能大于140");
			return true;
		}
		if(mf.getSendArea().getText().length()==0){//
			JOptionPane.showMessageDialog(null, "请输入发送内容");
			return true;
		}
		
		return false;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		sendMessage();
	}
	private void sendMessage() {
		if(mf.getSocket()==null){
			String str=null;
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
				if(str==null){
					str=JOptionPane.showInputDialog("请输入服务器ip", ConfClass.ServerIP);
				}
				mf.initSocket(str);
			}
		}else {
			if(!mf.getSocket().isConnected()){
				mf.setSocket(null);
				JOptionPane.showMessageDialog(null, "请重新连接服务器");
				return ;
			}
		}
		if(check()){
			return;
		}
		String[] sendout=sendData("tell", mf.name==null?mf.getSocket().getInetAddress()
				.getHostAddress():mf.name, mf.getSendArea().getText());
		
		
		
		Socket sk=mf.getSocket();
		ObjectOutputStream out=null;
		try {
			out=new ObjectOutputStream(sk.getOutputStream());
			out.reset();
			out.writeObject(sendout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mf.getSendArea().setText("");
		mf.repaint();
	}
	/**
	 * 发送前加密
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public String[] sendData(String a,String b,String c){
		String[] sendout=null;
		sendout=new String[3];
		sendout[0]=SimpleByteEncrypt.encrypt(a);
		sendout[1]=SimpleByteEncrypt.encrypt(b);
		sendout[2]=SimpleByteEncrypt.encrypt(c);
		return sendout;
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**(non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 * 写在press方法里反应会快一点,写在释放里不准确，ctrl可能也放了。
	 * ctrl+enter 发送信息
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_ENTER){
			sendMessage();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		
		
	}

}
