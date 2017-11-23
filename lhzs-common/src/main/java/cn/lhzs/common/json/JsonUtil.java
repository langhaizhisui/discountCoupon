//package cn.lhzs.common.json;
//
//
//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//import java.io.StringWriter;
//import java.util.*;
//
//public class JsonUtil {
//
//    public static String serializable(Object obj) {
//        StringWriter writer = new StringWriter();
//        try {
//            mapper.writeValue(writer,obj);
//            return writer.toString();
//        } catch (JsonGenerationException e) {
//        } catch (JsonMappingException e) {
//        } catch (IOException e) {
//        } finally {
//            try {
//                writer.close();
//            } catch (IOException e) {
//            }
//        }
//        return null;
//    }
//
//
//    private static ObjectMapper mapper = new ObjectMapper();
//
//    public static <T> T unSerializable(Class<T> clazz, String str) {
//        // long time = System.currentTimeMillis();
//        try {
//            if (str == null) return null;
//            T object = mapper.readValue(str, clazz);
//            // time = System.currentTimeMillis() - time;
//            // LOG.info("serializable 时间:{0}", time);
//            return object;
//        } catch (JsonMappingException e) {
//        } catch (JsonGenerationException e) {
//        } catch (IOException e) {
//        } catch (Exception e) {
//        }
//        return null;
//    }
//
//    public static <T> List<T> unSerializableList(Class<T> clazz, String str) {
//        try {
//            if (str == null) return null;
//            JavaType javaType = getCollectionType(ArrayList.class, clazz);
//            List<T> object = (List<T>) mapper.readValue(str, javaType);
//            return object;
//        } catch (JsonMappingException e) {
//        } catch (JsonGenerationException e) {
//        } catch (IOException e) {
//        }
//        return null;
//    }
//
//    public static <T> LinkedList<T> unSerializableLinkList(Class<T> clazz, String str) {
//         long time = System.currentTimeMillis();
//        try {
//            if (str == null) return null;
//            JavaType javaType = getCollectionType(LinkedList.class, clazz);
//            LinkedList<T> object = (LinkedList<T>) mapper.readValue(str, javaType);
//            // time = System.currentTimeMillis() - time;
//            // LOG.info("serializable 时间:{0}", time);
//            return object;
//        } catch (JsonMappingException e) {
//        } catch (JsonGenerationException e) {
//        } catch (IOException e) {
//        }
//        return null;
//    }
//
//
//    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
//        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
//    }
//
//    public static <K, T> Map<K, T> unSerializableMap(Class<K> keyClazz, Class<T> valueClazz, String str) {
//        // long time = System.currentTimeMillis();
//        try {
//            if (str == null || "".equals(str)) return null;
//            JavaType javaType = getCollectionType(Map.class, keyClazz, valueClazz);
//            Map<K, T> object = (Map<K, T>) mapper.readValue(str, javaType);
//            // time = System.currentTimeMillis() - time;
//            // LOG.info("serializable 时间:{0}", time);
//            return object;
//        } catch (JsonMappingException e) {
//        } catch (JsonGenerationException e) {
//        } catch (IOException e) {
//        }
//        return null;
//    }
//
////
////
////    public static void main(String[] args) {
////        Map<Integer, Object> map = new HashMap<>();
////        map.put(1, 10);
////        map.put(2, 30);
////        String str = serializable(map);
////        System.out.println("str = " + str);
////        Map<Integer, Object> map2 = unSerializableMap(Integer.class, Object.class, str);
////        System.out.println("str = " + map2);
////
////    }
//}
