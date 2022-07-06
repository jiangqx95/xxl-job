package com.xxl.job.admin.service;


import com.xxl.job.admin.core.model.DingdingRobotMsg;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author jiangqx
 * @date: 2022/7/6 9:55
 */
public interface DingdingRobotService {

    /**
     * @description: postForEntity
     * @param: msgType 消息类型（默认text）
     * @param: msgContent 消息内容
     * @param: at 需要@的人
     * @return: {@link ResponseEntity<Object>}
     * @author jiangqx
     * @date: 2022/7/6 9:54
     */
    public ResponseEntity<Object> postForEntity(String msgContent, DingdingRobotMsg.AtInfo atInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;

}
