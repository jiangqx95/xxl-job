package com.xxl.job.admin.service;


import com.xxl.job.admin.core.model.WeChatRobotMsg;
import org.springframework.http.ResponseEntity;

/**
 * @author jiangqx
 * @date: 2022/7/6 9:55
 */
public interface WeChatRobotService {

    /**
     * @description: postForEntity
     * @param: text 消息内容
     * @return: {@link ResponseEntity<Object>}
     * @author jiangqx
     * @date: 2022/7/6 9:54
     */
    public ResponseEntity<Object> postForEntity(WeChatRobotMsg.Text text);

}
