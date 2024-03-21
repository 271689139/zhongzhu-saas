package com.zhongzhu.elasticsearch.mapper;

import com.zhongzhu.elasticsearch.domain.ZhongzhuServiceLog;
import org.dromara.easyes.core.core.BaseEsMapper;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * 系统日志
 */
@Component
public interface ZhongzhuServiceLogMapper extends BaseEsMapper<ZhongzhuServiceLog> {
}
