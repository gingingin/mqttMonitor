package com.yzwy.infrastructure.message;

import com.yzwy.infrastructure.message.model.BaseParam;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gin on 2017/6/1.
 */
public class ClassUtil {


    /**  获取父类搜有属性
          * 循环向上转型, 获取对象的 DeclaredField
          * @param object : 子类对象
          * @return 父类中的属性数组
          */
    public static Field[] getDeclaredField(Object object){
        Field[] field = null ;

        Class<?> clazz = object.getClass() ;  
          
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                field = ArrayUtils.addAll(field, clazz.getDeclaredFields()) ;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }   
        }  
      
        return field;
    }

    /**
     * 将一个类查询方式加入map（属性值为int型时，0时不加入，
     * 属性值为String型或Long时为null和“”不加入）
     *
     */
    public static Map<String, Object> getParameterMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        if(obj==null){
            return null;
        }
//        Field[] fields = obj.getClass().getDeclaredFields();
        Field[] fields = getDeclaredField(obj);
        for(Field field : fields){
            String fieldName =  field.getName();
//            if(getValueByFieldName(fieldName,obj)!=null)
            field.setAccessible(true);
                map.put(fieldName, field.get(obj));
        }

        return map;

    }
    /**
     * 根据属性名获取该类此属性的值
     * @param fieldName
     * @param object
     * @return
     */
    private static Object getValueByFieldName(String fieldName,Object object){
        String firstLetter=fieldName.substring(0,1).toUpperCase();
        String getter = "get"+firstLetter+fieldName.substring(1);
        try {
            Method method = object.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(object, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }

    }

    public static void main(String[] args) throws IllegalAccessException {
        BaseParam param = new BaseParam();
        param.setFunctionCode("1122");
        Map map = getParameterMap(param);
        System.out.println(map);
    }
}
