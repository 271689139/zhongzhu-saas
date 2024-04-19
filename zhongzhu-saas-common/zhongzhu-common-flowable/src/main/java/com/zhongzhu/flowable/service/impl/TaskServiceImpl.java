package com.zhongzhu.flowable.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhongzhu.core.context.UserContextHolder;
import com.zhongzhu.core.dto.Datas;
import com.zhongzhu.core.utils.ConvertUtil;
import com.zhongzhu.flowable.dto.AssigneeDTO;
import com.zhongzhu.flowable.dto.TaskAssigneeGetQry;
import com.zhongzhu.flowable.dto.TaskDTO;
import com.zhongzhu.flowable.dto.TaskListQry;
import com.zhongzhu.flowable.dto.task.Audit;
import com.zhongzhu.flowable.dto.task.Start;
import com.zhongzhu.flowable.mapper.TaskMapper;
import com.zhongzhu.flowable.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

/**
 * @author shihao.liu
 * 审核任务
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final RepositoryService repositoryService;

    private final RuntimeService runtimeService;

    private final org.flowable.engine.TaskService taskService;

    private final TaskMapper taskMapper;

    @Override
    public void start(Start start) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionTenantId(UserContextHolder.get().getTenantId().toString())
                .processDefinitionKey(start.getDefinitionKey())
                .latestVersion()
                .singleResult();
        start.checkDefinition(processDefinition);
        start.checkSuspended(processDefinition.isSuspended());
        startInstance(start);
    }

    @Override
    public void audit(Audit audit) {
        Task task = taskService.createTaskQuery()
                .taskTenantId(UserContextHolder.get().getTenantId().toString())
                .taskId(audit.getTaskId())
                .singleResult();
        audit.checkTask(task);
        audit.checkPending(DelegationState.PENDING.equals(task.getDelegationState()));
        complete(audit);
    }

    @Override
    public Datas<TaskDTO> list(TaskListQry qry) {
        String key = qry.getKey();
        String name = qry.getName();
        Long userId = qry.getUserId();
        IPage<TaskDTO> page = new Page<>(qry.getPageNum(), qry.getPageSize());
        IPage<TaskDTO> newPage = taskMapper.getTaskList(page, key, userId, name, UserContextHolder.get().getTenantId());
        return Datas.of(ConvertUtil.sourceToTarget(newPage.getRecords(), TaskDTO.class), newPage.getTotal());
    }

    @Override
    public AssigneeDTO getAssigneeByInstanceId(TaskAssigneeGetQry qry) {
        return new AssigneeDTO(
                taskMapper.getAssigneeByInstanceId(qry.getInstanceId(),UserContextHolder.get().getTenantId())
        );
    }


    private void startInstance(Start start) {
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(
                    start.getDefinitionKey(), start.getBusinessKey(), UserContextHolder.get().getTenantId().toString());
            start.checkInstance(processInstance);
            runtimeService.setProcessInstanceName(processInstance.getId(), start.getInstanceName());
        } catch (Exception e) {
            log.error("错误信息,详情见日志", e);

        }
    }

    private void complete(Audit audit) {
        try {
            if (MapUtils.isNotEmpty(audit.getValues())) {
                taskService.complete(audit.getTaskId(), audit.getValues());
            } else {
                taskService.complete(audit.getTaskId());
            }
        } catch (Exception e) {
            String msg = e.getMessage();
            log.error("错误信息:详情见日志", e);
        }
    }
}
