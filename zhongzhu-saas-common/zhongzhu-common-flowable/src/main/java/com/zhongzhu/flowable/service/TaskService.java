package com.zhongzhu.flowable.service;

import com.zhongzhu.core.dto.Datas;
import com.zhongzhu.flowable.dto.AssigneeDTO;
import com.zhongzhu.flowable.dto.TaskAssigneeGetQry;
import com.zhongzhu.flowable.dto.TaskDTO;
import com.zhongzhu.flowable.dto.TaskListQry;
import com.zhongzhu.flowable.dto.task.Audit;
import com.zhongzhu.flowable.dto.task.Start;

/**
 * @author admin
 * 审核类
 */
public interface TaskService {
    /**
     * 发起流程
     *
     * @param start 启动参数
     */
    void start(Start start);

    /**
     * 审核节点
     *
     * @param audit 审核参数
     */
    void audit(Audit audit);

    /**
     * 查询任务流程列表.
     *
     * @param qry 查询任务流程列表参数
     * @return 任务流程列表
     */
    Datas<TaskDTO> list(TaskListQry qry);

    /**
     * 执行查看任务流程执行人员.
     *
     * @param qry 查看任务流程执行人员参数
     * @return 执行人员
     */
    AssigneeDTO getAssigneeByInstanceId(TaskAssigneeGetQry qry);
}
