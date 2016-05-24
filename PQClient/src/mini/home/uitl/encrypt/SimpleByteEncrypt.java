package mini.home.uitl.encrypt;

import java.io.UnsupportedEncodingException;

import mini.home.uitl.itf.Encrypt;

/**
 * @author K.S
 * @since 2013-06-27
 * �򵥵ذ��ַ���ת��byte������
 */
public class SimpleByteEncrypt implements Encrypt{
	
	/**
	 * @param str
	 * @return �����ַ���
	 */
	public static String encrypt(String str){
		byte[] bt=str.getBytes();
		str="";
		String[] bti=new String[bt.length];
		int temp;
		for(int i=0;i<bt.length;i++){
			temp=bt[i]&0xFF;
			if(temp<10){
				bti[i]="00"+temp;
			}else if(temp<100){
				bti[i]="0"+temp;
			}else{
				bti[i]=""+temp;
			}
			str+=bti[i];
		}
		return str;
	}
	/**
	 * @param str
	 * @return �����ַ���
	 * @throws UnsupportedEncodingException
	 */
	public static String decrypt(String str){
		int btlength = str.length()/3;
		int tempint;
		byte[] bt=new byte[btlength];
		for(int i=0;i<btlength;i++){
			tempint=Integer.parseInt(str.substring(i*3, i*3+3));
			bt[i]=(byte) tempint;
		}
		return new String(bt);
	}
	/**
	 * @param input
	 * @return �޷�����תint
	 */
	public static int unsignToint(int input){
		if(input>128){
			input=input-256;
		}
		return input;
	}

	@Override
	public Object encrypt(Object o) {
		// TODO Auto-generated method stub
		
		return this.encrypt(o);
	}

	@Override
	public Object decrypt(Object o) {
		// TODO Auto-generated method stub
		return this.decrypt(o);
	}
}
