package mini.home.uitl.itf;

/**
 * @author K.S
 * @since 2013-06-27
 * 加密算法接口类
 */
public interface Encrypt {
	/**
	 * @param o
	 * @return 加密后的对象
	 */
	public Object encrypt(Object o);
	/**
	 * @param o
	 * @return 解密后的对象
	 */
	public Object decrypt(Object o);
}
