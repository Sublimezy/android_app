package com.xueyiche.zjyk.xueyiche.mine.activities.bean;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.mine.activities.bean
 * @ClassName: YouZhengLianCheDetailBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/9/16 0016 10:41:14
 */
public class YouZhengLianCheDetailBean {


  private int code;
  private String msg;
  private String time;
  private DataBean data;

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

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public static class DataBean {
    private String user_id;
    private String order_sn;
    private String start_address;
    private String order_status;
    private String total_price;
    private String practice_id;
    private String h_money;
    private String hour_num;
    private String fixed_time;
    private String createtime;
    private String practice_nickname;
    private String practice_username;

    public String getUser_id() {
      return user_id;
    }

    public void setUser_id(String user_id) {
      this.user_id = user_id;
    }

    public String getOrder_sn() {
      return order_sn;
    }

    public void setOrder_sn(String order_sn) {
      this.order_sn = order_sn;
    }

    public String getStart_address() {
      return start_address;
    }

    public void setStart_address(String start_address) {
      this.start_address = start_address;
    }

    public String getOrder_status() {
      return order_status;
    }

    public void setOrder_status(String order_status) {
      this.order_status = order_status;
    }

    public String getTotal_price() {
      return total_price;
    }

    public void setTotal_price(String total_price) {
      this.total_price = total_price;
    }

    public String getPractice_id() {
      return practice_id;
    }

    public void setPractice_id(String practice_id) {
      this.practice_id = practice_id;
    }

    public String getH_money() {
      return h_money;
    }

    public void setH_money(String h_money) {
      this.h_money = h_money;
    }

    public String getHour_num() {
      return hour_num;
    }

    public void setHour_num(String hour_num) {
      this.hour_num = hour_num;
    }

    public String getFixed_time() {
      return fixed_time;
    }

    public void setFixed_time(String fixed_time) {
      this.fixed_time = fixed_time;
    }

    public String getCreatetime() {
      return createtime;
    }

    public void setCreatetime(String createtime) {
      this.createtime = createtime;
    }

    public String getPractice_nickname() {
      return practice_nickname;
    }

    public void setPractice_nickname(String practice_nickname) {
      this.practice_nickname = practice_nickname;
    }

    public String getPractice_username() {
      return practice_username;
    }

    public void setPractice_username(String practice_username) {
      this.practice_username = practice_username;
    }
  }
}