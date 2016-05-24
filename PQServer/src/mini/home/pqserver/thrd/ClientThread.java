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
 * strArr[0]Ϊ�������ͣ�����ȥ����������
 * tellΪ������Ϣ����
 * disconnectΪ������������
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
				//���ڸ�ʽ������
				String now = sdf.format(new java.util.Date());//��ǰʱ��
				
				String[] strArr;
				if(str instanceof String[]){
					strArr=(String[]) str;
					if(strArr.length>0){
//						System.out.print(strArr[0]);
						strArr[0]=SimpleByteEncrypt.decrypt(strArr[0]);
						if(strArr[0].equals("tell")){//������Ϣ
							strArr[1]=SimpleByteEncrypt.decrypt(strArr[1]);
							strArr[2]=SimpleByteEncrypt.decrypt(strArr[2]);
							/*
							 * 0��tellʱ
							 * 1������
							 * 2������
							 * */
							mt.tell(strArr[1],strArr[2], socket.getInetAddress()
									.getHostAddress());
							if (debug)
								System.out.println(socket.getInetAddress().getHostAddress()+":"+strArr[1]+"_"+now+"_"+strArr[2]+"\n");
							if (debug)
								MiniLog.logToFile(socket.getInetAddress().getHostAddress()+":"+strArr[1]+"_"+now+"_"+strArr[2]+"\n");
						}else if(strArr[0].equals("callback")){//�յ���Ϣ
							strArr[1]=SimpleByteEncrypt.decrypt(strArr[1]);
							strArr[2]=SimpleByteEncrypt.decrypt(strArr[2]);
							strArr[2]=SimpleByteEncrypt.decrypt(strArr[2]);//�յ���Ϣ�Ժ��ּ���һ���ܷ������ģ�����Ҫ��������
							if (debug)
								System.out.println("callback_"+socket.getInetAddress().getHostAddress()+":"+strArr[1]+"\n"+now+"\n"+strArr[2]);
							if (debug)
								MiniLog.logToFile("callback_"+socket.getInetAddress().getHostAddress()+":"+strArr[1]+"\n"+now+"\n"+strArr[2]);
							
						}else if(strArr[0].equals("disconnect")){//�����ж�
							running=false;
							System.out.println("socket:"+socket.hashCode()+"\nip:"+socket.getInetAddress()
									.getHostAddress()+"�����ж�");
							mt.getlSocket().remove(socket.hashCode());
						}
						
					}else{
						System.out.println("�յ��ַ����������");
					}
				}else{
					System.out.println("�յ��������,��֪����ʲô��:");
					System.out.println(str);
					continue;
				}
				
			} catch (IOException e) {

				
				running=false;
				System.out.println("socket:"+socket.hashCode()+"\nip:"+socket.getInetAddress()
						.getHostAddress()+"�����ж�");
				mt.getlSocket().remove(socket.hashCode());
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
				running=false;
				System.out.println(socket.getInetAddress()
						.getHostAddress()+"�����ж�");
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
