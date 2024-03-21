package com.zhongzhu.business.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongzhu.business.system.domain.SystemVersion;
import com.zhongzhu.business.system.mapper.SystemVersionMapper;
import com.zhongzhu.business.system.service.SystemVersionService;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Service
public class SystemVersionServiceImpl extends ServiceImpl<SystemVersionMapper, SystemVersion> implements SystemVersionService {
    @Override
    public SystemVersion getById(Long id) {
        return getBaseMapper().selectById(id);
    }
}
