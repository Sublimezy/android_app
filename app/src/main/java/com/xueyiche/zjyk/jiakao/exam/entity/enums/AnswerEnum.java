package com.xueyiche.zjyk.jiakao.exam.entity.enums;

//答案枚举
public enum AnswerEnum {

    A("A"),
    B("B"),
    C("C"),
    D("D");


    private final String description;

    AnswerEnum(String description) {
        this.description = description;
    }

    public char getDescription() {
        return description.charAt(0);
    }


}
