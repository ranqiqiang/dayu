package com.caicai.ottx.service.autokeeper;

import com.alibaba.otter.shared.common.model.autokeeper.AutoKeeperConnectionStat;
import com.alibaba.otter.shared.common.model.autokeeper.AutoKeeperServerStat;

import java.util.List;

/**
 *  zookeeper状态查询接口
 * Created by huaseng on 2019/9/20.
 */
public interface AutoKeeperStatService {

    /**
     * 根据serverIp查询对应的统计信息，包括Connection/Watch/Ephemeral等统计信息
     *
     * @param serverIp
     * @return
     */
    public AutoKeeperServerStat findServerStat(String serverIp);

    /**
     * 根据sessionId查询对应的统计信息，包括详细的Connection/Watch/Ephemeral等统计信息
     *
     * @param sessionId
     * @return
     */
    public AutoKeeperServerStat findServerStatBySessionId(String sessionId);

    /**
     * 根据sessionId查询对应的connction链接
     *
     * @param sessionId
     * @return
     */
    public AutoKeeperConnectionStat findConnectionBySessionId(String sessionId);

    /**
     * 根据临时节点路径查询对应的connection统计信息
     *
     * @param path
     * @return
     */
    public AutoKeeperConnectionStat findConnectionByEphemeralPath(String path);

    /**
     * 根据watcher路径查询对应的connection统计信息
     *
     * @param path
     * @return
     */
    public List<AutoKeeperConnectionStat> findConnectionByWatcherPath(String path);
}
