package com.zhongzhu.flowable.service.impl;

import com.zhongzhu.core.context.UserContextHolder;
import com.zhongzhu.core.dto.Datas;
import com.zhongzhu.core.i18n.SysConstants;
import com.zhongzhu.core.utils.StringUtil;
import com.zhongzhu.flowable.dto.DefinitionDTO;
import com.zhongzhu.flowable.dto.DefinitionListQry;
import com.zhongzhu.flowable.dto.definition.Activate;
import com.zhongzhu.flowable.dto.definition.Deployment;
import com.zhongzhu.flowable.dto.definition.Suspend;
import com.zhongzhu.flowable.service.DefinitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.utils.Base64;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.util.io.InputStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author shihao.liu
 * 流程定义
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefinitionServiceImpl implements DefinitionService {

    private final RepositoryService repositoryService;

    @Override
    public void create(Deployment deployment) {
        BpmnXMLConverter converter = new BpmnXMLConverter();
        InputStreamSource inputStreamSource = new InputStreamSource(deployment.getInputStream());
        BpmnModel bpmnModel = converter.convertToBpmnModel(inputStreamSource, true, true);
        Process process = bpmnModel.getProcesses().stream().findFirst().orElse(new Process());
        deployment.modify(process.getId(), process.getName());
        long count = repositoryService.createDeploymentQuery()
                .deploymentTenantId(UserContextHolder.get().getTenantId().toString())
                .deploymentKey(deployment.getKey())
                .count();
        deployment.checkKey(count);
        create(deployment, bpmnModel);
    }

    @Override
    public void remove(String deploymentId) {
        try {
            // true允许级联删除 不设置会导致数据库关联异常
            repositoryService.deleteDeployment(deploymentId, true);
        } catch (Exception e) {
            log.error("错误信息，详情见日志", e);
        }
    }


    @Override
    public void activate(Activate activate) {
        String definitionId = activate.getDefinitionId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionTenantId(UserContextHolder.get().getTenantId().toString())
                .processDefinitionId(definitionId)
                .singleResult();
        activate.checkActivated(!processDefinition.isSuspended());
        activate(definitionId);
    }


    @Override
    public void suspend(Suspend suspend) {
        String definitionId = suspend.getDefinitionId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionTenantId(UserContextHolder.get().getTenantId().toString())
                .processDefinitionId(definitionId)
                .singleResult();
        suspend.checkSuspended(processDefinition.isSuspended());
        suspend(definitionId);
    }

    @Override
    public Datas<DefinitionDTO> findList(DefinitionListQry qry) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
                .processDefinitionTenantId(UserContextHolder.get().getTenantId().toString())
                .latestVersion()
                .orderByProcessDefinitionKey()
                .asc();
        String name = qry.getName();
        if (StringUtil.isNotEmpty(name)) {
            query.processDefinitionNameLike(SysConstants.PERCENT.concat(name.concat(SysConstants.PERCENT)));
        }
        int pageNum = qry.getPageNum();
        int pageSize = qry.getPageSize();
        List<ProcessDefinition> definitionList = query.listPage(pageSize * (pageNum - 1), pageSize);
        return Datas.of(definitionList.stream().map(this::convert).toList(), query.count());
    }

    @Override
    public String findDiagram(String definitionId) {
        // 获取图片流
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(definitionId);
        // 输出为图片
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", Collections.emptyList(),
                Collections.emptyList(), "宋体",
                "宋体",
                "宋体",
                null, 1.0, false);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            if (null != image) {
                ImageIO.write(image, "png", outputStream);
            }
            return Base64.encodeBase64String(outputStream.toByteArray());
        } catch (IOException e) {
            log.error("错误信息:详情见日志", e);
            throw new RuntimeException("流程图查看失败");
        }
    }


    /**
     * 挂起流程.
     *
     * @param definitionId 定义ID
     */
    private void suspend(String definitionId) {
        try {
            // 挂起
            repositoryService.suspendProcessDefinitionById(definitionId, true, null);
        } catch (Exception e) {
            log.error("错误信息：详情见日志", e);
        }
    }

    /**
     * 激活流程.
     *
     * @param definitionId 定义ID
     */
    private void activate(String definitionId) {
        try {
            // 激活
            repositoryService.activateProcessDefinitionById(definitionId, true, null);
        } catch (Exception e) {
            log.error("错误信息:详情见日志", e);
        }
    }


    /**
     * 部署流程.
     *
     * @param deployment 部署
     * @param bpmnModel  模型
     */
    private void create(Deployment deployment, BpmnModel bpmnModel) {
        try {
            repositoryService.createDeployment()
                    .tenantId(UserContextHolder.get().getTenantId().toString())
                    .name(deployment.getName())
                    .key(deployment.getKey())
                    .addBpmnModel(deployment.getName(), bpmnModel)
                    .deploy();
        } catch (Exception e) {
            log.error("错误信息，详情见日志", e);
        }
    }

    /**
     * 转换为定义流程命令请求.
     *
     * @param definition 流程对象
     * @return 定义流程命令请求
     */
    private DefinitionDTO convert(ProcessDefinition definition) {
        return DefinitionDTO.builder()
                .definitionId(definition.getId())
                .processKey(definition.getKey())
                .processName(definition.getName())
                .deploymentId(definition.getDeploymentId())
                .isSuspended(definition.isSuspended())
                .build();
    }
}
