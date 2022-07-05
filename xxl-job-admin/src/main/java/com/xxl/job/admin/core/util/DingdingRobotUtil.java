package com.xxl.job.admin.core.util;

import com.xxl.job.admin.core.model.DingdingRobotMsg;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * @author jiangqx
 * @date: 2022/7/5 17:48
 */
public class DingdingRobotUtil {

    @Value("${dingding.webhook}")
    static
    String dingdingWebhook;

    @Value("${dingding.secret}")
    static
    String dingdingSecret;

    @Autowired
    private static RestTemplate restTemplate;

    public static ResponseEntity<Object> postForEntity(String msgType, String msgContent) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        long timestamp = System.currentTimeMillis();
        String url = dingdingWebhook + "&timestamp=" + timestamp + "&sign=" + sign(timestamp);
        DingdingRobotMsg dingdingRobotMsg = new DingdingRobotMsg();
        dingdingRobotMsg.setMsgtype(msgType);
        DingdingRobotMsg.Content content = new DingdingRobotMsg().new Content();
        content.setContent(msgContent);
        dingdingRobotMsg.setText(content);
        return restTemplate.postForEntity(url, dingdingRobotMsg, Object.class);
    }

    public static String sign(Long timestamp) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = timestamp + "\n" + dingdingSecret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(dingdingSecret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return sign;
    }


}
