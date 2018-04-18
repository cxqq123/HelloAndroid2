package com.cx.helloandroid2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cx on 2018/4/18.
 */

public class DBHelper extends SQLiteOpenHelper{

    private final static String mDbName = "cx.db";
    private final static int mDbVersion = 1;
    private static DBHelper mInstance = null;

    private String sql_CreateTable_User = "CREATE TABLE [tb_User] ("
            //+ "[_ID]	integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "[usID]	long NOT NULL DEFAULT 0,"
            + "[usLoginName]	varchar(50) NOT NULL COLLATE NOCASE DEFAULT '',"
            + "[usPassWord]	varchar(50) NOT NULL COLLATE NOCASE DEFAULT '',"
            + "[usSign]	varchar(50) NOT NULL COLLATE NOCASE DEFAULT '',"
            + ")";

    private String sql_CreateTable_Friend = "CREATE TABLE [tb_Friend] ("
            + "[frID]	long NOT NULL DEFAULT 0,"
            + "[frUserID]	long NOT NULL DEFAULT 0,"
            + "[frName]	varchar(50) NOT NULL COLLATE NOCASE DEFAULT '',"
            + "[frMsg]	varchar(200) NOT NULL COLLATE NOCASE DEFAULT '',"
            + ")";

    private String sql_CreateTable_Chat = "CREATE TABLE [tb_Chat] ("
            + "[chID]	long NOT NULL DEFAULT 0,"
            + "[chUserID]	long NOT NULL DEFAULT 0,"
            + "[chName]	varchar(50) NOT NULL COLLATE NOCASE DEFAULT '',"
            + "[chMsg]	varchar(200) NOT NULL COLLATE NOCASE DEFAULT '',"
            + ")";

    private Context mContext = null;
    private DBHelper(Context context){
        super(context,mDbName,null,mDbVersion);
        mContext = context;
    }

    public static DBHelper getInstance(Context context){
        if (mInstance == null){
            mInstance = new DBHelper(context);
        }
        return mInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_CreateTable_User);
        db.execSQL(sql_CreateTable_Friend);
        db.execSQL(sql_CreateTable_Chat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
