
package mini.home.pqserver;

import mini.home.pqserver.thrd.MainThread;


/**
 * @author K.S
 * @since 2013-06-19
 * 服务器入口类
 */
public class StarUp{
	public static void main(String[] args){
		MainThread mt=new MainThread();//
		new Thread(mt).start();
		
	}
}