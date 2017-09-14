package cn.lhzs.common.util;

import java.io.*;

/**
 * 序列化辅助类
 *
 * @author sonic.liu
 * @version 1.0
 */
public final class SerializeUtil {
	private SerializeUtil() {
	}

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	public static final byte[] toBytes(Object object) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			return baos.toByteArray();
		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			try {
				oos.close();
				baos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static final Object toObject(byte[] bytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			try {
				ois.close();
				bais.close();
			} catch (Exception e) {
			}
		}
	}
}
