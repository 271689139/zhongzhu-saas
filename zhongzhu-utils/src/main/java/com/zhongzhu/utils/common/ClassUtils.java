package com.zhongzhu.utils.common;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 * Class工具类
 */
public class ClassUtils {

    private static final char PACKAGE_SEPARATOR = '.';


    /**
     * 代理 class 的名称
     */
    private static final List<String> PROXY_CLASS_NAMES = Arrays.asList("net.sf.cglib.proxy.Factory"
            // cglib
            , "org.springframework.cglib.proxy.Factory", "javassist.util.proxy.ProxyObject"
            // javassist
            , "org.apache.ibatis.javassist.util.proxy.ProxyObject");


    /**
     * 获取实际的Class
     *
     * @param clazz clazz
     * @return Class
     */
    public static Class<?> getActualClass(Class<?> clazz) {
        return isProxy(clazz) ? clazz.getSuperclass() : clazz;
    }

    public static Class<?> getActualClass(Object object) {
        Assert.notNull(object, "Instance must not be null");
        return getActualClass(object.getClass());
    }

    /**
     * 判断一个Class是否是代理生成的
     *
     * @param clazz 类型
     * @return true | false
     */
    public static boolean isProxy(Class<?> clazz) {
        if (clazz != null) {
            for (Class<?> cls : clazz.getInterfaces()) {
                if (PROXY_CLASS_NAMES.contains(cls.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据类的全限定名获取Class
     *
     * @param fqClassName 全限定名
     * @return Class
     */
    public static Class<?> toClass(String fqClassName) {
        try {
            return Class.forName(fqClassName, false, getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(fqClassName);
            } catch (ClassNotFoundException ex) {
                throw new IllegalArgumentException("Class not found");
            }
        }
    }

    public static String getPackageName(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return getPackageName(clazz.getName());
    }

    /**
     * 获取包名,如果类是在默认的包下返回空字符串 {@code ""}
     *
     * @param fqClassName 类的全路径名,例如 java.lang.String
     * @return 包名, 例如 java.lang
     */
    @NonNull
    public static String getPackageName(String fqClassName) {
        Assert.notNull(fqClassName, "Class name must not be null");
        int lastDotIndex = fqClassName.lastIndexOf(PACKAGE_SEPARATOR);
        return (lastDotIndex != -1) ? fqClassName.substring(0, lastDotIndex) : "";
    }

    /**
     * 获取默认的类加载器,只有当系统类加载器不可获取时才会返回null
     *
     * @return 类加载器
     */
    @Nullable
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (classLoader == null) {
            // No thread context class loader -> use class loader of this class.
            classLoader = ClassUtils.class.getClassLoader();
            if (classLoader == null) {
                try {
                    classLoader = ClassLoader.getSystemClassLoader();
                } catch (Exception e) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return classLoader;
    }
}
