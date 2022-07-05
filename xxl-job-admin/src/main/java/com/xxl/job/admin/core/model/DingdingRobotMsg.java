package com.xxl.job.admin.core.model;

/**
 * @author jiangqx
 * @date: 2022/7/5 17:25
 */
public class DingdingRobotMsg {

    private String msgtype;

    private Content text;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Content getText() {
        return text;
    }

    public void setText(Content text) {
        this.text = text;
    }

    public class Content {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}
