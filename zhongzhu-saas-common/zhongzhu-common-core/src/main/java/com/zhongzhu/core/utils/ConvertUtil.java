package com.zhongzhu.core.utils;

import com.zhongzhu.core.i18n.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 对象属性复制工具类.
 *
 * @author shihao.liu
 */
@Slf4j
public class ConvertUtil extends BeanUtils {

    /**
     * 复制对象.
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <T>    泛型
     * @return 目标对象
     */
    public static <T> T sourceToTarget(Object source, Class<T> target) {
        if (ObjectUtil.isNull(source)) {
            return null;
        }
        T targetObject = null;
        try {
            targetObject = instantiateClass(target);
            copyProperties(source, targetObject);
        } catch (Exception e) {
            log.error("转换失败", e);
        }
        return targetObject;
    }

    /**
     * 复制集合.
     *
     * @param sourceList 源集合
     * @param target     目标对象
     * @param <T>        泛型
     * @return 目标集合
     */
    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        return sourceList.stream().map(s -> sourceToTarget(s, target)).toList();
    }

}
