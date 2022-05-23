package com.xueyiche.zjyk.xueyiche.community.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ctetin.expandabletextviewlibrary.app.StatusType;
import com.ctetin.expandabletextviewlibrary.model.ExpandableStatusFix;

import java.util.List;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.community
 * @ClassName: CommunityListBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/4/20 13:23
 */
public class CommunityListBean {
    private int code;
    private DataBean data;
    private String msg;
    private String time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        private int current_page;
        private List<DataBean.DataBeanX> data;
        private int last_page;
        private String per_page;
        private int total;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<DataBean.DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBean.DataBeanX> data) {
            this.data = data;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public static class DataBeanX implements MultiItemEntity, ExpandableStatusFix {
            private String content;
            private String avatar;
            private String nickname;
            private String createtime;
            private String description;
            private String id;
            private String image;
            private List<String> images;
            private String title;
            private String video_file;
            private String user_id;
            private String new_url;

            public String getNew_url() {
                return new_url;
            }

            public void setNew_url(String new_url) {
                this.new_url = new_url;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getVideo_file() {
                return video_file;
            }

            public void setVideo_file(String video_file) {
                this.video_file = video_file;
            }


            private StatusType status;
            @Override
            public void setStatus(StatusType status) {
                this.status = status;
            }
            @Override
            public StatusType getStatus() {
                return status;
            }
            public static final int TEXT = 1;
            public static final int TEXT_ONE_PIC = 2;
            public static final int TEXT_TWO_PIC = 3;
            public static final int TEXT_VIDEO = 4;
            public static final int TEXT_PICS = 5;
            private int itemType;
            @Override
            public int getItemType() {
                return itemType;
            }
        }
    }


//    public static class DataBeanX implements MultiItemEntity,ExpandableStatusFix {
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    }
}