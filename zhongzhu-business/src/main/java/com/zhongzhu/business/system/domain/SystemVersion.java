package com.zhongzhu.business.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_version")
public class SystemVersion implements Serializable {


    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "system_name")
    private String systemName;

    @TableField(value = "version")
    private String version;
}
