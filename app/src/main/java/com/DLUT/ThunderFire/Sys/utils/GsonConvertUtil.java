package com.DLUT.ThunderFire.Sys.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson对象解析
 */
public class GsonConvertUtil {

    static Gson gson = new Gson();
    //解析 Json对象
    public static <T> T parseJson(Class<T> type,String jsonStr)throws Exception{
        return gson.fromJson(jsonStr,type);
    }
    // 将Json数组解析成相应的映射对象列表
    public static <T> ArrayList<T> parseJsonArray(Class<T> myClass,String jsonStr)throws Exception{
        Type type = new ListParameterizedType(myClass);
        return gson.fromJson(jsonStr, type);
    }

    private static class ListParameterizedType implements ParameterizedType {
        private Type type;
        private ListParameterizedType(Type type) {
            this.type = type;
        }
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] {type};
        }
        @Override
        public Type getRawType() {
            return ArrayList.class;
        }
        @Override
        public Type getOwnerType() {
            return null;
        }
        // implement equals method too! (as per javadoc)
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
