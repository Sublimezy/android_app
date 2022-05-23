package com.xueyiche.zjyk.xueyiche.community.bean;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.community.bean
 * @ClassName: CallSuccessBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/5/23 10:28
 */
public class CallSuccessBean {


  private int code;
  private String msg;
  private String time;
  private Object data;

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

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}