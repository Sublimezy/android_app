package com.xueyiche.zjyk.xueyiche.main.activities.main;

/**
 * @Package: com.example.yjf.tata.message.qunshou_tui.bean
 * @ClassName: BaseBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2020/7/28 9:38
 */
public class BaseBean {

    /**
     * msg : 操作成功！
     * code : 200
     */

    private String msg;
    private String content;
    private int code;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
