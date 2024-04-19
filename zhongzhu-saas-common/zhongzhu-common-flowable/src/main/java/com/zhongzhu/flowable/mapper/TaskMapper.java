package com.zhongzhu.flowable.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhongzhu.flowable.dto.TaskDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import static com.zhongzhu.core.event.Identifier.*;
import static com.zhongzhu.core.i18n.SysConstants.*;


/**
 * 任务流程.
 *
 * @author shihao.liu
 */
@Mapper
@Repository
public interface TaskMapper {

    /**
     * 根据租户ID查看执行人员.
     *
     * @param instanceId 实例ID
     * @param tenantId   租户ID
     * @return 执行人员
     */
    String getAssigneeByInstanceId(@Param("instanceId") String instanceId, @Param(TENANT_ID) Long tenantId);

    /**
     * 查询任务流程列表.
     *
     * @param page     分页参数
     * @param key      key
     * @param userId   用户ID
     * @param name     流程名称
     * @param tenantId 租户ID
     * @return 任务流程列表
     */
    IPage<TaskDTO> getTaskList(IPage<TaskDTO> page, @Param("key") String key, @Param(USER_ID) Long userId, @Param("name") String name, @Param(TENANT_ID) Long tenantId);

}
