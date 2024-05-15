package com.xwl.mybatishelper.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 字段工具类
 *
 * @author xwl
 * @since 2024/05/13 13:19
 */
public class FieldUtils {

    /**
     * 查找某个类上标注某个注解的字段
     *
     * @param clazz           要查找的类类
     * @param annotationClass 注解类
     * @return
     */
    public static List<Field> findAnnotatedFields(Class<?> clazz, Class<?> annotationClass) {
        List<Field> annotatedFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent((Class<? extends Annotation>) annotationClass)) {
                annotatedFields.add(field);
            }
        }
        return annotatedFields;
    }

    /**
     * 获取标注某个注解的字段集合的jsonObject
     *
     * @param fields 字段集合
     * @param object 字段所在类的实例
     * @return
     * @throws IllegalAccessException
     */
    public static JSONObject getAnnotatedFieldJsonObject(List<Field> fields, Object object) throws IllegalAccessException {
        JSONObject jsonObject = new JSONObject();
        for (Field cryptoField : fields) {
            cryptoField.setAccessible(true);
            String cryptoFieldName = cryptoField.getName();
            Object cryptoFieldValue = cryptoField.get(object);
            jsonObject.set(cryptoFieldName, cryptoFieldValue);
        }
        return jsonObject;
    }

    /**
     * 获取标注某个注解的字段集合的json字符串
     *
     * @param fields 字段集合
     * @param object 字段所在类的实例
     * @return
     * @throws IllegalAccessException
     */
    public static String getAnnotatedFieldJsonStr(List<Field> fields, Object object) throws IllegalAccessException {
        JSONObject jsonObject = getAnnotatedFieldJsonObject(fields, object);
        return JSONUtil.toJsonStr(jsonObject);
    }
}
