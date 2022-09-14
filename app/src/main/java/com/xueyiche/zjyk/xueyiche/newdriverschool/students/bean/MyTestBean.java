package com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean;

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
 * #            Created by 張某人 on 2021/1/28/9:20 .
 * #            com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean
 * #            xueyiche
 */
public class MyTestBean {

    /**
     * msg : 操作成功！
     * code : 200
     * content : {"hello_world":"欢迎 : 方文山 同学，这是您来到学易车的第14天","all_list":[{"subject_id":2,"sub_type":"1","subject":"科目二","cross_subject":"1","list":[{"subject_id":5,"sub_type":"2","subject":"倒车入库"},{"subject_id":6,"sub_type":"2","subject":"坡道定点停车与起步"},{"subject_id":7,"sub_type":"2","subject":"直角转弯"},{"subject_id":8,"sub_type":"2","subject":"侧方停车"},{"subject_id":9,"sub_type":"2","subject":"曲线行驶"}],"sub_times":"0"},{"subject_id":3,"sub_type":"1","subject":"科目三","cross_subject":"0","list":[{"subject_id":10,"sub_type":"3","subject":"上车准备"},{"subject_id":11,"sub_type":"3","subject":"夜间停车"},{"subject_id":12,"sub_type":"3","subject":"起步"},{"subject_id":13,"sub_type":"3","subject":"侧方停车"},{"subject_id":14,"sub_type":"3","subject":"直线行驶"},{"subject_id":15,"sub_type":"3","subject":"加减挡位"},{"subject_id":16,"sub_type":"3","subject":"变更车道"},{"subject_id":17,"sub_type":"3","subject":"直行通过路口"},{"subject_id":18,"sub_type":"3","subject":"路口左转弯"},{"subject_id":19,"sub_type":"3","subject":"路口右转弯"}],"sub_times":"0"}]}
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
         * hello_world : 欢迎 : 方文山 同学，这是您来到学易车的第14天
         * all_list : [{"subject_id":2,"sub_type":"1","subject":"科目二","cross_subject":"1","list":[{"subject_id":5,"sub_type":"2","subject":"倒车入库"},{"subject_id":6,"sub_type":"2","subject":"坡道定点停车与起步"},{"subject_id":7,"sub_type":"2","subject":"直角转弯"},{"subject_id":8,"sub_type":"2","subject":"侧方停车"},{"subject_id":9,"sub_type":"2","subject":"曲线行驶"}],"sub_times":"0"},{"subject_id":3,"sub_type":"1","subject":"科目三","cross_subject":"0","list":[{"subject_id":10,"sub_type":"3","subject":"上车准备"},{"subject_id":11,"sub_type":"3","subject":"夜间停车"},{"subject_id":12,"sub_type":"3","subject":"起步"},{"subject_id":13,"sub_type":"3","subject":"侧方停车"},{"subject_id":14,"sub_type":"3","subject":"直线行驶"},{"subject_id":15,"sub_type":"3","subject":"加减挡位"},{"subject_id":16,"sub_type":"3","subject":"变更车道"},{"subject_id":17,"sub_type":"3","subject":"直行通过路口"},{"subject_id":18,"sub_type":"3","subject":"路口左转弯"},{"subject_id":19,"sub_type":"3","subject":"路口右转弯"}],"sub_times":"0"}]
         */

        private String hello_world;
        private String subject_type;
        private List<AllListBean> all_list;

        public String getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(String subject_type) {
            this.subject_type = subject_type;
        }

        public String getHello_world() {
            return hello_world;
        }

        public void setHello_world(String hello_world) {
            this.hello_world = hello_world;
        }

        public List<AllListBean> getAll_list() {
            return all_list;
        }

        public void setAll_list(List<AllListBean> all_list) {
            this.all_list = all_list;
        }

        public static class AllListBean {
            /**
             * subject_id : 2
             * sub_type : 1
             * subject : 科目二
             * cross_subject : 1
             * list : [{"subject_id":5,"sub_type":"2","subject":"倒车入库"},{"subject_id":6,"sub_type":"2","subject":"坡道定点停车与起步"},{"subject_id":7,"sub_type":"2","subject":"直角转弯"},{"subject_id":8,"sub_type":"2","subject":"侧方停车"},{"subject_id":9,"sub_type":"2","subject":"曲线行驶"}]
             * sub_times : 0
             */

            private int subject_id;
            private String sub_type;
            private String subject;
            private String cross_subject;
            private String sub_times;
            private String subject_one_four;
            private List<ListBean> list;

            public String getSubject_one_four() {
                return subject_one_four;
            }

            public void setSubject_one_four(String subject_one_four) {
                this.subject_one_four = subject_one_four;
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

            public String getCross_subject() {
                return cross_subject;
            }

            public void setCross_subject(String cross_subject) {
                this.cross_subject = cross_subject;
            }

            public String getSub_times() {
                return sub_times;
            }

            public void setSub_times(String sub_times) {
                this.sub_times = sub_times;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * subject_id : 5
                 * sub_type : 2
                 * subject : 倒车入库
                 */

                private int subject_id;
                private String sub_type;
                private String subject;

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
        }
    }
}
