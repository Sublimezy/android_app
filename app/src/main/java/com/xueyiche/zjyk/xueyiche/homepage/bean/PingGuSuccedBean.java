package com.xueyiche.zjyk.xueyiche.homepage.bean;

/**
 * Created by ZL on 2019/6/4.
 */
public class PingGuSuccedBean {

    /**
     * success : 1
     * message : {"id":200002206,"gid":"d678a29763a14d78a1f22b31c327a611    ","istate":5,"finishtime":1559629933,"refusetime":"1900-01-01","refusereason":"","isurgent":false}
     */

    private int success;
    private MessageBean message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * id : 200002206
         * gid : d678a29763a14d78a1f22b31c327a611
         * istate : 5
         * finishtime : 1559629933
         * refusetime : 1900-01-01
         * refusereason :
         * isurgent : false
         */

        private int id;
        private String gid;
        private int istate;
        private int finishtime;
        private String refusetime;
        private String refusereason;
        private boolean isurgent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public int getIstate() {
            return istate;
        }

        public void setIstate(int istate) {
            this.istate = istate;
        }

        public int getFinishtime() {
            return finishtime;
        }

        public void setFinishtime(int finishtime) {
            this.finishtime = finishtime;
        }

        public String getRefusetime() {
            return refusetime;
        }

        public void setRefusetime(String refusetime) {
            this.refusetime = refusetime;
        }

        public String getRefusereason() {
            return refusereason;
        }

        public void setRefusereason(String refusereason) {
            this.refusereason = refusereason;
        }

        public boolean isIsurgent() {
            return isurgent;
        }

        public void setIsurgent(boolean isurgent) {
            this.isurgent = isurgent;
        }
    }
}

