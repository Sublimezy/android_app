package com.gxuwz.zy.login.entity.dos;

public class LoginBean {
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

        private DistanceUser distanceUser;

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public DistanceUser getDistanceUser() {
            return distanceUser;
        }

        public void setUserinfo(DistanceUser distanceUser) {
            this.distanceUser = distanceUser;
        }

        public static class DistanceUser {
            /** 用户ID */
            private Long userId;

            /** 用户账号 */
            private String userName;

            /** 用户昵称 */
            private String nickName;

            /** 用户邮箱 */
            private String email;

            /** 手机号码 */
            private String phonenumber;

            /** 用户性别 */
            private Long sex;

            /** 头像地址 */
            private String avatar;

            /** 密码 */
            private String password;

            /** 帐号状态 */
            private Long status;

            /** 删除标志 */
            private Long delFlag;

            /** 粉丝数量 */
            private Long fans;

            /** 关注的人的数量 */
            private Long followee;

            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhonenumber() {
                return phonenumber;
            }

            public void setPhonenumber(String phonenumber) {
                this.phonenumber = phonenumber;
            }

            public Long getSex() {
                return sex;
            }

            public void setSex(Long sex) {
                this.sex = sex;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Long getStatus() {
                return status;
            }

            public void setStatus(Long status) {
                this.status = status;
            }

            public Long getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(Long delFlag) {
                this.delFlag = delFlag;
            }

            public Long getFans() {
                return fans;
            }

            public void setFans(Long fans) {
                this.fans = fans;
            }

            public Long getFollowee() {
                return followee;
            }

            public void setFollowee(Long followee) {
                this.followee = followee;
            }
        }
    }


}
