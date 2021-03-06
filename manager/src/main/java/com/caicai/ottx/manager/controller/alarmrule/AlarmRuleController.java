package com.caicai.ottx.manager.controller.alarmrule;

import com.alibaba.otter.shared.common.model.config.alarm.AlarmRule;
import com.alibaba.otter.shared.common.model.config.alarm.AlarmRuleStatus;
import com.caicai.ottx.common.ApiResult;
import com.caicai.ottx.common.utils.EnumBeanUtils;
import com.caicai.ottx.common.vo.PageResult;
import com.caicai.ottx.manager.controller.alarm.form.AlarmForm;
import com.caicai.ottx.service.config.alarm.AlarmRuleService;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huaseng on 2019/9/26.
 */
@RequestMapping("/alarmRule")
@RestController
@Slf4j
public class AlarmRuleController {
    @Autowired
    private AlarmRuleService alarmRuleService;
    @RequestMapping(value = "/findByPipelineId",method = RequestMethod.POST)
    public ApiResult<List<AlarmRule>> findByPipelineId(@RequestBody AlarmForm alarmForm) {
        try {
            List<AlarmRule> alarmRuleList = alarmRuleService.getAlarmRules(alarmForm.getPipelineId());
            return ApiResult.success(alarmRuleList);
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }

    @RequestMapping(value = "/addAlarmRules",method = RequestMethod.POST)
    public ApiResult<String> addAlarmRules(@RequestBody AlarmForm alarmForm){
       try{
           alarmRuleService.doOnekeyAddMonitor(alarmForm.getPipelineId());
           return ApiResult.success("插入成功");
       }catch (Exception e){
           e.printStackTrace();
           return ApiResult.failed(e.getMessage());
       }

    }

    @RequestMapping(value = "/doSwitchStatus",method = RequestMethod.POST)
    public ApiResult<String> doSwitchStatus(@RequestBody AlarmForm alarmForm){
        try{
            AlarmRule alarmRule = new AlarmRule();
            EnumBeanUtils.copyProperties(alarmForm,alarmRule);
            alarmRuleService.modify(alarmRule);
            return ApiResult.success("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return ApiResult.failed(e.getMessage());
        }

    }

    @RequestMapping(value = "/getByPage",method = RequestMethod.POST)
    public ApiResult<PageResult> getByPage(@RequestBody AlarmForm alarmForm){
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("current", alarmForm.getCurrentPage());
        condition.put("pageSize", alarmForm.getPageSize());
        List<AlarmRule> alarmRules = alarmRuleService.listAllAlarmRules(condition);
        return ApiResult.success(new PageResult(alarmRules,(Page)alarmRules));
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ApiResult<String> update(@RequestBody AlarmForm alarmForm){
      try{
          AlarmRule alarmRule = new AlarmRule();
          EnumBeanUtils.copyProperties(alarmForm,alarmRule);
          alarmRuleService.modify(alarmRule);
          return ApiResult.success("修改成功");
      }catch (Exception e){
          log.error(e.getMessage());
          return ApiResult.failed(e.getMessage());
      }
    }

    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    public ApiResult<String> remove(@RequestBody AlarmForm alarmForm){
        try{
            alarmRuleService.remove(alarmForm.getId());
            return ApiResult.success("删除成功");
        }catch (Exception e){
            log.error(e.getMessage());
            return ApiResult.failed(e.getMessage());
        }
    }


}
