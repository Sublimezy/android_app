package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean
 * @ClassName: ProjectListBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2021/2/5 9:03
 */
public class ProjectListBean {

    /**
     * subject_id : 5
     * sub_type : 2
     * subject : 倒车入库
     */

    private int subject_id;
    private String sub_type;
    private String subject;
    private boolean check_or_not;

    public boolean isCheck_or_not() {
        return check_or_not;
    }

    public void setCheck_or_not(boolean check_or_not) {
        this.check_or_not = check_or_not;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
