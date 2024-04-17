package com.zhongzhu.property.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhongzhu.property.bean.SystemVersionDTO;
import com.zhongzhu.property.domain.SystemVersion;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxiang.dai
 * @since 2024-03-12
 */
public interface SystemVersionService extends IService<SystemVersion> {

    /**
     * 根据id查询
     *
     * @param id 参数
     * @return 返回值
     */
    SystemVersionDTO getById(Long id);
}
