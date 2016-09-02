package com.timestudio.feicui.contacts.main.entry;

import android.provider.BaseColumns;

/**
 *  @author  Created by ShenGqing on 2016/9/1.
 *  @Description 子表契约类
 */
public class PhoneNumberEntry implements BaseColumns {
    //表名
    public static final String TABLE_NAME = "subtable";
    //列名
    public static final String COLUMNS_NAME_PHONETYPE = "type"; //电话类型
    public static final String COLUMNS_NAME_PHONENAME = "name"; //电话名称
    public static final String COLUMNS_NAME_PHONENUMBER = "phone";//电话号码
}
