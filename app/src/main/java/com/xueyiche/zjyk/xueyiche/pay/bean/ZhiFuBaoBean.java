package com.xueyiche.zjyk.xueyiche.pay.bean;

/**
 * Created by ZL on 2018/2/3.
 */
public class ZhiFuBaoBean {


    /**
     * content : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017032906454932&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%221dd88a20fb724275b889d910e1b28ca5%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%AD%A6%E6%98%93%E8%BD%A6%E8%AE%A2%E5%8D%95-E1525318332671%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fxyc.frp2.chuantou.org%2Fmg%2Fpayment%2Falipay%2Fnotify.do&sign=ZMjjq08fcWbVRbQMwu3Qxr0BLmUQXjKTQM4Zo%2BZGtmHRY2zpT9HYdzCg%2FjgVBEYIubUICWFm4FFXvLr%2FiyJMZWk3vPoWbCa4gL%2BLweNeQ9eTFE1QKLNfsqW3Nrt4SYZ6%2FCq0YH2lqAsILyiACXEC%2FQw91aqdzLUszVdEFXFj97oV2k5SjSf9JuLYuYdhE7K0Ia%2BY0rVxZwPMcTAcQEgyEAFasng8rZO%2FPBwES9E5dkUCsx5N2xbzJwXB5uexWvXCQ%2F8dK%2FK96XAh5KJ1d993HdI8veLNi%2F1XM0nneaBxdpOZq1io21W%2FwM922jx%2FuoLbve7XK6ukxplGFnqzj1M%2B2Q%3D%3D&sign_type=RSA2&timestamp=2018-05-03+12%3A07%3A30&version=1.0&sign=ZMjjq08fcWbVRbQMwu3Qxr0BLmUQXjKTQM4Zo%2BZGtmHRY2zpT9HYdzCg%2FjgVBEYIubUICWFm4FFXvLr%2FiyJMZWk3vPoWbCa4gL%2BLweNeQ9eTFE1QKLNfsqW3Nrt4SYZ6%2FCq0YH2lqAsILyiACXEC%2FQw91aqdzLUszVdEFXFj97oV2k5SjSf9JuLYuYdhE7K0Ia%2BY0rVxZwPMcTAcQEgyEAFasng8rZO%2FPBwES9E5dkUCsx5N2xbzJwXB5uexWvXCQ%2F8dK%2FK96XAh5KJ1d993HdI8veLNi%2F1XM0nneaBxdpOZq1io21W%2FwM922jx%2FuoLbve7XK6ukxplGFnqzj1M%2B2Q%3D%3D
     * code : 200
     * msg : 操作成功
     */

    private String content;
    private int code;
    private String msg;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
}
