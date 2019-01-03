package com.thzy.myapplication.data;

import java.util.List;

/**
 * Created by sj on 2018/5/22.
 */

public class XKlDataBean {
    /**
     * Code : 0
     * Message : 成功
     * Data : [{"Sid":"108","logo":"http://xn.koudai10.com/HaiYouHua/admin/Public/upload/5b037d5c77624.png","name":"曲奇饼干","advantage":"香港进口，珍妮聪明小熊曲奇饼干","trait":"","position":"56~198元","day":"1~3kg","dayNum":"今日已有65人购买","application_Address":"","Ed":"价格","Qx":"规格","Sq":"购买"},{"Sid":"107","logo":"http://xn.koudai10.com/HaiYouHua/admin/Public/upload/5b037c4102a5c.png","name":"彩虹糖","advantage":"彩虹糖原果味","trait":"","position":"38~68元","day":"1~3kg","dayNum":"今日已有35人购买","application_Address":"","Ed":"价格","Qx":"规格","Sq":"购买"},{"Sid":"106","logo":"http://xn.koudai10.com/HaiYouHua/admin/Public/upload/5b037b5d5b913.png","name":"棒棒糖","advantage":"不二家棒棒糖，牛奶糖，儿童休闲零食","trait":"","position":"38~68元","day":"1~3kg","dayNum":"今日已有35人购买","application_Address":"","Ed":"价格","Qx":"规格","Sq":"购买"},{"Sid":"105","logo":"http://xn.koudai10.com/HaiYouHua/admin/Public/upload/5b037a85dfff6.png","name":"真嘻环花生","advantage":"黑皮花生，带壳烘炒","trait":"","position":"39~120元","day":"1~3kg","dayNum":"今日已有35人购买","application_Address":"","Ed":"价格","Qx":"规格","Sq":"购买"},{"Sid":"104","logo":"http://xn.koudai10.com/HaiYouHua/admin/Public/upload/5b0283d7542a2.png","name":"蓝月亮洗衣液","advantage":"自然清香，亮白增艳","trait":"","position":"38~68元","day":"1~3kg","dayNum":"今日已有35人购买","application_Address":"","Ed":"价格","Qx":"规格","Sq":"购买"}]
     */

    private String Code;
    private String Message;
    private List<DataBean> Data;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Sid : 108
         * logo : http://xn.koudai10.com/HaiYouHua/admin/Public/upload/5b037d5c77624.png
         * name : 曲奇饼干
         * advantage : 香港进口，珍妮聪明小熊曲奇饼干
         * trait :
         * position : 56~198元
         * day : 1~3kg
         * dayNum : 今日已有65人购买
         * application_Address :
         * Ed : 价格
         * Qx : 规格
         * Sq : 购买
         */

        private String Sid;
        private String logo;
        private String name;
        private String advantage;
        private String trait;
        private String position;
        private String day;
        private String dayNum;
        private String application_Address;
        private String Ed;
        private String Qx;
        private String Sq;

        public String getSid() {
            return Sid;
        }

        public void setSid(String Sid) {
            this.Sid = Sid;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdvantage() {
            return advantage;
        }

        public void setAdvantage(String advantage) {
            this.advantage = advantage;
        }

        public String getTrait() {
            return trait;
        }

        public void setTrait(String trait) {
            this.trait = trait;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getDayNum() {
            return dayNum;
        }

        public void setDayNum(String dayNum) {
            this.dayNum = dayNum;
        }

        public String getApplication_Address() {
            return application_Address;
        }

        public void setApplication_Address(String application_Address) {
            this.application_Address = application_Address;
        }

        public String getEd() {
            return Ed;
        }

        public void setEd(String Ed) {
            this.Ed = Ed;
        }

        public String getQx() {
            return Qx;
        }

        public void setQx(String Qx) {
            this.Qx = Qx;
        }

        public String getSq() {
            return Sq;
        }

        public void setSq(String Sq) {
            this.Sq = Sq;
        }
    }
}
