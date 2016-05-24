package mini.home.uitl.encrypt;

import java.io.ObjectOutputStream.PutField;
import java.util.Map;

import mini.home.uitl.itf.Encrypt;

/**
 * @author K.S
 * @since 2013-07-12
 * 摩尔斯电文加密
 */
public class MorseEncrypt implements Encrypt {
	/**
	 * 点的表示方法
	 */
	public static String[] point={".",",","。","`","、","'","*"};
	/**
	 * 线的表示方法
	 */
	public static String[] line={ "-","_","—","~"};
	
	/**
	 * 分隔的表示方法
	 */
	public static String[] separated={"/","\\","|"," "};
	/**
	 * 明文->密文
	 */
	public static Map<String,String> plaintextMap;
	
	/**
	 * 密文->明文
	 */
	public static Map<String,String> ciphertextMap;
	
	public void initMap(){
		//TODO
		putIntoMap("A", ".-");
	}
	
	
	/**
	 * @param str
	 * @return 替代字符中的点
	 */
	public static String replacePoint(String str){
		StringBuffer sbf=new StringBuffer();
	
		for(int i=1;i<point.length;i++){
			sbf.append(point[i]);
			if(i<point.length-1)
			sbf.append("|");
		}
		str=str.replaceAll(sbf.toString(), ".");
		
		return str;
	}
	
	/**
	 * @param str
	 * @return 替代字符中的线
	 */
	public static String replaceLine(String str){
		StringBuffer sbf=new StringBuffer();
		
		for(int i=1;i<line.length;i++){
			sbf.append(line[i]);
			if(i<line.length-1)
			sbf.append("|");
		}
		str=str.replaceAll(sbf.toString(), "-");
		return str;
	}
	
	/**
	 * @param str
	 * @return 替代字符中的分隔
	 */
	public static String replaceSeparated(String str){
		StringBuffer sbf=new StringBuffer();
		
		for(int i=1;i<separated.length;i++){
			sbf.append(separated[i]);
			if(i<separated.length-1)
			sbf.append("|");
		}
		str=str.replaceAll(sbf.toString(), "/");
			
		return str;
	}
	
	@Override
	public Object encrypt(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object decrypt(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param str
	 * @return 加密字符串
	 */
	public static String encrypt(String str){
		
		
		return str;
	}
	
	/**
	 * @param str
	 * @return 解密字符串
	 */
	public static String decrypt(String str){
		str=replaceSeparated(replaceLine(replacePoint(str)));
		return str;
	}
	/**
	 * @param a 明文
	 * @param b 密文
	 */
	public void putIntoMap(String a,String b){
		plaintextMap.put(a, b);
		ciphertextMap.put(b, a);
	}
}
