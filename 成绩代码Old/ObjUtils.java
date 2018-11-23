package utils;

import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射获得或设置bean字段（两种方式：1、通过get或set方法；2、直接反射字段（包含父类字段））
 */
public class ObjUtils{

    /**
     * 首字母大写
     * @param letter
     * @return
     */
    public static String upperFirstLetter(String letter){
        if(StringUtils.isBlank(letter)){
            return letter;
        }
        String firstLetter = letter.substring(0, 1).toUpperCase();
        return firstLetter + letter.substring(1);
    }

    /**
     * 反射获得属性值（通过get方法）
     * @param obj
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object getFieldValue(Object obj,String fieldName) throws Exception{
        if(obj==null || StringUtils.isBlank(fieldName)){
            return obj;
        }
        Class userCla =  obj.getClass();
        String getMethodName = "get" + upperFirstLetter(fieldName);
        Method method = userCla.getMethod(getMethodName);
        if(method!=null){
            obj = method.invoke(obj);
        }
        return obj;
    }

    /**
     * 反射设置属性值（通过set方法）
     * @param obj
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object setFieldValue(Object obj,String fieldName,Object fieldValue) throws Exception{
        if(obj==null || StringUtils.isBlank(fieldName)){
            return obj;
        }
        Class userCla =  obj.getClass();
        String setMethodName = "set" + upperFirstLetter(fieldName);
        Method method = userCla.getMethod(setMethodName,fieldValue.getClass());
        if(method!=null){
            obj = method.invoke(obj,fieldValue);
        }
        return obj;
    }


    /**
     * 反射获得值(直接反射字段取值)
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue1(Object obj,String fieldName) throws Exception{
        if(obj==null || StringUtils.isBlank(fieldName)){
            return obj;
        }
        Class userCla =  obj.getClass();
        do{
            Field[] fs = userCla.getDeclaredFields();
            for(int i = 0 ; i < fs.length; i++){
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                Object objVal= f.get(obj);//得到此属性的值
                if(f.getName().equals(fieldName)){
                    return objVal;
                }
            }
            userCla = userCla.getSuperclass();
        }while (userCla!=null);
        return null;
    }

    /**
     * 反射设置值(直接给字段赋值)
     * @param obj
     * @param fieldName
     * @param fieldValue
     * @throws Exception
     */
    public static Object setFieldValue1(Object obj,String fieldName,Object fieldValue) throws Exception{
        if(obj==null || StringUtils.isBlank(fieldName)){
            return obj;
        }
        Class userCla =  obj.getClass();
        do{
            Field[] fs = userCla.getDeclaredFields();
            for(int i = 0 ; i < fs.length; i++){
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                if(f.getName().equals(fieldName)){
                    f.set(obj,fieldValue);
                    return obj;
                }
            }
            userCla = userCla.getSuperclass();
        }while (userCla!=null);
        return obj;
    }

}
