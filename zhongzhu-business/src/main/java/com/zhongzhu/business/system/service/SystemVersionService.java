package com.zhongzhu.business.system.service;


import com.zhongzhu.business.system.domain.SystemVersion;

/**
 * @author admin
 */
public interface SystemVersionService {
    /**
     * 根据id查询
     *
     * @param id id
     * @return SystemVersion
     */
    SystemVersion getById(Long id);
}
