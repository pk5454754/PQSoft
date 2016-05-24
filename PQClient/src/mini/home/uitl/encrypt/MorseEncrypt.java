package mini.home.uitl.encrypt;

import java.io.ObjectOutputStream.PutField;
import java.util.Map;

import mini.home.uitl.itf.Encrypt;

/**
 * @author K.S
 * @since 2013-07-12
 * Ħ��˹���ļ���
 */
public class MorseEncrypt implements Encrypt {
	/**
	 * ��ı�ʾ����
	 */
	public static String[] point={".",",","��","`","��","'","*"};
	/**
	 * �ߵı�ʾ����
	 */
	public static String[] line={ "-","_","��","~"};
	
	/**
	 * �ָ��ı�ʾ����
	 */
	public static String[] separated={"/","\\","|"," "};
	/**
	 * ����->����
	 */
	public static Map<String,String> plaintextMap;
	
	/**
	 * ����->����
	 */
	public static Map<String,String> ciphertextMap;
	
	public void initMap(){
		//TODO
		putIntoMap("A", ".-");
	}
	
	
	/**
	 * @param str
	 * @return ����ַ��еĵ�
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
	 * @return ����ַ��е���
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
	 * @return ����ַ��еķָ�
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
	 * @return �����ַ���
	 */
	public static String encrypt(String str){
		
		
		return str;
	}
	
	/**
	 * @param str
	 * @return �����ַ���
	 */
	public static String decrypt(String str){
		str=replaceSeparated(replaceLine(replacePoint(str)));
		return str;
	}
	/**
	 * @param a ����
	 * @param b ����
	 */
	public void putIntoMap(String a,String b){
		plaintextMap.put(a, b);
		ciphertextMap.put(b, a);
	}
}
