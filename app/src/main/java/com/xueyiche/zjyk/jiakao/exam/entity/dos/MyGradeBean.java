package com.xueyiche.zjyk.jiakao.exam.entity.dos;

public class MyGradeBean {
    private int id;
    private String examDate;
    private int score;
    private int totalQuestions;
    private int mistakesNum;
    private int trueNum;
    private int unAnsweredNum;
    private String mistakesQuestion;
    private int allTime;
    private int takeTime;
    private int subject;
    private String model;

    @Override
    public String toString() {
        return "MyGradeBean{" +
                "id=" + id +
                ", examDate='" + examDate + '\'' +
                ", score='" + score + '\'' +
                ", totalQuestions=" + totalQuestions +
                ", mistakesNum=" + mistakesNum +
                ", trueNum=" + trueNum +
                ", unAnsweredNum=" + unAnsweredNum +
                ", mistakesQuestion='" + mistakesQuestion + '\'' +
                ", allTime=" + allTime +
                ", takeTime=" + takeTime +
                ", subject=" + subject +
                ", model='" + model + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(int takeTime) {
        this.takeTime = takeTime;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getMistakesNum() {
        return mistakesNum;
    }

    public void setMistakesNum(int mistakesNum) {
        this.mistakesNum = mistakesNum;
    }

    public int getTrueNum() {
        return trueNum;
    }

    public void setTrueNum(int trueNum) {
        this.trueNum = trueNum;
    }

    public int getUnAnsweredNum() {
        return unAnsweredNum;
    }

    public void setUnAnsweredNum(int unAnsweredNum) {
        this.unAnsweredNum = unAnsweredNum;
    }

    public String getMistakesQuestion() {
        return mistakesQuestion;
    }

    public void setMistakesQuestion(String mistakesQuestion) {
        this.mistakesQuestion = mistakesQuestion;
    }

    public int getAllTime() {
        return allTime;
    }

    public void setAllTime(int allTime) {
        this.allTime = allTime;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public MyGradeBean() {
    }

    public MyGradeBean(String examDate, int score, int totalQuestions, int mistakesNum, int trueNum, int unAnsweredNum, String mistakesQuestion, int allTime, int subject, String model) {
        this.examDate = examDate;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.mistakesNum = mistakesNum;
        this.trueNum = trueNum;
        this.unAnsweredNum = unAnsweredNum;
        this.mistakesQuestion = mistakesQuestion;
        this.allTime = allTime;
        this.subject = subject;
        this.model = model;
    }

    public MyGradeBean(int id, String examDate, int score, int totalQuestions, int mistakesNum, int trueNum, int unAnsweredNum, String mistakesQuestion, int allTime, int takeTime, int subject, String model) {
        this.id = id;
        this.examDate = examDate;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.mistakesNum = mistakesNum;
        this.trueNum = trueNum;
        this.unAnsweredNum = unAnsweredNum;
        this.mistakesQuestion = mistakesQuestion;
        this.allTime = allTime;
        this.takeTime = takeTime;
        this.subject = subject;
        this.model = model;
    }
}
