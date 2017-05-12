package cn.lhzs.util;

import cn.lhzs.data.bean.Product;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by IBA-EDV on 2017/5/12.
 */
public class PoiHelper {
    public static List<?> readExcel(String fileName, InputStream inputStream, String type) {
        return null;
    }

    public static Object getFieldMethod(Object pojo, String filed) throws Exception {
        StringBuilder builder = new StringBuilder("get");
        builder.append(filed.substring(0, 1).toUpperCase());
        builder.append(filed.substring(1));
        Method method = pojo.getClass().getMethod(builder.toString());
        return method.invoke(pojo);
    }

    public static <T> void setFieldMethod(Object pojo, String filed, Class<T> valType, T val) throws Exception {
        StringBuilder builder = new StringBuilder("set");
        builder.append(filed.substring(0, 1).toUpperCase());
        builder.append(filed.substring(1));
        Method method = pojo.getClass().getMethod(builder.toString(), valType);
        method.invoke(pojo, val);
    }
}
