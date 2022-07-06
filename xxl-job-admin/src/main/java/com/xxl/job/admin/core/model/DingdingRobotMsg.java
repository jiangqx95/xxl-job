package com.xxl.job.admin.core.model;

import java.util.List;

/**
 * @author jiangqx
 * @date: 2022/7/5 17:25
 */
public class DingdingRobotMsg {

    private String msgtype;

    private Content text;

    private AtInfo at;

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

    public AtInfo getAt() {
        return at;
    }

    public void setAt(AtInfo at) {
        this.at = at;
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

    public class AtInfo {
        private List<String> atMobiles;

        private List<String> atUserIds;

        private boolean isAtAll;

        public List<String> getAtMobiles() {
            return atMobiles;
        }

        public void setAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }

        public List<String> getAtUserIds() {
            return atUserIds;
        }

        public void setAtUserIds(List<String> atUserIds) {
            this.atUserIds = atUserIds;
        }

        public boolean isAtAll() {
            return isAtAll;
        }

        public void setAtAll(boolean atAll) {
            isAtAll = atAll;
        }
    }


}
