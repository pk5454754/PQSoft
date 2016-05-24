package mini.home.uitl.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author K.S
 * @since 2013-07-01
 */
public class MiniLog {
	/**
	 * �Ƿ�debug���Ժ���Ըĳɼ��������ļ��ġ�
	 */
	public static boolean debug = true;
	/**
	 * ��־·��
	 */
	public static String path="log.txt";//��������Եģ�Ҳ�����Ǿ��Ե�
	/**
	 * �ļ�����
	 */
	public static File file;

	
	
	/**
	 * ����̨��־
	 * 
	 * @param str
	 */
	public static void printLog(String str) {
		if (!debug) {// �������debug�Ͳ���ӡ
			return;
		}
		System.out.println(str);
	}

	/**
	 * �ļ���־
	 * 
	 * @param str
	 * @throws IOException
	 */
	public static void logToFile(String str) {
		if (!debug) {// �������debug�Ͳ�д��־
			return;
		}
		FileWriter writer;
		try {
			writer = new FileWriter(getFile(), true);//true�Ƿ�׷��ģʽ
			writer.write(str);
			writer.write("\r\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ����
	 * @return
	 */
	public static File getFile(){
		if(file==null){
			file=new File(path);
		}
		return file;
	}
}
