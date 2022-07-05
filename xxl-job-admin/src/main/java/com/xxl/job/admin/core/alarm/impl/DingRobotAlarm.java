package com.xxl.job.admin.core.alarm.impl;

import com.xxl.job.admin.core.alarm.JobAlarm;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.core.util.DingdingRobotUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author jiangqx
 * @description: 钉钉群机器人告警信息
 * @date: 2022/7/4 16:27
 */
@Component
public class DingRobotAlarm implements JobAlarm {
    private static Logger logger = LoggerFactory.getLogger(DingRobotAlarm.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * fail alarm
     *
     * @param jobLog
     */
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
        try {
            ResponseEntity<Object> text = DingdingRobotUtil.postForEntity("text", content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * load email job alarm template
     *
     * @return
     */
    private static final HashMap<String, Object> loadEmailJobAlarmTemplate(XxlJobInfo info, XxlJobLog jobLog) {
        HashMap<String, Object> requestMap = new HashMap<>(16);

        // 消息类型
        requestMap.put("msgtype", "text");

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

        HashMap<String, Object> map = new HashMap<>(16);
        map.put("content", content);
        requestMap.put("text", map);

        // 设置是否@指定人
//        Map<String, Object> atmap = new HashMap<String, Object>(16);
//        String[] authorList = info.getAuthor().split(",");
//        ArrayList arrayList = new ArrayList();
//        for (String author : authorList) {
//            if ("".equals(author) || author.split("-").length < 2) {
//                continue;
//            }
//            arrayList.add(author.split("-")[1]);
//        }
//        if (arrayList.size() > 0) {
//            atmap.put("atMobiles", arrayList.toArray());
//            requestMap.put("at", atmap);
//        }
        return requestMap;
    }

}
