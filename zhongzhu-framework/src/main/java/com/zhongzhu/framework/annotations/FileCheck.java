package com.zhongzhu.framework.annotations;

import com.zhongzhu.framework.enums.FileType;

import java.lang.annotation.*;

/**
 * 文件校验
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface FileCheck {
    /**
     * 格式校验不通过提示信息
     *
     * @return
     */
    String typeCheckErrorMsg() default "文件格式不正确";

    /*
    校验方式
     */
    CheckType type() default CheckType.SUFFIX;

    /**
     * 支持的文件后缀
     *
     */
    String[] supportedSuffixes() default {};

    /**
     * 支持的文件类型
     *
     */
    FileType[] supportedFileTypes() default {};

    /**
     * 文件的最大大小
     *
     * @return
     */
    long maxSize() default 10;

    /**
     * 文件大小的单位
     *
     * @return
     */
    SizeUnit sizeUnit() default SizeUnit.MB;

    /**
     * 文件大小
     *
     * @return
     */
    String sizeCheckErrorMsg() default "文件大小超出限制";

    enum CheckType {
        /**
         * 仅校验后缀
         */
        SUFFIX,
        /**
         * 校验文件头(魔数)
         */
        MAGIC_NUMBER
    }

    /**
     * 文件大小单位
     */
    enum SizeUnit {
        BYTE,
        KB,
        MB
    }
}
