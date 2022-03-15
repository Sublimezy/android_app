package com.xueyiche.zjyk.xueyiche.constants.bean;

import java.util.List;

/**
 * Created by Owner on 2016/11/25.
 */
public class PinPaiBean {


    private int code;
    private String msg;
    /**
     * id : 1
     * logo : http://car3.autoimg.cn/cardfs/brand/50/g23/M09/5B/8F/autohomecar__wKgFXFbCuGGAark9AAAOm8MlQDA537.jpg
     * name : 大众
     * pinyin : D
     */

    private List<ContentBean> content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private int id;
        private String logo;
        private String name;
        private String pinyin;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }
    }
}
