package com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean;

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
 * #            Created by 張某人 on 2021/2/5/21:32 .
 * #            com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean
 * #            xueyiche
 */
public class StudentsIndentContentBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"id_number":"11256658584948","cost_money":"","trainee_id":"学易车用户的id1345678","entry_project":"学易车驾考","trainee_name":"吴宗宪","trainee_phone":"13946665585","pay_type":"","invent_people":"","driving_order_number":"E1612665043121","discounted_price":""}
     */

    private String msg;
    private int code;
    private ContentBean content;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id_number : 11256658584948
         * cost_money :
         * trainee_id : 学易车用户的id1345678
         * entry_project : 学易车驾考
         * trainee_name : 吴宗宪
         * trainee_phone : 13946665585
         * pay_type :
         * invent_people :
         * driving_order_number : E1612665043121
         * discounted_price :
         */

        private String id_number;
        private String cost_money;
        private String trainee_id;
        private String entry_project;
        private String trainee_name;
        private String trainee_phone;
        private String pay_type;
        private String invent_people;
        private String driving_order_number;
        private String discounted_price;

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getCost_money() {
            return cost_money;
        }

        public void setCost_money(String cost_money) {
            this.cost_money = cost_money;
        }

        public String getTrainee_id() {
            return trainee_id;
        }

        public void setTrainee_id(String trainee_id) {
            this.trainee_id = trainee_id;
        }

        public String getEntry_project() {
            return entry_project;
        }

        public void setEntry_project(String entry_project) {
            this.entry_project = entry_project;
        }

        public String getTrainee_name() {
            return trainee_name;
        }

        public void setTrainee_name(String trainee_name) {
            this.trainee_name = trainee_name;
        }

        public String getTrainee_phone() {
            return trainee_phone;
        }

        public void setTrainee_phone(String trainee_phone) {
            this.trainee_phone = trainee_phone;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getInvent_people() {
            return invent_people;
        }

        public void setInvent_people(String invent_people) {
            this.invent_people = invent_people;
        }

        public String getDriving_order_number() {
            return driving_order_number;
        }

        public void setDriving_order_number(String driving_order_number) {
            this.driving_order_number = driving_order_number;
        }

        public String getDiscounted_price() {
            return discounted_price;
        }

        public void setDiscounted_price(String discounted_price) {
            this.discounted_price = discounted_price;
        }
    }
}
