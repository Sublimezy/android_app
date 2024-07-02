package com.gxuwz.zy.constants;


public interface AppUrl {

    String IPWAIWANG = "http://192.168.121.1:8080";

    //获取头像
    String My_Head = IPWAIWANG + "mg/my/selectHeadImgAndNickName.do";


    //f头像的token
    String TOUXIANG = IPWAIWANG + "/common/upload";

    // 邮箱验证码
    String sendSMS = IPWAIWANG + "/app/email/sendEmail";

    //登录/注册
    String LOGIN = IPWAIWANG + "/app/user/userLogin";

    //查询驾考题目
    String DRIVING_LIST = IPWAIWANG + "/app/book/list";

    //查询平台消息
    String MESSAGE_LIST = IPWAIWANG + "/app/message/list";


    String userInfoEdit = IPWAIWANG + "api/Userindex/userInfoEdit";

    String userInfoEditIdentity = IPWAIWANG + "api/Userindex/userInfoEditIdentity";


}

