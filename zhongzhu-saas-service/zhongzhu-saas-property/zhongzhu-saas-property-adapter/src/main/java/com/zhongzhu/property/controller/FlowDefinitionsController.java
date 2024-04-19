package com.zhongzhu.property.controller;

import com.zhongzhu.core.dto.Datas;
import com.zhongzhu.core.request.GenericBaseRequest;
import com.zhongzhu.core.response.GenericBaseResponse;
import com.zhongzhu.flowable.dto.DefinitionDTO;
import com.zhongzhu.flowable.dto.DefinitionListQry;
import com.zhongzhu.flowable.dto.definition.Activate;
import com.zhongzhu.flowable.dto.definition.Deployment;
import com.zhongzhu.flowable.dto.definition.Suspend;
import com.zhongzhu.flowable.service.DefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * @author shihao.liu
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "DefinitionsController", description = "流程定义")
@RequestMapping("v1/definitions")
public class FlowDefinitionsController {

    private final DefinitionService definitionService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "流程定义", description = "新增流程")
    public GenericBaseResponse<Boolean> create(@RequestPart("file") MultipartFile file) {
        definitionService.create(new Deployment(file));
        return GenericBaseResponse.ok(Boolean.TRUE);
    }

    @PostMapping("list")
    @Operation(summary = "流程定义", description = "查询流程列表")
    public GenericBaseResponse<Datas<DefinitionDTO>> findList(@RequestBody GenericBaseRequest<DefinitionListQry> request) {
        return GenericBaseResponse.ok(definitionService.findList(request.getParam()));
    }

    @GetMapping("{definitionId}/diagram")
    @Operation(summary = "流程定义", description = "查看流程图")
    public GenericBaseResponse<String> findDiagram(@PathVariable("definitionId") String definitionId) {
        return GenericBaseResponse.ok(definitionService.findDiagram(definitionId));
    }

    @DeleteMapping("{deploymentId}")
    @Operation(summary = "流程定义", description = "删除流程")
    public GenericBaseResponse<Boolean> remove(@PathVariable("deploymentId") String deploymentId) {
        definitionService.remove(deploymentId);
        return GenericBaseResponse.ok(Boolean.TRUE);
    }

    @PutMapping("{definitionId}/suspend")
    @Operation(summary = "流程定义", description = "挂起流程")
    public GenericBaseResponse<Boolean> suspend(@PathVariable("definitionId") String definitionId) {
        definitionService.suspend(Suspend.builder().definitionId(definitionId).build());
        return GenericBaseResponse.ok(Boolean.TRUE);
    }

    @PutMapping("{definitionId}/activate")
    @Operation(summary = "流程定义", description = "激活流程")
    public GenericBaseResponse<Boolean> activate(@PathVariable("definitionId") String definitionId) {
        definitionService.activate(Activate.builder().definitionId(definitionId).build());
        return GenericBaseResponse.ok(Boolean.TRUE);
    }

}
