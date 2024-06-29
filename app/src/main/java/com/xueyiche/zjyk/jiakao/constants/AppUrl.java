package com.xueyiche.zjyk.jiakao.constants;


public interface AppUrl {

    String IPWAIWANG = "http://192.168.121.1:8080";

    //获取头像
    String My_Head = IPWAIWANG + "mg/my/selectHeadImgAndNickName.do";

    //修改头像
    String Change_Head = IPWAIWANG + "mg/my/updateHeadImgAndNickName.do";

    //f头像的token
    String TOUXIANG = IPWAIWANG + "/common/upload";

    // 邮箱验证码
    String sendSMS = IPWAIWANG + "/app/email/sendEmail";
    //登录/注册
    String LOGIN = IPWAIWANG + "/app/user/userLogin";

    //查询驾考题目
    String DRIVING_LIST = IPWAIWANG+"/app/book/list";


    String userInfoEdit = IPWAIWANG + "api/Userindex/userInfoEdit";

    String userInfoEditIdentity = IPWAIWANG + "api/Userindex/userInfoEditIdentity";

    String IPWAIWANGBEFORE = "http://122.9.33.73:8082/";

    //插入学习记录
    String addlearningrecord = IPWAIWANGBEFORE + "drivingtrainee/addlearningrecord.do";


}

