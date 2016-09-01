package com.timestudio.feicui.contacts.main.entity;

/**
 * Created by thinkpad on 2016/8/26.
 */
public class PhoneTypeEntity {
    private String phoneTypeName;

    public PhoneTypeEntity() {
    }

    public PhoneTypeEntity(String phoneTypeName) {
        this.phoneTypeName=phoneTypeName;
    }

    public String getPhoneTypeName() {
        return phoneTypeName;
    }

    public void setPhoneTypeName(String phoneTypeName) {
        this.phoneTypeName = phoneTypeName;
    }
}
