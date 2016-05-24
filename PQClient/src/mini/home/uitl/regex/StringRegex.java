package mini.home.uitl.regex;

/**
 * @author K.S
 * @since 2013-07-15 �ַ�У����
 */
public class StringRegex {
	/**
	 * @param str
	 * @return ������
	 */
	public static boolean isNum(String str) {
		if (str.matches("[0-9]*")) {
			return true;
		}
		return false;
	}

	/**
	 * @param str
	 * @return �Ƿ�IP
	 */
	public static boolean isIP(String str) {
		if (str.matches("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})")) {
			return true;
		}
		return false;
	}

	

}
