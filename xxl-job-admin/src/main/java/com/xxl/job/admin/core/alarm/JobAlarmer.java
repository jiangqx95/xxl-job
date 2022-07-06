package com.xxl.job.admin.core.alarm;

import com.xxl.job.admin.core.alarm.impl.DingRobotAlarm;
import com.xxl.job.admin.core.alarm.impl.EmailJobAlarm;
import com.xxl.job.admin.core.alarm.impl.WeChatRobotAlarm;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class JobAlarmer implements ApplicationContextAware, InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(JobAlarmer.class);

    private ApplicationContext applicationContext;
    private List<JobAlarm> jobAlarmList;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, JobAlarm> serviceBeanMap = applicationContext.getBeansOfType(JobAlarm.class);
        if (serviceBeanMap != null && serviceBeanMap.size() > 0) {
            jobAlarmList = new ArrayList<JobAlarm>(serviceBeanMap.values());
        }
    }

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean alarm(XxlJobInfo info, XxlJobLog jobLog) {

        List<String> alarmTypeList = new ArrayList<>();

        if (info.getAlarmType() != null) {
            alarmTypeList = Arrays.asList(info.getAlarmType().split(","));
        }

        boolean result = false;

        if (jobAlarmList != null && jobAlarmList.size() > 0) {
            result = true;

            for (JobAlarm alarm : jobAlarmList) {
                // 邮件
                if (alarm instanceof EmailJobAlarm && !alarmTypeList.contains("1")) {
                    continue;
                }
                // 钉钉
                if (alarm instanceof DingRobotAlarm && !alarmTypeList.contains("2")) {
                    continue;
                }
                // 企业微信
                if (alarm instanceof WeChatRobotAlarm && !alarmTypeList.contains("3")) {
                    continue;
                }
                boolean resultItem = false;
                try {
                    resultItem = alarm.doAlarm(info, jobLog);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                if (!resultItem) {
                    result = false;
                }
            }
        }

        return result;
    }

}
