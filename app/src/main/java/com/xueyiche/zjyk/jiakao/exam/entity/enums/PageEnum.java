package com.xueyiche.zjyk.jiakao.exam.entity.enums;

//页面枚举
public enum PageEnum {
    SEQUENCE("顺序联系"),
    MISTAKES("我的错题"),
    COLLECTION("我的收藏"),
    SPECIALS("专项联系"),
    PRACTICE("模拟练习");

    private final String description;

    PageEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
