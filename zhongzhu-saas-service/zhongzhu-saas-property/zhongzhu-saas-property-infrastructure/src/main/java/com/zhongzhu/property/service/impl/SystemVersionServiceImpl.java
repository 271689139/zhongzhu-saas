package com.zhongzhu.property.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongzhu.property.bean.SystemVersionDTO;
import com.zhongzhu.property.domain.SystemVersion;
import com.zhongzhu.property.mapper.SystemVersionMapper;
import com.zhongzhu.property.service.SystemVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxiang.dai
 * @since 2024-03-12
 */
@Slf4j
@Service
public class SystemVersionServiceImpl extends ServiceImpl<SystemVersionMapper, SystemVersion> implements SystemVersionService {

    @Override
    public SystemVersionDTO getById(Long id) {
        SystemVersionDTO dto = new SystemVersionDTO();
        BeanUtils.copyProperties(this.getBaseMapper().selectById(id), dto);
        log.info("查询完毕");
        return dto;
    }
}
