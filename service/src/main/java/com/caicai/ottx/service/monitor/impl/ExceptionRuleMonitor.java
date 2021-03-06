/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.caicai.ottx.service.monitor.impl;

import com.alibaba.otter.shared.common.model.config.alarm.AlarmRule;
import com.alibaba.otter.shared.common.model.config.alarm.AlarmRuleStatus;
import com.alibaba.otter.shared.common.model.config.alarm.MonitorName;
import com.alibaba.otter.shared.common.model.config.pipeline.Pipeline;
import com.alibaba.otter.shared.communication.model.arbitrate.NodeAlarmEvent;
import com.caicai.ottx.service.config.alarm.AlarmRuleService;
import com.caicai.ottx.service.config.pipeline.PipelineService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */
public class ExceptionRuleMonitor extends AbstractRuleMonitor {

    private static final String MESAGE_FORMAT = "pid:%s channelid:%s nid:%s exception:%s";

    @Autowired
    private AlarmRuleService alarmRuleService;

    @Autowired
    private PipelineService pipelineService;

    // ExceptionRuleMonitor(){
    // MonitorRuleExplorerRegisty.register(MonitorName.EXCEPTON, this);
    // }

    @Override
    public void explore(List<AlarmRule> rules) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void feed(Object data, Long pipelineId) {
        if (!(data instanceof NodeAlarmEvent)) {
            return;
        }
        NodeAlarmEvent alarmEvent = (NodeAlarmEvent) data;
        Pipeline pipeline = pipelineService.findById(alarmEvent.getPipelineId());
        // 异常一定需要记录日志
        String message = String.format(MESAGE_FORMAT, alarmEvent.getPipelineId(),pipeline.getChannelId(), alarmEvent.getNid(),
                                       alarmEvent.getMessage());
        logRecordAlarm(pipelineId, alarmEvent.getNid(), MonitorName.EXCEPTION, message);
        // 报警检查
        List<AlarmRule> rules = alarmRuleService.getAlarmRules(pipelineId, AlarmRuleStatus.ENABLE);

        // TODO 需要给 alarmRuleService 提需求
        Date now = new Date();
        List<AlarmRule> exceptionRules = new ArrayList<AlarmRule>();
        for (AlarmRule rule : rules) {
            if (MonitorName.EXCEPTION.equals(rule.getMonitorName()) && checkEnable(rule, now)) {
                exceptionRules.add(rule);
            }
        }

        if (CollectionUtils.isEmpty(exceptionRules)) {
            return;
        }

        for (AlarmRule rule : exceptionRules) {
            check(rule, alarmEvent);
        }
    }

    private boolean checkEnable(AlarmRule rule, Date now) {
        return rule.getPauseTime() == null || rule.getPauseTime().before(now);
    }

    private void check(AlarmRule rule, NodeAlarmEvent alarmEvent) {
        if (!inPeriod(rule)) {
            return;
        }

        String matchValue = rule.getMatchValue();
        matchValue = StringUtils.substringBeforeLast(matchValue, "@");

        String[] matchValues = StringUtils.split(matchValue, ",");

        for (String match : matchValues) {
            if (StringUtils.containsIgnoreCase(alarmEvent.getMessage(), match)) {
                String message = String.format(MESAGE_FORMAT, alarmEvent.getPipelineId(), alarmEvent.getNid(),
                                               alarmEvent.getMessage());
                sendAlarm(rule, message);
                break;
            }
        }
    }
}
