package com.timestudio.feicui.contacts.main.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.timestudio.feicui.contacts.R;
import com.timestudio.feicui.contacts.main.entry.PhoneNumberEntry;
import com.timestudio.feicui.contacts.main.entry.PhoneTypeEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author  Created by thinkpad on 2016/8/31.
 * @description  对数据库的一些操作类
 */
public class DatabaseOperation {

    public static final int READ_DB_TYPE = 0; //查询电话类型表
    public static final int READ_DB_PHONENUMBER = 1; //查询电话号码表

    //存放数据库的目录
    public static final String DATABASE_PATH = "/data/data/com.timestudio.feicui.contacts/databases";
    /**
     * @description 将raw文件夹中随APK发布的数据库引入到本地数据库目录中
     */
    public static void importDatabase(Context context){
        Log.i("aaa","成功0");
        try{
            //创建数据库目录，如果目录不存在，创建目录
            File dirFile = new File(DATABASE_PATH);
            if(!dirFile.exists()){
                dirFile.mkdir();
                Log.i("aaa","成功1");
            }
            //创建被导入的数据库文件
            File file = new File(DATABASE_PATH,"phone.db");
            //判断文件是否存在，如果不存在则创建该文件，若存在则返回
            if(!file.exists()){
                file.createNewFile();
                Log.i("aaa","成功2");
            }else {
                return;
            }
            //获取自带的数据库输入流
            InputStream ip = context.getResources().openRawResource(R.raw.phone);
            Log.i("aaa","成功3");
            //创建被导入数据库文件的输出流
            FileOutputStream fop = new FileOutputStream(file);
            //创建缓冲区
            byte[] buffer = new byte[1024];
            //将数据读入缓冲区，并且写入输出流
            while (ip.read(buffer) != -1){
                fop.write(buffer);
                //创建新的缓冲区
                buffer = new byte[1024];
            }
            Log.i("aaa","成功4");
            //关闭输入输出流
            ip.close();
            fop.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @Description 从本地数据库中读取数据，返回一个游标
     * @param selection 选择查询那个表
     * @param type 查询电话号码表示，需要传过来的类型（可以为空）
     */
    public static Cursor readDatabase(int selection,String type){
        //根据自定义的帮助类来获取数据库的database对象
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + "/phone.db",null);
        Cursor cursor = null; //声明游标，存放查询到的数据集合
        //查询数据库
        if(selection == READ_DB_TYPE){
            cursor = db.query(
                    PhoneTypeEntry.TABLE_NAME, //表名
                    new String[] {PhoneTypeEntry.COLUMNS_NAME_TYPE,PhoneTypeEntry.COLUMNS_NAME_SUBTABLE}, //查询的列
                    null, //WHERE
                    null, //对WHERE中的占位补全
                    null, //GROUP BY
                    null, //HAVING
                    null  //ORDER BY
            );
        }else
        if (selection == READ_DB_PHONENUMBER){
            cursor = db.query(
                PhoneNumberEntry.TABLE_NAME, //表名
                new String[] {PhoneNumberEntry.COLUMNS_NAME_PHONENAME,PhoneNumberEntry.COLUMNS_NAME_PHONENUMBER}, //查询的列
                PhoneNumberEntry.COLUMNS_NAME_PHONETYPE + " = '" + type + "'", //WHERE
                null, //对WHERE中的占位补全
                null, //GROUP BY
                null, //HAVING
                null  //ORDER BY
            );
        }
        return cursor;
    }
}
