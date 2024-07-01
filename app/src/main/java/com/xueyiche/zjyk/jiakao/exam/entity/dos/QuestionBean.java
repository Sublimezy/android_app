package com.xueyiche.zjyk.jiakao.exam.entity.dos;


import android.os.Parcel;
import android.os.Parcelable;

public class QuestionBean implements Parcelable {

    private Long id;

    private Long subject;

    private String model;

    private Long questionType;

    private String content;

    private String item1;

    private String item2;

    private String item3;

    private String item4;

    private String answer;

    private String question;

    private String explains;

    private String url;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String remark;

    protected QuestionBean(Parcel in) {
        id = in.readLong();
        subject = in.readLong();
        model = in.readString();
        questionType = in.readLong();
        content = in.readString();
        item1 = in.readString();
        item2 = in.readString();
        item3 = in.readString();
        item4 = in.readString();
        answer = in.readString();
        question = in.readString();
        explains = in.readString();
        url = in.readString();
        createBy = in.readString();
        createTime = in.readString();
        updateBy = in.readString();
        updateTime = in.readString();
        remark = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        if (subject != null) {
            dest.writeLong(subject);
        }
        if (model != null) {
            dest.writeString(model);
        }

        dest.writeLong(questionType);
        dest.writeString(content);
        dest.writeString(item1);
        dest.writeString(item2);
        dest.writeString(item3);
        dest.writeString(item4);
        dest.writeString(answer);
        dest.writeString(question);
        dest.writeString(explains);
        dest.writeString(url);
        dest.writeString(createBy);
        dest.writeString(createTime);
        dest.writeString(updateBy);
        dest.writeString(updateTime);
        dest.writeString(remark);
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionBean> CREATOR = new Creator<QuestionBean>() {
        @Override
        public QuestionBean createFromParcel(Parcel in) {
            return new QuestionBean(in);
        }

        @Override
        public QuestionBean[] newArray(int size) {
            return new QuestionBean[size];
        }
    };

    public QuestionBean(Long id, Long subject, String model, Long questionType) {
        this.id = id;
        this.subject = subject;
        this.model = model;
        this.questionType = questionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Long questionType) {
        this.questionType = questionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public QuestionBean() {
    }

    public QuestionBean(Long subject, String model) {
        this.subject = subject;
        this.model = model;
    }

    public QuestionBean(Long id, Long subject, String model, Long questionType, String content, String item1, String item2, String item3, String item4, String answer, String question, String explains, String url, String createBy, String createTime, String updateBy, String updateTime, String remark) {
        this.id = id;
        this.subject = subject;
        this.model = model;
        this.questionType = questionType;
        this.content = content;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.answer = answer;
        this.question = question;
        this.explains = explains;
        this.url = url;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "QuestionBean{" +
                "id=" + id +
                ", subject=" + subject +
                ", model='" + model + '\'' +
                ", questionType=" + questionType +
                ", content='" + content + '\'' +
                ", item1='" + item1 + '\'' +
                ", item2='" + item2 + '\'' +
                ", item3='" + item3 + '\'' +
                ", item4='" + item4 + '\'' +
                ", answer='" + answer + '\'' +
                ", question='" + question + '\'' +
                ", explains='" + explains + '\'' +
                ", url='" + url + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}
