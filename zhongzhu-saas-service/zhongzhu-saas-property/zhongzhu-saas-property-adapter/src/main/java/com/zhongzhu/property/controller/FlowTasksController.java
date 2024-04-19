package com.zhongzhu.property.controller;

import com.zhongzhu.core.dto.Datas;
import com.zhongzhu.core.request.GenericBaseRequest;
import com.zhongzhu.core.response.GenericBaseResponse;
import com.zhongzhu.flowable.dto.AssigneeDTO;
import com.zhongzhu.flowable.dto.TaskAssigneeGetQry;
import com.zhongzhu.flowable.dto.TaskDTO;
import com.zhongzhu.flowable.dto.TaskListQry;
import com.zhongzhu.flowable.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author shihao.liu
 */
@RestController
@Tag(name = "TasksController", description = "任务流程")
@RequiredArgsConstructor
@RequestMapping("v1/tasks")
public class FlowTasksController {

    private final TaskService taskService;

    @PostMapping("list")
    @Operation(summary = "任务流程", description = "查询任务流程列表")
    public GenericBaseResponse<Datas<TaskDTO>> list(@RequestBody GenericBaseRequest<TaskListQry> qry) {
        return GenericBaseResponse.ok(taskService.list(qry.getParam()));
    }


    @GetMapping("{instanceId}/assignee")
    @Operation(summary = "任务流程", description = "流程人员")
    public GenericBaseResponse<AssigneeDTO> assignee(@PathVariable("instanceId") String instanceId) {
        return GenericBaseResponse.ok(taskService.getAssigneeByInstanceId(new TaskAssigneeGetQry(instanceId)));
    }
}
