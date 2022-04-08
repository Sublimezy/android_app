package com.xueyiche.zjyk.xueyiche.constants;

/**
 * Created by Owner on 2016/9/20.
 */
public interface AppUrl {
    //3.0国裕测试
    String IPWAIWANG = "http://112.103.231.139:8082/";

    //3.0线上正式
//   String IPWAIWANG = "http://112.103.231.134:8082/";


    //内网---盖哥
    // String IPWAIWANG = "http://192.168.1.232:8080/";
//     String IPWAIWANG = "http://172.16.51.61:8082/";
    //内网---马哥
    // String IPWAIWANG = "http://172.16.48.26:8081/";
    //3.0测试
    //String IPWAIWANG = "http://192.168.1.235:8081/";
    //String IPWAIWANG = "http://xyc.frp2.chuantou.org/";
    //盖盖
//    String IPWAIWANG = "http://172.16.51.61:8082/";
//    String IPWAIWANG = "http://172.16.49.145:8082/";
    // php
    //String php = "http://192.168.1.121:338/wap/api/";
    //外网
    String php = "http://www.xueyiche.vip:88/wap/api/";
    //3.0
    //二手车首页
    String Used_Car_ShouYe = php + "index";
    //买车 列表 筛选 数量
    String Used_Car_Buy = php + "carSearch";
    //租车首页
    String Rent_Car = php + "rentTwoCar";
    //卖车提交信息
    String Sell_Car_Submit = php + "sellTwoCarAdd";
    //卖车
    String Sell_Car = php + "sellTwoCar";
    //二手车开通城市
    String Open_City = php + "openCity";
    //二手车品牌
    String Used_Car_Brand = php + "brand";
    //二手车车系
    String Used_Car_CarSystem = php + "carSystem";
    //二手车门店列表
    String Used_Car_Merchant = php + "merchant";
    //租车 列表 筛选 数量
    String Used_Car_Rent = php + "carSearchRent";
    //二手车车辆详情
    String Used_Car_Info = php + "carSourceInfo";
    //预约看车
    String Used_Car_BuyOrder = php + "buyOrder";
    //租车订单接口
    String Used_Car_RntOrder = php + "rentOrder";
    //收藏
    String Used_Car_Collect = php + "collect";
    //租车身份证信息提交
    String Used_Car_IdData = php + "idData";
    //身份证token
    String Used_Car_Token = php + "qiniuToken";
    //热门搜索
    String Used_Car_BrandHot = php + "brandHot";
    //二手车支付
    String Used_Car_Pay = php + "rentPayment";
    //征信查询
    String ZhengXin_ChaXun = IPWAIWANG + "ershouche/insertcreditenquiry.do";
    //征信查询其他
    String ZhengXin_ChaXun_Other = IPWAIWANG + "ershouche/getcreditenquirymoney.do";
    //征信支付
    String ZhengXin_ChaXun_Other_Pay = IPWAIWANG + "ershouche/xycreditenquirynotify.do";
    //支付成功后调用
    String ZhengXin_ChaXun_Other_Pay_Success = IPWAIWANG + "ershouche/payendmoney.do";

    String SelectCarTypeAndPrice = IPWAIWANG + "mg/practiceDriving/selectCarTypeAndPrice.do";


    String ZhengXin_History = IPWAIWANG + "ershouche/selectcreditenquiryhistory.do";
    String Insert_DeviceId = IPWAIWANG + "substitute/updateSubstituteDeviceId.do";

    //首页
    String All_ShouYe = IPWAIWANG + "mg/home/selectHome.do";
    //首页快报
    String ShouYe_KuaiBao = IPWAIWANG + "mg/my/getDiscoverListKuaiBao.do";
    //有证练车列表
    String getDriverListYZ = IPWAIWANG + "mg/practiceDriving/getDriverListYZ.do";
    String XianXing = IPWAIWANG + "mg/my/findtodycross.do";
    //车生活服务列表
    String CARLIFE_SERVICE = IPWAIWANG + "mg/carlifeinfo/showAllServices.do";
    //车生活商品列表
    String Carlife_Goods = IPWAIWANG + "mg/carlifeinfo/carList.do";
    //驾校
    String DRVIER_SCHOOL = IPWAIWANG + "mg/driver_school/showDriver_schoolList.do";
    //公共搜索的页面
    String Carlife_Search = IPWAIWANG + "mg/carlifeinfo/searchServices.do";

    String PingGu_Image= IPWAIWANG + "secondhandcarevaluation/getshotplan.do";
    String PingGu_Out= IPWAIWANG + "secondhandcarevaluation/getrejects.do";
    String PingGu_Yes= IPWAIWANG + "secondhandcarevaluation/querystate.do";
    String PingGu_History_List= IPWAIWANG + "secondhandcarevaluation/querylist.do";
    String PingGu_Once_post= IPWAIWANG + "secondhandcarevaluation/rejectsubmit.do";
    //驾校详情
    String Driver_Shcool_Content = IPWAIWANG + "mg/driver_school/selectDriver_schools.do";
    //驾校项目套餐列表
    String Driver_Shcool_Taocan_List = IPWAIWANG + "mg/driver_school/getPackageBySchoolId.do";
    String onlineshowinfo = IPWAIWANG + "mg/carlifeorder/onlineshowinfo.do";
    //驾校订单
    String Driver_Shcool_submit = IPWAIWANG + "mg/driver_school_order/enterDriver_school.do";
    String BanChe_Content = IPWAIWANG + "mg/home/detailSchoolBus.do";
    //直通车
    String Direct_Driver_Shcool_Order = IPWAIWANG + "mg/driver_school_order/enterZTC.do";
    //提交订单
    String Driver_Shcool_Post = IPWAIWANG + "mg/driver_school_order/enterSchoolOrder.do";
    //直通车提交订单
    String ZTC_Driver_Shcool_Post = IPWAIWANG + "mg/driver_school_order/enterZTCOrder.do";
    //驾校订单详情
    String Driver_Shcool_Indent_Details = IPWAIWANG + "mg/driver_school_order/getOrder_schoolDetail.do";
    //车生活订单详情
    String Car_Live_Indent_Details = IPWAIWANG + "mg/carlifeorder/car_life_orderDetail.do";
    //二手车订单详情
    String UsedCar_Indent_Details = IPWAIWANG + "mg/twoCar/twoCarOrderDetail.do";
    //驾校订单支付
    String Driver_Shcool_Indent_Pay = IPWAIWANG + "mg/driver_school_order/pay_driver_school.do";
    //车生活订单支付
    String Car_Life_Indent_Pay = IPWAIWANG + "mg/carlifeorder/pay_carLifeOrder.do";
    //车生活详情da
    String Car_Life_Content = IPWAIWANG + "mg/carlifeinfo/shopGoods.do";
    //车生活购物车下单接口
    String Car_Life_XiaDan = IPWAIWANG + "mg/carlifeorder/createCarLifeOrder.do";
    //车生活根据订单查询订单信息
    String Car_Life_Indent_Submit = IPWAIWANG + "mg/carlifeorder/selectByCarLifeOrderNumber.do";
    //我的问题的发布页面
    String My_Question_FaBu = IPWAIWANG + "mg/questions_answers/selectMyFabuList.do";
    //删除我的问题的发布页面
    String My_Question_FaBu_Delete = IPWAIWANG + "mg/questions_answers/deleteMyAnswer.do";
    //我的问题的回答页面
    String My_Question_HuiDa = IPWAIWANG + "mg/questions_answers/selectMyPinglunList.do";
    //优惠券列表
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
    //投票活动个人获奖价格查询
    String Driver_School_HuoDong_Money = IPWAIWANG + "mg/driver_school_order/getSchoolOrderByUserCards.do";
    //教练列表
    String Have_Parper_Test = IPWAIWANG + "mg/practiceDriving/getDriverList.do";
    //问答的发布
    String Question_Post = IPWAIWANG + "mg/questions_answers/release_question.do";
    //有证练车开通的城市的区域
    String Have_Parper_Test_Area = IPWAIWANG + "mg/area/selectRegionsByAreaId.do";
    //紧急联系人上传
    String JinJi_Phone = IPWAIWANG + "mg/practiceDriving/updateUserTruePhone.do";
    //参考价格
    String Can_Kao_Price = IPWAIWANG + "mg/practiceDriving/getHourPrices.do";
    //紧急联系人获取
    String JinJi_Phone_Get = IPWAIWANG + "mg/practiceDriving/getTruePhone.do";
    //获取拼团码
    String Pin_By_OrderNumber = IPWAIWANG + "mg/driver_school_order/getPinByOrderNumber.do";
    //预约，直接约教练练车发布提交数据
    String Order_Indent_Post = IPWAIWANG + "mg/practiceDriving/backPracticeOrderYZ.do";
    //无证练车的预约
    String WZ_Order_Indent_Post = IPWAIWANG + "mg/practiceDriving/backPracticeOrderWZ.do";
    //预约下单
    String Order_Post_Ok = IPWAIWANG + "mg/practiceDriving/submitPracticeOrderReady.do";
    //
    String Order_Post_Ok_WZ = IPWAIWANG + "mg/practiceDriving/submitPracticeOrderReadyWZ.do";
    //社区列表
    String Discover_SheQu_List = IPWAIWANG + "mg/my/getDiscoverList.do";
    //文章详情 社区
    String Discover_SheQu_Content = IPWAIWANG + "mg/my/getDiscoverDetails.do";
    //问答列表
    String Discover_WenDa_List = IPWAIWANG + "mg/questions_answers/selectQuestions_answers.do";
    //视频
    String Discover_Video_List = IPWAIWANG + "mg/video/showEasyVideoList.do";
    //问答详情
    String Discover_WenDa_Content = IPWAIWANG + "mg/questions_answers/selectQuestions_comments.do";
    //视频详情
    String Discover_Video_Content = IPWAIWANG + "mg/video/showEasyVideoDetail.do";
    //社区评论
    String Discover_PingLun = IPWAIWANG + "mg/my/comment.do";
    //点赞：社区、问答、视频评论
    String FaXian_DianZan_PingLun = IPWAIWANG + "mg/my/like.do";
    //点赞：社区、问答、视频内容
    String FaXian_DianZan_Content = IPWAIWANG + "mg/my/main_praise.do";
    //驾校，店铺，教练收藏列表
    String Collection_List_All = IPWAIWANG + "mg/my/collect_or_mark.do";
    //驾校，店铺，教练 加入收藏
    String Collection_Add = IPWAIWANG + "mg/my/insertCollect.do";
    //驾校，店铺，教练 删除收藏
    String Collection_Delete = IPWAIWANG + "mg/my/updateCollectOrMarkById.do";
    //教练详情
    String Driver_Details = IPWAIWANG + "mg/my/driverDetails.do";
    //无证教练详情
    String WZDriver_Details = IPWAIWANG + "mg/my/driverDetailsWZ.do";
    //我的积分 和积分明细
    String My_JiFen = IPWAIWANG + "mg/my/getMyIntegral.do";
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
    String FaBu_QueRen_WZ = IPWAIWANG + "mg/practiceDriving/pushPracticeOrderToDriverWZ.do";
    //根据教练id返回服务区域
    String Select_AreaBy_DriverId = IPWAIWANG + "mg/practiceDriving/selectAreaByDriverId.do";
    //客服问题List
    String KeFu_List = IPWAIWANG + "mg/customService/selectCustomService.do";
    //客服问题详情
    String KeFu_Content = IPWAIWANG + "mg/customService/selectQuestDetailById.do";
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
    //获取已绑定的银行卡
    String Get_Wallet_bank = IPWAIWANG + "mg/mywallet/getWalletbankById.do";
    //提现_充值
    String Insert_wallet = IPWAIWANG + "mg/mywallet/insertwallet.do";
    //登录
    String LOGIN = IPWAIWANG + "mg/usersinfo/loginByPhone.do";
    //有证练车赔偿违约金取消订单
    String Cancel_Indent_Money = IPWAIWANG + "mg/practiceDriving/cancelBreak.do";
    //获取验证码
    String YANZHENGMA = IPWAIWANG + "mg/usersinfo/getverifycode2.do";
    //考驾照推荐有奖
    String TuiJianMoney = IPWAIWANG + "mg/my/recommendPrize.do";
    //我的钱包
    String WDQB = IPWAIWANG + "mg/mywallet/selectwalletmoney.do";
    //在线客服聊天列表
    String Zaixian_Kefu_List = IPWAIWANG + "mg/message/getServiceMessageList.do";
    //在线客服回复聊天内容
    String Zaixian_User_Push = IPWAIWANG + "mg/message/pushServiceMessage.do";
    //教练的地图
    String Driver_Map = IPWAIWANG + "mg/practiceDriving/driverJWDList.do";
    //有证练车用户确认完成
    String User_End_Practicing = IPWAIWANG + "mg/practiceDriving/userEndPracticing.do";
    //无证练车
    String WZ_Practice_List = IPWAIWANG + "mg/practiceDriving/getDriverListWZ.do";
    //无证练车的套餐
    String WZ_TC_Practice_List = IPWAIWANG + "mg/practiceDriving/selectWZTaoCan.do";
    //评价驾校教练
    String PingJia_DriverSchool_Trainer = IPWAIWANG + "mg/driver_school/evaluateSchoolDriver.do";
    //教练评论学员
    String JiaoLian_Remark_content = IPWAIWANG + "mg/driver_school/schoolDriverDetailForUser.do";
    String JiaoLian_PingJia_Trainer = IPWAIWANG + "mg/driver_school/replyStudent.do";
    String JiaoLian_PingJia_Trainer_DianZan = IPWAIWANG + "mg/driver_school/insertEvaluate_praiseRecord.do";
    String JiaoLian_PingJia_Trainer_List_DianZan = IPWAIWANG + "mg/driver_school/insertEvaluate_detail_praiseRecord.do";
    //获取教练信息
    String Get_WZ_Driver_Information = IPWAIWANG + "mg/practiceDriving/getDriverHeadName.do";
    //培训报名提交
    String Train_BaoMing = IPWAIWANG + "driverSchoolEP/getOrderNumberEP.do";
    //培训套餐
    String Train_TaoCan = IPWAIWANG + "driverSchoolEP/selectEPTaocanList.do";
    //培训支付
    String Train_Pay = IPWAIWANG + "driverSchoolEP/payEP.do";
    //直通车支付
    String ZTC_Pay = IPWAIWANG + "mg/driver_school_order/pay_ZTC.do";
    String ChaXun_Pay = IPWAIWANG + "secondhandcarevaluation/pay_insurance_notify.do";
 String BanChe_List = IPWAIWANG + "mg/home/schoolBusList.do";
 String Get_ChaXun_Ordernumber = IPWAIWANG + "secondhandcarevaluation/subordernumber.do";




    //2.0

    String LiPinDuiHuan = IPWAIWANG + "insuranceConvert/insertBatch.do";
    //汽车配件品牌列表
    String PJPPLB = IPWAIWANG + "mg/brandInfo/selectcarlifebrand.do";
    //加载兑换好礼商品信息页面
    String DuiHuanGoods = IPWAIWANG + "insuranceConvert/queryGift.do";
    //练车品牌列表
    String PPLB = IPWAIWANG + "mg/brandInfo/selectbrandinfo.do";
    //单一品牌的列表
    String CAR = IPWAIWANG + "mg/brandInfo/selectbrandinfobyid.do";
    //驾校详情页
    String JXXQ = IPWAIWANG + "mg/driverschool/driverschoolinfo.do";
    //根据品牌查找车系 全部
    String PinPaiCheXi = IPWAIWANG + "mg/brandInfo/selectcarlifebrandbyid.do";
    //根据保单号获取可用总金额及兑换状态
    String BaoDanChaXun = IPWAIWANG + "insuranceConvert/selectInsuranceConvert.do";
    String ZFYL = IPWAIWANG + "mg/orderinfo/updatepay1.do";
    //签到 分享 兑换
    String QianDao = IPWAIWANG + "mg/volutioninfo/insertdetailemoney.do";
    String PingGu_Post = IPWAIWANG + "secondhandcarevaluation/submitfile.do";

    //答题成绩
    String DT = IPWAIWANG + "mg/carlifeinfo/answerwincashinfo.do";
    //答题赢现金名次列表
    String DATIYINGXIANJINLIEBIAO = IPWAIWANG + "mg/carlifeinfo/showanswerwincash.do";
    //意见反馈
    String YIJIANFANKUI = IPWAIWANG + "mg/carlifeinfo/feedbackinfo.do";
    //科目三的视频
    String KEMUSAN = IPWAIWANG + "mg/carlifeinfo/threesubjects.do";
    //模拟考试排行榜
    String MNKSPH = IPWAIWANG + "mg/carlifeinfo/showsimulation.do";
    //积分
    String JIFEN = IPWAIWANG + "mg/evaluate/showintergral.do";
    //上传绑定的银行卡
    String SCBDYHK = IPWAIWANG + "mg/mywallet/insertwalletbank.do";
    String Post_Ordernumber = IPWAIWANG + "secondhandcarevaluation/callback_iosinfo.do";
    //加油支付宝接口
    String JYZFB = IPWAIWANG + "mg/orderinfo/payjiayou.do";
    //易币消费
    String YBXF = IPWAIWANG + "mg/evaluate/consumeintergral.do";
    //新闻
    String NEWS = IPWAIWANG + "mg/brandInfo/selectnewsutils.do";
    String GGY = IPWAIWANG + "mg/advertising/advert_city_id.do";
    //平台手续费 驾校返现 有证练车开关
    String SHOUXUFEI = IPWAIWANG + "mg/carlifeinfo/loginoutmessage.do";
    //教练评价详情
    String DriverSchool_Trainer_Content = IPWAIWANG + "mg/driver_school/schoolDriverDetail.do";
    //f头像的token
    String TOUXIANG = IPWAIWANG + "mg/qiniuun/token.do";
    //易币商城
    String YIBISHOP = IPWAIWANG + "mg/volutioninfo/selecteasygoodslist.do";
    //易币商城的订单
    String YIBISHOPINDENT = IPWAIWANG + "mg/volutioninfo/selectemorderinfolist.do";
    //获取地址（收货的）
    String YIBISHOPINDENTLOCATION = IPWAIWANG + "volutioninfo/selecteasyaddress.do";
    //大地保险的token
    String CARBAOXIANTOKEN = IPWAIWANG + "qiniuun/insuranceToken.do";
    //大地保险的数据上传
    String DADIDATAPOST = IPWAIWANG + "insurance/insertInsurance.do";
    String GETYOUHUIQUAN = IPWAIWANG + "mg/evaluate/insertmycoupon.do";
    //预约列表
    String Order_List = IPWAIWANG + "yueche/myYuyueList.do";
    //预约
    String Order_List_order = IPWAIWANG + "yueche/yuyue.do";
    //取消预约
    String Order_List_del = IPWAIWANG + "yueche/yuyueDel.do";
    //预约记录
    String Order_Record = IPWAIWANG + "yueche/myYuyueRecords.do";
    //
    String Order_SaoMa = IPWAIWANG + "yueche/saoma.do";
    String Order_PingJia = IPWAIWANG + "yueche/studentEvaDriver.do";
    //获取app版本
    String App_Code = IPWAIWANG + "mg/home/getVersionForAndroid.do";
    String TiJian_List = IPWAIWANG + "mg/home/hospitalList.do";

    String Post_WeiBao = IPWAIWANG + "secondhandcarevaluation/weibao.do";
    String Get_Money = IPWAIWANG + "secondhandcarevaluation/pay_type.do";
    String Post_WeiZhang = IPWAIWANG + "secondhandcarevaluation/violation.do";
    String Post_BaoXian = IPWAIWANG + "secondhandcarevaluation/insurance_inquiry.do";
    String ChaXun_List = IPWAIWANG + "secondhandcarevaluation/baoxianlist.do";
    //答题赢现金成绩
    String WinCash_Result = IPWAIWANG + "";



    //代驾相关接口
    String ChangYong_Address= IPWAIWANG + "substitute/selectAddressList.do";
    //添加常用地址
    String Add_ChangYong_Address= IPWAIWANG + "substitute/insertAddress.do";
    String Update_ChangYong_Address= IPWAIWANG + "substitute/updateAddress.do";
    String selectdrivingsecret= IPWAIWANG + "drivingtrainee/selectdrivingsecret.do";
    //输入起止点获取预估费
    String YuGuFei= IPWAIWANG + "substitute/beforeSubstituteOrder.do";
    //立即下单
    String Now_Order= IPWAIWANG + "substitute/insertNowSubstituteOrder.do";
    //预约代驾下单
    String YuYue_Order= IPWAIWANG + "substitute/appointedSubstituteOrder.do";
    String insertSubstituteOrderDJ= IPWAIWANG + "substitute/insertSubstituteOrderDJ.do";
    String getSubstituteDriverInfoByJobNumber= IPWAIWANG + "substitute/getSubstituteDriverInfoByJobNumber.do";
    //获取订单信息
    String Get_Order= IPWAIWANG + "substitute/getSubstituteOrderDetail.do";
    //第一次支付
    String Pay_Order_One= IPWAIWANG + "substitute/paySubstituteOrder.do";
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
    String Czh_Collect = IPWAIWANG + "mg/my/updateCollectRecord.do";
    //车知乎转发计数
    String Czh_Share_number = IPWAIWANG + "mg/my/shareRecord.do";
    //车生活 金融服务
    String Czh_Baoxian_List = IPWAIWANG + "mg/carlifeinfo/baoxianList.do";
    //教练列表
    String drivingcoachlist = IPWAIWANG + "driving/drivingcoachlist.do";
//    教练释放的天数和当天的日期
    String selectcoachdays = IPWAIWANG + "driving/selectcoachdays.do";
//    学员查看-教练释放的天数和当天的日期
    String stuselectcoachdays = IPWAIWANG + "driving/stuselectcoachdays.do";
    //教练选择日期
    String addcoachdate = IPWAIWANG + "driving/addcoachdate.do";

    //教练选择时间段基本表
    String coachperiodlist = IPWAIWANG + "driving/coachperiodlist.do";
    //教练选择要释放的时间段
    String addcoachperiod = IPWAIWANG + "driving/addcoachperiod.do";

    //学习记录
    String studyinfolist = IPWAIWANG + "drivingtrainee/studyinfolist.do";
    //我的驾考
    String selectstudyprocess = IPWAIWANG + "drivingtrainee/selectstudyprocess.do";
    //我的预约
    String reservationuserlist = IPWAIWANG + "drivingtrainee/reservationuserlist.do";
    //练车详情
    String selectdrivingtraining = IPWAIWANG + "drivingtrainee/selectdrivingtraining.do";
    //身份类型
    String usertype = IPWAIWANG + "drivingtrainee/usertype.do";
    //评论
    String evaluationboth = IPWAIWANG + "drivingtrainee/evaluationboth.do";
    //插入学习记录
    String addlearningrecord = IPWAIWANG + "drivingtrainee/addlearningrecord.do";
    //报名
    String inserttrainee = IPWAIWANG + "drivingtrainee/inserttrainee.do";
    //订单详情
    String orderinfo = IPWAIWANG + "drivingtrainee/orderinfo.do";
    //报名项目
    String drivingentryproject = IPWAIWANG + "drivingtrainee/drivingentryproject.do";
    //测试支付
    String paytypedrivingorder = IPWAIWANG + "drivingtrainee/paytypedrivingorder.do";
    //预约练车
    String addpracticedriving = IPWAIWANG + "drivingtrainee/addpracticedriving.do";

    //教练信息
    String coachinfomation = IPWAIWANG + "drivingtrainee/coachinfomation.do";

    //可以预约的时间日期
    String opendrivingreservation = IPWAIWANG + "drivingtrainee/opendrivingreservation.do";

    //预约考试
    String addreservationexamination = IPWAIWANG + "drivingtrainee/addreservationexamination.do";
    //修改上下车
    String updategetonoff = IPWAIWANG + "drivingtrainee/updategetonoff.do";




    //展示教练释放的时间段
    String coachfreedperiodlist = IPWAIWANG + "driving/coachfreedperiodlist.do";
    //教练端的预约列表
    String coachreservationlist = IPWAIWANG + "drivingtrainee/coachreservationlist.do";
    //开始练车  结束练车
    String startendpracticecar = IPWAIWANG + "driving/startendpracticecar.do";

//更新经纬度
    String updatelonlatuser = IPWAIWANG +"mg/home/updatelonlatuser.do";
    //获取市和街道
    String selectRegionsByAreaId2 = IPWAIWANG +"mg/area/selectRegionsByAreaId2.do";
    String selectRegionsByAreaIdStreet = IPWAIWANG +"mg/area/selectRegionsByAreaIdStreet.do";
    String drivingList = "http://guanli.xueyiche.vip:101/api/Userindex/drivingList";
    String cancelOrder = "http://guanli.xueyiche.vip:101/api/Userindex/cancelOrder";

}

