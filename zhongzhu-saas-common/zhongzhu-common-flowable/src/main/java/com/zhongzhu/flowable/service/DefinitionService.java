package com.zhongzhu.flowable.service;

import com.zhongzhu.core.dto.Datas;
import com.zhongzhu.flowable.dto.DefinitionDTO;
import com.zhongzhu.flowable.dto.DefinitionListQry;
import com.zhongzhu.flowable.dto.definition.Activate;
import com.zhongzhu.flowable.dto.definition.Deployment;
import com.zhongzhu.flowable.dto.definition.Suspend;

/**
 * @author shihao.liu
 * 流程类
 */
public interface DefinitionService {

    /**
     * 创建流程
     *
     * @param deployment param
     */
    void create(Deployment deployment);

    /**
     * 删除流程
     *
     * @param deploymentId param
     */
    void remove(String deploymentId);

    /**
     * 激活流程
     *
     * @param activate param
     */
    void activate(Activate activate);

    /**
     * 挂起
     *
     * @param suspend param
     */
    void suspend(Suspend suspend);

    /**
     * 查询流程列表.
     *
     * @param qry 查询流程列表参数
     * @return 流程列表
     */
    Datas<DefinitionDTO> findList(DefinitionListQry qry);

    /**
     * 查看流程图
     *
     * @param definitionId 流程定义id
     * @return 流程图
     */
    String findDiagram(String definitionId);
}
