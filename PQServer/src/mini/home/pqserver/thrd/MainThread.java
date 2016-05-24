package mini.home.pqserver.thrd;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mini.home.uitl.encrypt.SimpleByteEncrypt;


/**
 * @author KS
 * @since 2013-06-19
 * 服务器主线程
 */
public class MainThread implements Runnable {
	private ServerSocket sSocket;
	private HashMap<Integer,Socket> lSocket; //客户端
	
	public HashMap<Integer, Socket> getlSocket() {
		return lSocket;
	}
	public void setlSocket(HashMap<Integer, Socket> lSocket) {
		this.lSocket = lSocket;
	}
	//	private List<ClientThread> lClientThread;//客户端线程
	private boolean isRun=true;
	
	public MainThread(){
		try {
			sSocket = new ServerSocket(8080);
			
		} catch (IOException e) {
			System.out.println("端口已被占用!");
			e.printStackTrace();
			//天之写的这里用了休眠，不知道有没有特殊用途
			System.exit(0);
		}
		lSocket=new HashMap<Integer,Socket>();
//		lClientThread=new ArrayList<ClientThread>();
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Server started");
		while(isRun){
			try {
				Socket socket=sSocket.accept();
				System.out.println("连接成功\nhashcode:"+socket.hashCode()+"\n来自:"+socket.getInetAddress().getHostAddress()+":"+socket.getPort());
				ClientThread ct=new ClientThread(socket.hashCode(),this,socket);
				lSocket.put(socket.hashCode(),socket);
				new Thread(ct).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public boolean isRun() {
		return isRun;
	}
	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}
	/**
	 * @param name
	 * @param str
	 * @param ip
	 * @throws IOException
	 */
	public void tell(String name,String str,String ip) throws IOException{
		int lsize = lSocket.size();
		Socket sk=null;
		ObjectOutputStream out=null;
		
		Iterator it=lSocket.keySet().iterator();
		while(it.hasNext()){
			sk=lSocket.get(it.next());
			try {
				sk.setKeepAlive(true);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				lSocket.remove(sk.hashCode());
				continue;
			}// 不影响运行的错误是不是要用catch?
			if (!sk.isConnected()) {// 如果没有连接
				lSocket.remove(sk.hashCode());//移除客户端
				continue;
			}

			out = new ObjectOutputStream(sk.getOutputStream());
			out.reset();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 日期格式化的类
			String now = sdf.format(new java.util.Date());// 当前时间
			
			String sendMessage = name+" said at "+now+":\n  "+str+"\n\n";
			sendMessage=SimpleByteEncrypt.encrypt(sendMessage);
			out.writeObject(sendMessage);
			
		}
		
		
	}
	
	
}
