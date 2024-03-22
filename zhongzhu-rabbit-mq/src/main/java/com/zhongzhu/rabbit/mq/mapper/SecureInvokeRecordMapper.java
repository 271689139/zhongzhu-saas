package com.zhongzhu.rabbit.mq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhongzhu.rabbit.mq.domain.SecureInvokeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SecureInvokeRecordMapper extends BaseMapper<SecureInvokeRecord> {


}
