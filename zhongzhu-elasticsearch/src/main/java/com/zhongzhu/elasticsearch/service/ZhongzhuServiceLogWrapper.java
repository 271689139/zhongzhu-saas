package com.zhongzhu.elasticsearch.service;

import com.zhongzhu.elasticsearch.domain.ZhongzhuServiceLog;
import com.zhongzhu.elasticsearch.mapper.ZhongzhuServiceLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author admin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ZhongzhuServiceLogWrapper {

    private final ZhongzhuServiceLogMapper zhongzhuServiceLogMapper;

    /**
     * 批量写入日志
     *
     * @param list 集合
     */
    public void batchInsert(List<ZhongzhuServiceLog> list) {
        zhongzhuServiceLogMapper.insertBatch(list);
    }
}
