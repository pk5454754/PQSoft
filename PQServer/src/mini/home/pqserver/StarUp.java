
package mini.home.pqserver;

import mini.home.pqserver.thrd.MainThread;


/**
 * @author K.S
 * @since 2013-06-19
 * �����������
 */
public class StarUp{
	public static void main(String[] args){
		MainThread mt=new MainThread();//
		new Thread(mt).start();
		
	}
}