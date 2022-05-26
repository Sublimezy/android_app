package com.xueyiche.zjyk.xueyiche.community.bean;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.community.bean
 * @ClassName: TuWenDetailBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/5/18 10:00
 */
public class TuWenDetailBean {

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
    private NewsinfoBean newsinfo;
    private List<MessagelistBean> messagelist;

    public NewsinfoBean getNewsinfo() {
      return newsinfo;
    }

    public void setNewsinfo(NewsinfoBean newsinfo) {
      this.newsinfo = newsinfo;
    }

    public List<MessagelistBean> getMessagelist() {
      return messagelist;
    }

    public void setMessagelist(List<MessagelistBean> messagelist) {
      this.messagelist = messagelist;
    }

    public static class NewsinfoBean {
      private String id;
      private String title;
      private String avatar;
      private String image;
      private List<String> images;
      private String video_file;
      private String description;
      private String content;
      private String createtime;

      public String getAvatar() {
        return avatar;
      }

      public void setAvatar(String avatar) {
        this.avatar = avatar;
      }

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public String getImage() {
        return image;
      }

      public void setImage(String image) {
        this.image = image;
      }

      public List<String> getImages() {
        return images;
      }

      public void setImages(List<String> images) {
        this.images = images;
      }

      public String getVideo_file() {
        return video_file;
      }

      public void setVideo_file(String video_file) {
        this.video_file = video_file;
      }

      public String getDescription() {
        return description;
      }

      public void setDescription(String description) {
        this.description = description;
      }

      public String getContent() {
        return content;
      }

      public void setContent(String content) {
        this.content = content;
      }

      public String getCreatetime() {
        return createtime;
      }

      public void setCreatetime(String createtime) {
        this.createtime = createtime;
      }
    }

    public static class MessagelistBean {
      private String id;
      private String user_id;
      private String article_id;
      private String text;
      private String createtime;
      private String user_nickname;
      private String user_avatar;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }

      public String getUser_id() {
        return user_id;
      }

      public void setUser_id(String user_id) {
        this.user_id = user_id;
      }

      public String getArticle_id() {
        return article_id;
      }

      public void setArticle_id(String article_id) {
        this.article_id = article_id;
      }

      public String getText() {
        return text;
      }

      public void setText(String text) {
        this.text = text;
      }

      public String getCreatetime() {
        return createtime;
      }

      public void setCreatetime(String createtime) {
        this.createtime = createtime;
      }

      public String getUser_nickname() {
        return user_nickname;
      }

      public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
      }

      public String getUser_avatar() {
        return user_avatar;
      }

      public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
      }
    }
  }
}