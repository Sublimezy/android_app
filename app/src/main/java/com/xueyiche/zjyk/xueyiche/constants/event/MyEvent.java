package com.xueyiche.zjyk.xueyiche.constants.event;

/**
 * Created by Owner on 2016/12/12.
 */
public class MyEvent {
    private String mMsg;
    private String Search_content;
    public MyEvent(String msg) {
        mMsg = msg;
    }

    public MyEvent(String mMsg, String search_content) {
        this.mMsg = mMsg;
        Search_content = search_content;
    }

    public String getMsg(){
        return mMsg;
    }
    public String getData(){
        return Search_content;
    }

}
