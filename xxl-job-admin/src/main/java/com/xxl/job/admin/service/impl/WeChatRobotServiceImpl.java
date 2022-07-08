package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.model.WeChatRobotMsg;
import com.xxl.job.admin.service.WeChatRobotService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author jiangqx
 * @date: 2022/7/6 9:57
 */
@Service
public class WeChatRobotServiceImpl implements WeChatRobotService {

    @Resource
    private RestTemplate restTemplate;


    @Override
    public ResponseEntity<Object> postForEntity(WeChatRobotMsg.Text text) {
        String weChatWebhook = XxlJobAdminConfig.getAdminConfig().getWeChatWebhook();
        WeChatRobotMsg weChatRobotMsg = new WeChatRobotMsg();
        weChatRobotMsg.setMsgtype("text");
        weChatRobotMsg.setText(text);
        ResponseEntity<Object> objectResponseEntity = restTemplate.postForEntity(weChatWebhook, weChatRobotMsg, Object.class);
        return objectResponseEntity;
    }
}
