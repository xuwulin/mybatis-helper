package com.xwl.mybatishelper.provider;

import com.xwl.mybatishelper.annotation.CryptoField;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 加密字段缓存 TODO
 * @author xwl
 * @since 2022/12/7 21:17
 */
public class CryptoFieldProvider {
    private static final Map<Class<?>, Set<Field>> CRYPTO_FIELD_CACHE = new ConcurrentHashMap<>();

    public static Set<Field> get(Class<?> parameterClass) {
        return CRYPTO_FIELD_CACHE.computeIfAbsent(parameterClass, aClass -> {
            Field[] declaredFields = aClass.getDeclaredFields();
            Set<Field> fieldSet = Arrays.stream(declaredFields).filter(field ->
                    field.isAnnotationPresent(CryptoField.class) && field.getType() == String.class)
                    .collect(Collectors.toSet());
            for (Field field : fieldSet) {
                field.setAccessible(true);
            }
            return fieldSet;
        });
    }
}
