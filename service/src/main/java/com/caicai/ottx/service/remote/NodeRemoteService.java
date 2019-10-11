package com.caicai.ottx.service.remote;

import java.util.List;

/**
 *  查询node节点的一些运行信息
 * Created by huaseng on 2019/8/23.
 */
public interface NodeRemoteService {
    /**
     * 返回当前运行pipeline数量，可能只运行S/E/T/L的某个模块
     */
    public int getRunningPipelineCount(Long nid);

    /**
     * 返回当前运行中的pipeline的id列表
     */
    public List<Long> getRunningPipelines(Long nid);

    /**
     * 获取当前使用的heap区大小
     */
    public String getHeapMemoryUsage(Long nid);

    /**
     * 获取系统对应的load
     */
    public String getNodeSystemInfo(Long nid);

    /**
     * 获取node节点对应的版本信息
     */
    public String getNodeVersionInfo(Long nid);

    /**
     * 获取node共享线程线程池的线程数
     */
    public int getThreadPoolSize(Long nid);

    /**
     * 获取当前node共享线程的当前活跃线程数
     */
    public int getThreadActiveSize(Long nid);

    /**
     * 设置是否开启profile统计
     */
    public void setProfile(Long nid, boolean profile);

    /**
     * 设置对应的s/e/t/l seda模型的线程池大小
     */
    public void setThreadPoolSize(Long nid, int size);

    /**
     * 当前节点是否运行select
     */
    public boolean isSelectRunning(Long nid, Long pipelineId);

    /**
     * 当前节点是否运行extract
     */
    public boolean isExtractRunning(Long nid, Long pipelineId);

    /**
     * 当前节点是否运行transform
     */
    public boolean isTransformRunning(Long nid, Long pipelineId);

    /**
     * 当前节点是否运行load
     */
    public boolean isLoadRunning(Long nid, Long pipelineId);

    /**
     * select stage统计信息
     */
    public String selectStageAggregation(Long nid, Long pipelineId);

    /**
     * extract stage统计信息
     */
    public String extractStageAggregation(Long nid, Long pipelineId);

    /**
     * transform stage统计信息
     */
    public String transformStageAggregation(Long nid, Long pipelineId);

    /**
     * load stage统计信息
     */
    public String loadStageAggregation(Long nid, Long pipelineId);

    /**
     * select pending队列信息
     */
    public String selectPendingProcess(Long nid, Long pipelineId);

    /**
     * extract pending队列信息
     */
    public String extractPendingProcess(Long nid, Long pipelineId);

    /**
     * transform pending队列信息
     */
    public String transformPendingProcess(Long nid, Long pipelineId);

    /**
     * load pending队列信息
     */
    public String loadPendingProcess(Long nid, Long pipelineId);
}
