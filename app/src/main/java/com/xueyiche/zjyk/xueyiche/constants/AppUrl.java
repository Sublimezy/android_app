package com.xueyiche.zjyk.xueyiche.constants;

/**
 * Created by Owner on 2016/9/20.
 */
public interface AppUrl {
    //3.0国裕测试
    String IPWAIWANG = "http://guanli.xueyiche.vip:101/";
    String SelectCarTypeAndPrice = IPWAIWANG + "mg/practiceDriving/selectCarTypeAndPrice.do";
    String Insert_DeviceId = IPWAIWANG + "substitute/updateSubstituteDeviceId.do";
    //有证练车列表
    String getDriverListYZ = IPWAIWANG + "mg/practiceDriving/getDriverListYZ.do";
    //驾校订单支付
    String Driver_Shcool_Indent_Pay = IPWAIWANG + "mg/driver_school_order/pay_driver_school.do";
    //车生活订单支付
    String Car_Life_Indent_Pay = IPWAIWANG + "mg/carlifeorder/pay_carLifeOrder.do";
    String Coupon_List = IPWAIWANG + "mg/my/myCouponList.do";
    //代驾可用优惠券列表
    String Dj_Coupon_List = IPWAIWANG + "substitute/getMyCouponsList.do";
    //车生活取消订单
    String Delete_Indent_CarLive = IPWAIWANG + "mg/carlifeorder/deleteCarLifeOrder.do";
    //二手车取消订单
    String Delete_Indent_UsedCar = IPWAIWANG + "mg/twoCar/deleteTwoCarOrder.do";
    String Delete_Indent_ZTC = IPWAIWANG + "mg/driver_school_order/deleteZTCOrder.do";
    //驾校取消订单
    String Delete_Indent_DriverSchool = IPWAIWANG + "mg/driver_school_order/deleteDriverSchoolOrder.do";
    //预约练车取消订单
    String Delete_Indent_Practice = IPWAIWANG + "mg/practiceDriving/canclePracticeOrder.do";
    //有证练车开通的城市的区域
    String Have_Parper_Test_Area = IPWAIWANG + "mg/area/selectRegionsByAreaId.do";
    //紧急联系人上传
    String JinJi_Phone = IPWAIWANG + "mg/practiceDriving/updateUserTruePhone.do";
    //紧急联系人获取
    String JinJi_Phone_Get = IPWAIWANG + "mg/practiceDriving/getTruePhone.do";
    //获取拼团码
    String Pin_By_OrderNumber = IPWAIWANG + "mg/driver_school_order/getPinByOrderNumber.do";
    //预约，直接约教练练车发布提交数据
    String Order_Indent_Post = IPWAIWANG + "mg/practiceDriving/backPracticeOrderYZ.do";
    //预约下单
    String Order_Post_Ok = IPWAIWANG + "mg/practiceDriving/submitPracticeOrderReady.do";
    //教练详情
    String Driver_Details = IPWAIWANG + "mg/my/driverDetails.do";
    //获取头像
    String My_Head = IPWAIWANG + "mg/my/selectHeadImgAndNickName.do";
    //修改头像
    String Change_Head = IPWAIWANG + "mg/my/updateHeadImgAndNickName.do";
    //订单接口
    String All_Order = IPWAIWANG + "mg/my/getMyOrderList.do";
    //驾校教练列表
    String Driver_School_Coach_List = IPWAIWANG + "mg/driver_school/schoolDriverList.do";
    //有证练车违约取消订单规则
    String WeiYue_GuiZe = IPWAIWANG + "mg/practiceDriving/getBreakListString.do";
    //确认发布
    String FaBu_QueRen = IPWAIWANG + "mg/practiceDriving/pushPracticeOrderToDriver.do";
    //设置修改
    String Setting_Change = IPWAIWANG + "mg/my/updateMySet.do";
    //查看个人信息
    String CHAKANGERENXINXI = IPWAIWANG + "mg/my/selectMySet.do";
    //查看评价
    String All_PingJia = IPWAIWANG + "mg/my/more_comment.do";
    //开通城市列表
    String KAITONGCHENGSHI = IPWAIWANG + "mg/area/selectCitys.do";
    //分享的积分
    String Shared_JiFen = IPWAIWANG + "mg/my/shared.do";
    //练车进行中订单详情
    String Practice_Going_Indent_Content = IPWAIWANG + "mg/practiceDriving/getDriverOrderDetail.do";
    //申请返现
    String DriverSchool_FanXian = IPWAIWANG + "mg/driver_school_order/applyForCashBack.do";
    //评价里的数据
    String Indent_PingJia = IPWAIWANG + "mg/my/order_evaluate_detail.do";
    //消息列表
    String Message_List = IPWAIWANG + "mg/message/getMessageList.do";
    //是否查看过该条信息
    String Message_Content_Read = IPWAIWANG + "mg/message/readOrDelMessage.do";
    //提交评价
    String Submit_PingLun = IPWAIWANG + "mg/my/insertEvaluate.do";
    //充值积分支付接口
    String ChongZhi_JiFen = IPWAIWANG + "mg/my/my_integral_pay.do";
    //我的数量信息
    String Mine_Infrmation = IPWAIWANG + "mg/my/select_user_integral.do";
    String BangDing_Car = IPWAIWANG + "mg/my/binding_mylovecar.do";
    String Get_BangDing_Car = IPWAIWANG + "mg/my/backlovecarinfo.do";
    //有证练车支付
    String Practice_Pay = IPWAIWANG + "mg/practiceDriving/payPracticeOrder.do";
    String WZ_Practice_Pay = IPWAIWANG + "mg/practiceDriving/payPracticeOrderWZ.do";
    //二手车租车支付
    String UsedCar_Pay = IPWAIWANG + "mg/twoCar/pay_twoCarRent.do";
    //我的钱包
    //教练的地图
    String Driver_Map = IPWAIWANG + "mg/practiceDriving/driverJWDList.do";
    //有证练车用户确认完成
    String User_End_Practicing = IPWAIWANG + "mg/practiceDriving/userEndPracticing.do";
    //评价驾校教练
    String PingJia_DriverSchool_Trainer = IPWAIWANG + "mg/driver_school/evaluateSchoolDriver.do";
    //获取教练信息
    String Get_WZ_Driver_Information = IPWAIWANG + "mg/practiceDriving/getDriverHeadName.do";
    //培训支付
    String Train_Pay = IPWAIWANG + "driverSchoolEP/payEP.do";
    //直通车支付
    String ZTC_Pay = IPWAIWANG + "mg/driver_school_order/pay_ZTC.do";
    String ChaXun_Pay = IPWAIWANG + "secondhandcarevaluation/pay_insurance_notify.do";
    //练车品牌列表
    String PPLB = IPWAIWANG + "mg/brandInfo/selectbrandinfo.do";
    //单一品牌的列表
    String CAR = IPWAIWANG + "mg/brandInfo/selectbrandinfobyid.do";
    //加油支付宝接口
    String JYZFB = IPWAIWANG + "mg/orderinfo/payjiayou.do";
    //f头像的token
    String TOUXIANG = "http://112.103.231.134:8082/" + "mg/qiniuun/token.do";
    String GETYOUHUIQUAN = IPWAIWANG + "mg/evaluate/insertmycoupon.do";
    //预约列表
    String Order_List = IPWAIWANG + "yueche/myYuyueList.do";
    //预约
    String Order_List_order = IPWAIWANG + "yueche/yuyue.do";
    //取消预约
    String Order_List_del = IPWAIWANG + "yueche/yuyueDel.do";
    //
    String Order_SaoMa = IPWAIWANG + "yueche/saoma.do";
    String Order_PingJia = IPWAIWANG + "yueche/studentEvaDriver.do";


    //代驾相关接口
    String ChangYong_Address= IPWAIWANG + "substitute/selectAddressList.do";
    //添加常用地址
    String Add_ChangYong_Address= IPWAIWANG + "substitute/insertAddress.do";
    String Update_ChangYong_Address= IPWAIWANG + "substitute/updateAddress.do";
    String getSubstituteDriverInfoByJobNumber= IPWAIWANG + "substitute/getSubstituteDriverInfoByJobNumber.do";
    //获取订单信息
    String Get_Order= IPWAIWANG + "substitute/getSubstituteOrderDetail.do";
    String Pay_Order_One= IPWAIWANG + "api/userindex/userPay";
    //获取司机经纬度
    String DaiJia_location= IPWAIWANG + "substitute/getSubstituteDriverJWD.do";
    //代叫电话
    String PingTai_Constants= IPWAIWANG + "substitute/getSysValue.do";
    //评价代驾
    String PingJia_DaiJia= IPWAIWANG + "substitute/scoreSubstituteOrder.do";
    //取消订单
    String Quxiao_Indent= IPWAIWANG + "substitute/cancleSubstituteOrder.do";
    //获取司机位置
    String SJ = IPWAIWANG + "substitute/getDriverJWDByOrderNumber.do";
    //根据useid获取代驾订单
    String Get_Number_UserId = IPWAIWANG + "substitute/getOrderNumberByUserId.do";
    //底部收藏资讯、问答、视频或取消收藏

    //车知乎转发计数
    String Czh_Share_number = IPWAIWANG + "mg/my/shareRecord.do";
    //车生活 金融服务
//更新经纬度
    String updatelonlatuser = IPWAIWANG +"mg/home/updatelonlatuser.do";
    String drivingList = IPWAIWANG +"api/Userindex/drivingList";
    String cancelOrder = IPWAIWANG +"api/Userindex/cancelOrder";
    String orderSave = IPWAIWANG +"api/Userindex/orderSave";
    String sendSMS = IPWAIWANG +"index.php/api/Sms/send";
    String LOGIN = IPWAIWANG + "index.php/api/User/mobilelogin";
    String orderBudgetPrice = IPWAIWANG + "api/Userindex/orderBudgetPrice";
    String orderDetails = IPWAIWANG + "api/Userindex/orderDetails";
    String userOrderDetails = IPWAIWANG + "api/Userindex/userOrderDetails";

    String orderList = IPWAIWANG + "api/Drivinguser/orderList";
    String mine_center = IPWAIWANG + "api/Userindex/myuserinfo";
    String userInfoEdit = IPWAIWANG + "api/Userindex/userInfoEdit";
    String userInfoEditIdentity = IPWAIWANG + "api/Userindex/userInfoEditIdentity";
    String updateOrderStatus = IPWAIWANG + "api/Drivingindex/updateOrderStatus";
    String orderAssess = IPWAIWANG + "api/Userindex/orderAssess";
    String entranceSave = IPWAIWANG + "api/tatapublic/entranceSave";
}

