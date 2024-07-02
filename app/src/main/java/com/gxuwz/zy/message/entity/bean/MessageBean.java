package com.gxuwz.zy.message.entity.bean;

public class MessageBean {
    private int id;
    private String messageImage;
    private String messageTextTitle;
    private String messageTextDescribe;
    private String messageTextTime;
    private String messageText;
    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String remark;

    public MessageBean(int id, String messageImage, String messageTextTitle, String messageTextDescribe, String messageTextTime, String messageText, String createBy, String createTime, String updateBy, String updateTime, String remark) {
        this.id = id;
        this.messageImage = messageImage;
        this.messageTextTitle = messageTextTitle;
        this.messageTextDescribe = messageTextDescribe;
        this.messageTextTime = messageTextTime;
        this.messageText = messageText;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remark = remark;
    }

    public MessageBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(String messageImage) {
        this.messageImage = messageImage;
    }

    public String getMessageTextTitle() {
        return messageTextTitle;
    }

    public void setMessageTextTitle(String messageTextTitle) {
        this.messageTextTitle = messageTextTitle;
    }

    public String getMessageTextDescribe() {
        return messageTextDescribe;
    }

    public void setMessageTextDescribe(String messageTextDescribe) {
        this.messageTextDescribe = messageTextDescribe;
    }

    public String getMessageTextTime() {
        return messageTextTime;
    }

    public void setMessageTextTime(String messageTextTime) {
        this.messageTextTime = messageTextTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "id=" + id +
                ", messageImage='" + messageImage + '\'' +
                ", messageTextTitle='" + messageTextTitle + '\'' +
                ", messageTextDescribe='" + messageTextDescribe + '\'' +
                ", messageTextTime='" + messageTextTime + '\'' +
                ", messageText='" + messageText + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
