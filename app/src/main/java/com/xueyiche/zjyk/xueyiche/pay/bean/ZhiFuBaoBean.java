package com.xueyiche.zjyk.xueyiche.pay.bean;

/**
 * Created by ZL on 2018/2/3.
 */
public class ZhiFuBaoBean {


    /**
     * code : 1
     * msg : 请求成功
     * time : 1650251383
     * data : app_id=2017032906454932&format=JSON&charset=utf-8&sign_type=RSA2&version=1.0&notify_url=http%3A%2F%2Fguanli.xueyiche.vip%3A101%2Faddons%2Fepay%2Fapi%2Fnotifyx%2Ftype%2Falipay&timestamp=2022-04-18+11%3A09%3A43&biz_content=%7B%22out_trade_no%22%3A%22A2022041810353904400%22%2C%22total_amount%22%3A%221%22%2C%22subject%22%3A%22%5Cu5b66%5Cu6613%5Cu8f66-%5Cu4ee3%5Cu9a7e%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&method=alipay.trade.app.pay&sign=e1%2FU%2F5ninlS9VzzDL3HKuSrbyqT1cQ1CciG28WI6z4z1UMyuI%2FVCVjWSw%2FcnVyGyEhwQ77a%2FBK%2F2CPDKn3ZUHL94QZOelaWoB73p%2BeSoJB9lk7Cv2jJGy8DNuxZThbgg%2FWPPXl4bU3KZITbg35NyCSzeU8i%2BttIVwtdjd3vI8A5OkITHWel6aE%2Bsq1VMLYYGbzYfM%2FHKQByptvH4zKD5rng41bDeY5GrhU1sj7EkvV5LpdwM31rSKu6IvOej0yHnpcK78ZRNRBUxYXnHVMWfhtkdMUyV31ZHd9rYq9yeUGOu%2FSEvV0SyKMaffJoTMgjveegxxJtoojS9LGBq4%2FvyLg%3D%3D
     */

    private int code;
    private String msg;
    private String time;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
