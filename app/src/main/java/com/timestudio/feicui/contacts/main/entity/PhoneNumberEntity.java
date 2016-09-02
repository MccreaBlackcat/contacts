package com.timestudio.feicui.contacts.main.entity;

/**
 *@author  Created by ShenGqiang on 2016/9/2.
 * @desciption 电话号码内容的实体类
 */
public class PhoneNumberEntity {
    private String phoneName; //号码名
    private String phoneNumber; //电话号码

    public PhoneNumberEntity(String phoneName, String phoneNumber) {
        this.phoneName = phoneName;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
