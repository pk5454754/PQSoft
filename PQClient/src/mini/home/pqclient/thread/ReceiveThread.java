package mini.home.pqclient.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mini.home.pqclient.ui.MainFrame;
import mini.home.uitl.encrypt.SimpleByteEncrypt;

/**
 * @author KindomStar
 * @since 2013-06-25
 * 用于客户端接收信息的线程
 */
public class ReceiveThread implements Runnable {
	public boolean run=true;
	private MainFrame mt=null;
	public ReceiveThread(MainFrame mt){
		this.setMt(mt);
	}
	@Override
	public void run() {
		while(run){
			if(mt.getSocket()==null){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			ObjectInputStream ois=null;
			Object input=null;
			String[] callback=new String[3];//回写服务器，表示收到内容
			try {
				ois=new ObjectInputStream( mt.getSocket().getInputStream());
				input=ois.readObject();
				callback=sendData("callback", mt.name==null?mt.getSocket().getInetAddress()
						.getHostAddress():mt.name, input.toString());
				
			} catch (IOException e) {
				e.printStackTrace();
				run=false;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				run=false;
			}
			
			sendMessage(callback);
			
			
//			System.out.println(input.toString());
			String inputstring = SimpleByteEncrypt.decrypt(input.toString());
//			System.out.println(inputstring);
			mt.getShowArea().append(inputstring);
			//移动滚动条
			mt.getShowArea().setCaretPosition(mt.getShowArea().getText().length());
			mt.setVisible(false);
			mt.setVisible(true);
		}
	}
	/**
	 * 发送信息回服务器表示收到这样的信息
	 * @param callback
	 */
	private void sendMessage(String[] callback) {
		ObjectOutputStream out=null;
		try {
			out=new ObjectOutputStream(mt.getSocket().getOutputStream());
			out.reset();
			out.writeObject(callback);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public MainFrame getMt() {
		return mt;
	}
	public void setMt(MainFrame mt) {
		this.mt = mt;
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

}
