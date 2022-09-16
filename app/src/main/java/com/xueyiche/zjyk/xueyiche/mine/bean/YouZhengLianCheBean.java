package com.xueyiche.zjyk.xueyiche.mine.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.mine.bean
 * @ClassName: YouZhengLianCheBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/9/15 0015 11:07:25
 */
public class YouZhengLianCheBean {

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
    private int total;
    private String per_page;
    private int current_page;
    private int last_page;
    private List<DataBean.DataBeanX> data;

    public int getTotal() {
      return total;
    }

    public void setTotal(int total) {
      this.total = total;
    }

    public String getPer_page() {
      return per_page;
    }

    public void setPer_page(String per_page) {
      this.per_page = per_page;
    }

    public int getCurrent_page() {
      return current_page;
    }

    public void setCurrent_page(int current_page) {
      this.current_page = current_page;
    }

    public int getLast_page() {
      return last_page;
    }

    public void setLast_page(int last_page) {
      this.last_page = last_page;
    }

    public List<DataBean.DataBeanX> getData() {
      return data;
    }

    public void setData(List<DataBean.DataBeanX> data) {
      this.data = data;
    }

    public static class DataBeanX {
      private String order_sn;
      private String user_id;
      private String start_address;
      private String end_address;
      private String order_type;
      private String order_status;
      private String createtime;
      private String bd_type;
      private String practice_id;
      private String mobile;

      public String getOrder_sn() {
        return order_sn;
      }

      public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
      }

      public String getUser_id() {
        return user_id;
      }

      public void setUser_id(String user_id) {
        this.user_id = user_id;
      }

      public String getStart_address() {
        return start_address;
      }

      public void setStart_address(String start_address) {
        this.start_address = start_address;
      }

      public String getEnd_address() {
        return end_address;
      }

      public void setEnd_address(String end_address) {
        this.end_address = end_address;
      }

      public String getOrder_type() {
        return order_type;
      }

      public void setOrder_type(String order_type) {
        this.order_type = order_type;
      }

      public String getOrder_status() {
        return order_status;
      }

      public void setOrder_status(String order_status) {
        this.order_status = order_status;
      }

      public String getCreatetime() {
        return createtime;
      }

      public void setCreatetime(String createtime) {
        this.createtime = createtime;
      }

      public String getBd_type() {
        return bd_type;
      }

      public void setBd_type(String bd_type) {
        this.bd_type = bd_type;
      }

      public String getPractice_id() {
        return practice_id;
      }

      public void setPractice_id(String practice_id) {
        this.practice_id = practice_id;
      }

      public String getMobile() {
        return mobile;
      }

      public void setMobile(String mobile) {
        this.mobile = mobile;
      }
    }
  }
}