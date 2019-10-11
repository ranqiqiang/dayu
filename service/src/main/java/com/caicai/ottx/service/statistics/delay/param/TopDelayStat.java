package com.caicai.ottx.service.statistics.delay.param;

import java.util.Date;

/**
 * Created by huaseng on 2019/9/2.
 */
public class TopDelayStat {
    private String   channelName;
    private String   pipelineName;
    private Long     channelId;
    private Long     pipelineId;
    private Long     delayTime;
    private Date     lastUpdate;                     // 延迟统计最后一次更新时间
    private Long     statTime;                       // stat统计时间范围,分钟为单位
    private DataStat dbStat   = new DataStat(0L, 0L);
    private DataStat fileStat = new DataStat(0L, 0L);

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
    }

    public Long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Long delayTime) {
        this.delayTime = delayTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getStatTime() {
        return statTime;
    }

    public void setStatTime(Long statTime) {
        this.statTime = statTime;
    }

    public DataStat getDbStat() {
        return dbStat;
    }

    public void setDbStat(DataStat dbStat) {
        this.dbStat = dbStat;
    }

    public DataStat getFileStat() {
        return fileStat;
    }

    public void setFileStat(DataStat fileStat) {
        this.fileStat = fileStat;
    }

    /**
     * 获取延迟统计最后更新时间距当前时间的差值
     */
    public Long getLastUpdateDelay() {
        return (new Date().getTime() - lastUpdate.getTime()) / 1000;
    }

    public static class DataStat {

        public DataStat(Long number, Long size){
            this.number = number;
            this.size = size;
        }

        private Long number;
        private Long size;

        public Long getNumber() {
            return number;
        }

        public void setNumber(Long number) {
            this.number = number;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

    }
}
