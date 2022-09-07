package com.example.foreground1;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallLogModel {
    @JsonProperty("callPhoneNumber")
    String callPhoneNumber;
    @JsonProperty
    String contactName;
    @JsonProperty("callTypeCode")
    String callTypeCode;
    @JsonProperty("callDate")
    SimpleDateFormat callDate;
    @JsonProperty("callTime")
    String callTime;
    @JsonProperty("callDuration")
    String callDuration;
    @JsonProperty("userNumber")
    String userNumber;

    public CallLogModel(String callPhoneNumber, String contactName, String callTypeCode, SimpleDateFormat callDate, String callTime, String callDuration, String userNumber) {
        this.callPhoneNumber = callPhoneNumber;
        this.contactName = contactName;
        this.callTypeCode = callTypeCode;
        this.callDate = callDate;
        this.callTime = callTime;
        this.callDuration = callDuration;
        this.userNumber = userNumber;
    }

    public String getCallPhoneNumber() {
        return callPhoneNumber;
    }

    public void setCallPhoneNumber(String callPhoneNumber) {
        this.callPhoneNumber = callPhoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCallTypeCode() {
        return callTypeCode;
    }

    public void setCallTypeCode(String callTypeCode) {
        this.callTypeCode = callTypeCode;
    }

    public SimpleDateFormat getCallDate() {
        return callDate;
    }

    public void setCallDate(SimpleDateFormat callDate) {
        this.callDate = callDate;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
    //    public CallLogModel(String phNumber, String contactName, String callType, String callDate, String callTime, String callDuration,String userNumber) {
//        this.callPhoneNumber = phNumber;
//        this.contactName = contactName;
//        this.callTypeCode = callType;
//        this.callDate = callDate;
//        this.callTime = callTime;
//        this.callDuration = callDuration;
//        this.userNumber = userNumber;
//    }
//
//    public String getCallPhoneNumber() {
//        return callPhoneNumber;
//    }
//
//    public void setCallPhoneNumber(String callPhoneNumber) {
//        this.callPhoneNumber = callPhoneNumber;
//    }
//
//    public String getContactName() {
//        return contactName;
//    }
//
//    public void setContactName(String contactName) {
//        this.contactName = contactName;
//    }
//
//    public String getCallTypeCode() {
//        return callTypeCode;
//    }
//
//    public void setCallTypeCode(String callTypeCode) {
//        this.callTypeCode = callTypeCode;
//    }
//
//    public String getCallDate() {
//        return callDate;
//    }
//
//    public void setCallDate(String callDate) {
//        this.callDate = callDate;
//    }
//
//    public String getCallTime() {
//        return callTime;
//    }
//
//    public void setCallTime(String callTime) {
//        this.callTime = callTime;
//    }
//
//    public String getCallDuration() {
//        return callDuration;
//    }
//
//    public void setCallDuration(String callDuration) {
//        this.callDuration = callDuration;
//    }
//
//    public String getUserNumber() {
//        return userNumber;
//    }
//
//    public void setUserNumber(String userNumber) {
//        this.userNumber = userNumber;
//    }
}
