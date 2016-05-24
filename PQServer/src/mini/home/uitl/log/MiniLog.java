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
	 * 是否debug，以后可以改成加载配置文件的。
	 */
	public static boolean debug = true;
	/**
	 * 日志路径
	 */
	public static String path="log.txt";//可以是相对的，也可以是绝对的
	/**
	 * 文件对象
	 */
	public static File file;

	
	
	/**
	 * 控制台日志
	 * 
	 * @param str
	 */
	public static void printLog(String str) {
		if (!debug) {// 如果不是debug就不打印
			return;
		}
		System.out.println(str);
	}

	/**
	 * 文件日志
	 * 
	 * @param str
	 * @throws IOException
	 */
	public static void logToFile(String str) {
		if (!debug) {// 如果不是debug就不写日志
			return;
		}
		FileWriter writer;
		try {
			writer = new FileWriter(getFile(), true);//true是否追加模式
			writer.write(str);
			writer.write("\r\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 单例
	 * @return
	 */
	public static File getFile(){
		if(file==null){
			file=new File(path);
		}
		return file;
	}
}
