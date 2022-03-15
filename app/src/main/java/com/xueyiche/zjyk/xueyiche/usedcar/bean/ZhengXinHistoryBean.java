package com.xueyiche.zjyk.xueyiche.usedcar.bean;

import java.util.List;

/**
 * Created by ZL on 2018/11/2.
 */
public class ZhengXinHistoryBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : [{"credit_num":"T1541062226017","basic_name":"哈哈","address":"","credit_sfz":"232131199002122921","deal_with_type":"0","credit_json":"{\"als_fst_cell_nbank_inten\":\"Review\",\"rs_platform\":\"offline\",\"rs_product_name\":\"汽车金融评分\",\"rs_product_type\":\"100086\",\"rs_scene\":\"lend\",\"rs_strategy_id\":\"STR0002458\",\"rs_strategy_version\":\"1.0\",\"scoreautolea\":\"653\",\"swift_number\":\"3002504_20181101165027_2462\"}","salesman_number":"896692","other_type":"1","credit_type":"0","credit_phone":"66647a6a6f70636e637a77","credit_id":39,"user_id":"11737464646711e8b10e5254f2dc841f","danbao_id":"23","find_system":"2018-11-01 16:50:27","credit_pass":"2"},{"credit_num":"T1541062288386","basic_name":"哈哈","address":"","credit_sfz":"232131199002122921","deal_with_type":"0","credit_json":"{\"als_fst_cell_nbank_inteday\":\"71\",\"als_fst_id_nbank_inteday\":\"71\",\"als_lst_cell_nbank_consnum\":\"1\",\"als_lst_cell_nbank_csinteday\":\"1\",\"als_lst_cell_nbank_inteday\":\"71\",\"als_lst_id_nbank_consnum\":\"1\",\"als_lst_id_nbank_csinteday\":\"1\",\"als_lst_id_nbank_inteday\":\"71\",\"als_m12_cell_avg_monnum\":\"1.00\",\"als_m12_cell_max_inteday\":\"\",\"als_m12_cell_manum\":\"0\",\"als_m6_id_rel_allnum\":\"1\",\"als_m6_id_rel_orgnum\":\"1\",\"als_m6_id_tot_mons\":\"1\",\"code\":\"00\",\"flag_applyloanstr\":\"1\",\"flag_consumption_c\":\"0\",\"flag_riskstrategy\":\"1\",\"flag_score\":\"1\",\"flag_specialList_c\":\"0\",\"flag_stability_c\":\"0\",\"rs_Score_decision\":\"Review\",\"rs_Score_scoreautolea\":\"653\",\"rs_final_decision\":\"Review\",\"rs_platform\":\"offline\",\"rs_product_name\":\"汽车金融评分\",\"rs_product_type\":\"100086\",\"rs_scene\":\"lend\",\"rs_strategy_id\":\"STR0002458\",\"rs_strategy_version\":\"1.0\",\"scoreautolea\":\"653\",\"swift_number\":\"3002504_20181101165129_8694\"}","salesman_number":"896692","other_type":"1","credit_type":"0","credit_phone":"66647a6a6f70636e637a77","credit_id":40,"user_id":"11737464646711e8b10e5254f2dc841f","danbao_id":"23","find_system":"2018-11-01 16:51:29","credit_pass":"2"}]
     */

    private String msg;
    private int code;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * credit_num : T1541062226017
         * basic_name : 哈哈
         * address :
         * credit_sfz : 232131199002122921
         * deal_with_type : 0
         * credit_json : {"als_fst_cell_nbank_inten":"Review","rs_platform":"offline","rs_product_name":"汽车金融评分","rs_product_type":"100086","rs_scene":"lend","rs_strategy_id":"STR0002458","rs_strategy_version":"1.0","scoreautolea":"653","swift_number":"3002504_20181101165027_2462"}
         * salesman_number : 896692
         * other_type : 1
         * credit_type : 0
         * credit_phone : 66647a6a6f70636e637a77
         * credit_id : 39
         * user_id : 11737464646711e8b10e5254f2dc841f
         * danbao_id : 23
         * find_system : 2018-11-01 16:50:27
         * credit_pass : 2
         */

        private String credit_num;
        private String find_system;
        private String credit_url;

        public String getCredit_url() {
            return credit_url;
        }

        public void setCredit_url(String credit_url) {
            this.credit_url = credit_url;
        }

        public String getCredit_num() {
            return credit_num;
        }

        public void setCredit_num(String credit_num) {
            this.credit_num = credit_num;
        }

        public String getFind_system() {
            return find_system;
        }

        public void setFind_system(String find_system) {
            this.find_system = find_system;
        }
    }
}
