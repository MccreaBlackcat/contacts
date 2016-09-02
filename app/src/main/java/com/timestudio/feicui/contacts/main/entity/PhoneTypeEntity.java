package com.timestudio.feicui.contacts.main.entity;

/**
 * @author   Created by ShenGqiang on 2016/8/26.
 * @description 手机类型实体类
 */
public class PhoneTypeEntity {
    private String phoneTypeName;
    private String subtable;

    public PhoneTypeEntity(String phoneTypeName, String subtable) {
        this.phoneTypeName = phoneTypeName;
        this.subtable = subtable;
    }

    public String getSubtable() {
        return subtable;
    }

    public void setSubtable(String subtable) {
        this.subtable = subtable;
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
