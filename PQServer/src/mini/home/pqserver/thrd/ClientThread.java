package mini.home.pqserver.thrd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;

import mini.home.uitl.encrypt.SimpleByteEncrypt;
import mini.home.uitl.log.MiniLog;

/**
 * @author K.S
 * @since 2013-06-27
 * strArr[0]为数据类型，接下去是其它内容
 * tell为发送消息类型
 * disconnect为结束连接命令
 * 
 */
public class ClientThread implements Runnable {
	private MainThread mt;
	private Socket socket;
	private boolean debug = false;
	int threadId;

	public ClientThread(int id,MainThread mt, Socket s) {
		threadId=id;
		setMt(mt);
		setSocket(s);
		if(debug)
		System.out.println(s.toString());
	}

	boolean running = true;
	ObjectInputStream in;
	Object str;

	
	@Override
	public void run() {
		while (running) {
			try {
				socket.setKeepAlive(true);
				in = new ObjectInputStream(socket.getInputStream());
				str = in.readObject();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//日期格式化的类
				String now = sdf.format(new java.util.Date());//当前时间
				
				String[] strArr;
				if(str instanceof String[]){
					strArr=(String[]) str;
					if(strArr.length>0){
//						System.out.print(strArr[0]);
						strArr[0]=SimpleByteEncrypt.decrypt(strArr[0]);
						if(strArr[0].equals("tell")){//发送信息
							strArr[1]=SimpleByteEncrypt.decrypt(strArr[1]);
							strArr[2]=SimpleByteEncrypt.decrypt(strArr[2]);
							/*
							 * 0是tell时
							 * 1放名字
							 * 2放内容
							 * */
							mt.tell(strArr[1],strArr[2], socket.getInetAddress()
									.getHostAddress());
							if (debug)
								System.out.println(socket.getInetAddress().getHostAddress()+":"+strArr[1]+"_"+now+"_"+strArr[2]+"\n");
							if (debug)
								MiniLog.logToFile(socket.getInetAddress().getHostAddress()+":"+strArr[1]+"_"+now+"_"+strArr[2]+"\n");
						}else if(strArr[0].equals("callback")){//收到信息
							strArr[1]=SimpleByteEncrypt.decrypt(strArr[1]);
							strArr[2]=SimpleByteEncrypt.decrypt(strArr[2]);
							strArr[2]=SimpleByteEncrypt.decrypt(strArr[2]);//收到信息以后又加了一次密发过来的，所以要解密两次
							if (debug)
								System.out.println("callback_"+socket.getInetAddress().getHostAddress()+":"+strArr[1]+"\n"+now+"\n"+strArr[2]);
							if (debug)
								MiniLog.logToFile("callback_"+socket.getInetAddress().getHostAddress()+":"+strArr[1]+"\n"+now+"\n"+strArr[2]);
							
						}else if(strArr[0].equals("disconnect")){//连接中断
							running=false;
							System.out.println("socket:"+socket.hashCode()+"\nip:"+socket.getInetAddress()
									.getHostAddress()+"连接中断");
							mt.getlSocket().remove(socket.hashCode());
						}
						
					}else{
						System.out.println("空的字符串数组对象");
					}
				}else{
					System.out.println("收到这个东西,不知道有什么用:");
					System.out.println(str);
					continue;
				}
				
			} catch (IOException e) {

				
				running=false;
				System.out.println("socket:"+socket.hashCode()+"\nip:"+socket.getInetAddress()
						.getHostAddress()+"连接中断");
				mt.getlSocket().remove(socket.hashCode());
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
				running=false;
				System.out.println(socket.getInetAddress()
						.getHostAddress()+"连接中断");
				mt.getlSocket().remove(socket.hashCode());
			}

		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public MainThread getMt() {
		return mt;
	}

	public void setMt(MainThread mt) {
		this.mt = mt;
	}

}
