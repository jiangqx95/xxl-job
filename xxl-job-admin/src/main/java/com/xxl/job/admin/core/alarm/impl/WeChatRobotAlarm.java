package com.xxl.job.admin.core.alarm.impl;

import com.xxl.job.admin.core.alarm.JobAlarm;
import com.xxl.job.admin.core.model.WeChatRobotMsg;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.service.WeChatRobotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author jiangqx
 * @description: 钉钉群机器人告警信息
 * @date: 2022/7/4 16:27
 */
@Component
public class WeChatRobotAlarm implements JobAlarm {
    private static Logger logger = LoggerFactory.getLogger(WeChatRobotAlarm.class);

    @Resource
    private WeChatRobotService weChatRobotService;

    @Override
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        // 消息内容
        String content = "【告警信息】 \t\n" +
                "负责人 : \t" + info.getAuthor() + "\t\n" +
                "任务id : \t" + info.getId() + "\t\n" +
                "任务名称 : \t" + info.getJobDesc() + "\t\n" +
                "执行器名称 : \t" + info.getExecutorHandler() + "\t\n" +
                "执行器ip : \t" + jobLog.getExecutorAddress() + "\t\n" +
                "任务参数 : \t" + jobLog.getExecutorParam() + "\t\n" +
                "LogId : \t" + jobLog.getId() + "\t\n" +
                "TriggerMsg : \t" + jobLog.getTriggerMsg().replace("<br>", "\n") + "\t\n" +
                "HandleCode : \t" + jobLog.getHandleMsg() + "\t\n" +
                "报警时间 : \t" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t\n";

        WeChatRobotMsg.Text text = new WeChatRobotMsg().new Text();
        text.setContent(content);

        // 负责人
        List<String> mentioned_list = new ArrayList<>(Arrays.asList(info.getAuthor().split(",")));
        text.setMentioned_list(mentioned_list);

        // 负责人电话
        List<String> mentioned_mobile_list = new ArrayList<>(Arrays.asList(info.getMobiles().split(",")));
        text.setMentioned_mobile_list(mentioned_mobile_list);

        try {
            weChatRobotService.postForEntity(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
