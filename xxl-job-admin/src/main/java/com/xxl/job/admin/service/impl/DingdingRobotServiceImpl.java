package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.model.DingdingRobotMsg;
import com.xxl.job.admin.service.DingdingRobotService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author jiangqx
 * @date: 2022/7/6 9:57
 */
@Service
public class DingdingRobotServiceImpl implements DingdingRobotService {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<Object> postForEntity(String msgContent, DingdingRobotMsg.AtInfo atInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        long timestamp = System.currentTimeMillis();
        String dingdingWebhook = XxlJobAdminConfig.getAdminConfig().getDingdingWebhook();
        String url = dingdingWebhook + "&timestamp=" + timestamp + "&sign=" + sign(timestamp);
        DingdingRobotMsg dingdingRobotMsg = new DingdingRobotMsg();
        dingdingRobotMsg.setMsgtype("text");
        DingdingRobotMsg.Content content = new DingdingRobotMsg().new Content();
        content.setContent(msgContent);
        dingdingRobotMsg.setText(content);
        dingdingRobotMsg.setAt(atInfo);
        ResponseEntity<Object> objectResponseEntity = restTemplate.postForEntity(url, dingdingRobotMsg, Object.class);
        return objectResponseEntity;
    }

    private String sign(Long timestamp) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String dingdingSecret = XxlJobAdminConfig.getAdminConfig().getDingdingSecret();
        String stringToSign = timestamp + "\n" + dingdingSecret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(dingdingSecret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return sign;
    }

}
