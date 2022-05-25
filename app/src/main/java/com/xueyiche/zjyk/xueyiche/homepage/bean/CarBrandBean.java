package com.xueyiche.zjyk.xueyiche.homepage.bean;

import java.util.List;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 2022/5/25/9:26 下午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage.bean
 * #            xueyiche5.0
 */
public class CarBrandBean {

    /**
     * code : 1
     * msg : 请求成功
     * time : 1653485140
     * data : ["丰田","大众","福特","本田","日产","现代","雪佛兰","起亚","雷诺","奔驰","宝马","标致","奥迪","菲亚特","玛鲁蒂","马自达","铃木","长安","jeep","别克","吉利","斯柯达","五菱","欧宝","斯巴鲁","雪铁龙","三菱","宝骏","东风","大发","哈弗","ram","gmc","雷克萨斯","达契亚","道奇","沃尔沃","奇瑞","北汽","塔塔","传祺","saipa","西雅特","路虎","马恒达","江淮","荣威","比亚迪","福田","mini"]
     */

    private int code;
    private String msg;
    private String time;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
