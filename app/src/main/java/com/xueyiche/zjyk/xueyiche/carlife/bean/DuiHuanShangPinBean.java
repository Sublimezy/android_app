package com.xueyiche.zjyk.xueyiche.carlife.bean;

/**
 * Created by Owner on 2017/11/2.
 */
public class DuiHuanShangPinBean {
    private int main_id;
    private int cvt_num;
    private String policy_num;

    public int getMain_id() {
        return main_id;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }

    public int getCvt_num() {
        return cvt_num;
    }

    public void setCvt_num(int cvt_num) {
        this.cvt_num = cvt_num;
    }

    public String getPolicy_num() {
        return policy_num;
    }

    public void setPolicy_num(String policy_num) {
        this.policy_num = policy_num;
    }

    @Override
    public String toString() {
        return "{" +
                "main_id:" + main_id +
                ", cvt_num:" + cvt_num +
                ", policy_num:'" + policy_num + '\'' +
                '}';
    }
}
