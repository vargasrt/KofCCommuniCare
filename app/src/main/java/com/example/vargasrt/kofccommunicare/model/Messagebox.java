package com.example.vargasrt.kofccommunicare.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Messagebox extends RealmObject {

    @PrimaryKey
    private long msgid;
    private String msgtimestamp;
    private String msgbound;
    private String msgparam1;
    private String msgparam2;
    private String msgparam3;
    private String imageSenderUrl;


    // Standard getters & setters generated by your IDE…
    public long getMsgid() {
        return msgid;
    }
    public void setMsgid(long msgid) {
        this.msgid = msgid;
    }

    public String getMsgtimestamp() {
        return msgtimestamp;
    }
    public void setMsgtimestamp(String msgtimestamp) {
        this.msgtimestamp = msgtimestamp;
    }

    public String getMsgbound() {
        return msgbound;
    }
    public void setMsgbound(String msgbound) {
        this.msgbound = msgbound;
    }

    public String getMsgparam1() {
        return msgparam1;
    }
    public void setMsgparam1(String msgparam1) {
        this.msgparam1 = msgparam1;
    }

    public String getMsgparam2() {
        return msgparam2;
    }
    public void setMsgparam2(String msgparam2) {
        this.msgparam2 = msgparam2;
    }

    public String getMsgparam3() {
        return msgparam3;
    }
    public void setMsgparam3(String msgparam3) {
        this.msgparam3 = msgparam3;
    }

    public String getImageSenderUrl() { return imageSenderUrl; }
    public void setImageSenderUrl(String imageSenderUrl) {
        this.imageSenderUrl = imageSenderUrl;
    }
}

