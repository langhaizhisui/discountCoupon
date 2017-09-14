package cn.lhzs.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * bean工具类，spring BeanUtils 复制属性，扩展功能，过滤Null属性
 */
public class BeanUtil {

	/**
	 * Bean properties copy ignore null.
	 *
	 * @param src source
	 * @param target target
	 *            
	 */
	public static void copyPropertiesIgnoreNull(Object src, Object target) {
		org.springframework.beans.BeanUtils.copyProperties(src, target, getNamesOfNullProperties(src));
	}

	/**
	 * COPY bean 属性时过滤空NULL 属性，如果原bean含有boolean属性值，正确转换成目标bean的int类型
	 * 
	 * @param src
	 * @param target void
	 */
	public static void copyPropertiesIgnoreNullAndBool2Int(Object src, Object target) {
		org.springframework.beans.BeanUtils.copyProperties(src, target, getNamesOfNullProperties(src));
		copyPropertiesBool2Int(src, target);
	}
	
	/**
	 * copybean如果src中含有boolean类型，转为target类型为int，需要修改
	 * 
	 * @param src
	 * @param target void
	 */
	public static void copyPropertiesBool2Int(Object src, Object target){
		Class<?> targetClazz = target.getClass();
		Class<?> srcClazz = src.getClass();
		Field[] fields = srcClazz.getDeclaredFields();
		for (Field srcField : fields) {
			srcField.setAccessible(true);
			if (srcField.getType().isAssignableFrom(Boolean.class)) {
				try {
					Field targetField = targetClazz.getDeclaredField(srcField.getName());
					targetField.setAccessible(true);
					if (!targetField.getType().isAssignableFrom(Boolean.class)) {
						String methodName = "set" + StringUtil.firstCharUpperCase(targetField.getName());
						Method method = targetClazz.getMethod(methodName, targetField.getType());
						Boolean val = (boolean)srcField.get(src);
						method.invoke(target, val?1:0);
					}
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Get names of null properties
	 *
	 * @param source
	 *            source object
	 * @return
	 */
	private static String[] getNamesOfNullProperties(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

}
