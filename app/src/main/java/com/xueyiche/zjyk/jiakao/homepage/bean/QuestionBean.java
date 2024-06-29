package com.xueyiche.zjyk.jiakao.homepage.bean;


public class QuestionBean {
    public String id;
    public String subject;
    public String model;
    public String questionType;
    public String item1;
    public String item2;
    public String item3;
    public String item4;
    public String answer;
    public String question;
    public String explains;
    public String url;

    public QuestionBean(String subject, String model, String questionType, String item1, String item2, String item3, String item4, String answer, String question, String explains, String url) {
        this.subject = subject;
        this.model = model;
        this.questionType = questionType;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.answer = answer;
        this.question = question;
        this.explains = explains;
        this.url = url;
    }

    public QuestionBean() {
    }

    public QuestionBean(String id, String subject, String model, String questionType) {
        this.id = id;
        this.subject = subject;
        this.model = model;
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        return "QuestionBean{" +
                "subject='" + subject + '\'' +
                ", model='" + model + '\'' +
                ", questionType='" + questionType + '\'' +
                ", item1='" + item1 + '\'' +
                ", item2='" + item2 + '\'' +
                ", item3='" + item3 + '\'' +
                ", item4='" + item4 + '\'' +
                ", answer='" + answer + '\'' +
                ", question='" + question + '\'' +
                ", explains='" + explains + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
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
}
