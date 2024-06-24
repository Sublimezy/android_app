package com.xueyiche.zjyk.jiakao.myhttp;


public interface RequestBase {

    /**
     * @param errorMsg 错误原因,如果catch返回报错信息
     * @param errorType  错误参数,如果catch返回-1
     */
    void requestError(String errorMsg, int errorType);
}
