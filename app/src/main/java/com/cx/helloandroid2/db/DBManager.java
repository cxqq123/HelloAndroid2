package com.cx.helloandroid2.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cx.helloandroid2.model.UserInfo;

/**
 * Created by cx on 2018/4/18.
 */

public class DBManager {

    private DBHelper dbHelper ;
    private SQLiteDatabase db;

    public DBManager(Context context){
        dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    //////////////////////////////////////tb_User操作///////////////////////////////////////
    public boolean hasUser(long userID){
        String sql = "select * from tb_User where usID=?";
        Cursor c = db.rawQuery(sql, new String[]{userID + ""});
        if(c != null && c.moveToNext()){
            c.close();
            return true;
        }else{
            return false;
        }
    }

    public synchronized void AddUser(long userID)
    {
        String sql = "INSERT INTO tb_User (usID,usDefaultLogin,usAddDate) VALUES(?,1,?)";
        db.execSQL(sql,
                new Object[]
                        { userID, System.currentTimeMillis() });
    }

    public synchronized void upgradeGuestUser(long userID) //0用户升级为非0时，将相关资料转换过来
    {
        Long[] parms=new Long[]{userID};
        //db.execSQL("UPDATE tb_FriendCategory set fcForUserID=? where fcForUserID<1", parms);
        String sqlCall = "UPDATE tb_CallRecord set crForUserID=? where crForUserID<1";
        String sqlCompany = "UPDATE tb_Company set cpForUserID=? where cpForUserID<1";
        String sqlContact = "UPDATE tb_Contact set ctForUserID=? where ctForUserID<1";
        String sqlConfig = "UPDATE tb_Config set coForUserID=? where coForUserID<1";
        db.execSQL(sqlCall,parms);
        db.execSQL(sqlCompany,parms);
        db.execSQL(sqlContact,parms);
        db.execSQL(sqlConfig,parms);
    }

    // 本地没有保存用户表的数据，需要保存
    public UserInfo getDefaultUser()
    {
        UserInfo info = new UserInfo();
        Cursor c = db.rawQuery("SELECT * FROM tb_User ", null);
        //Cursor c = db.rawQuery("SELECT * FROM tb_User where usDefaultLogin=1", null);
        if(c != null) {
            while (c.moveToNext()) {
                long usID = c.getLong(c.getColumnIndex("usID"));
                if (usID > 0) {
                    info = new UserInfo();
                    info.userID = usID;//c.getInt(c.getColumnIndex("usID"));
                    info.userName = c.getString(c.getColumnIndex("usLoginName"));
                    info.userSign = c.getString(c.getColumnIndex("usSign"));
                    info.password = c.getString(c.getColumnIndex("usPassWord"));
                }
            }
            c.close();
        }
        return info;
    }

    public synchronized void addUser(UserInfo userInfo)
    {
        if(hasUser(userInfo.userID)){//更新
            updateUser(userInfo);
        }else{//添加
            String sql = "INSERT INTO tb_User (usID,usLoginName,usPassWord,usSign) VALUES(?,?,?,?)";
            db.execSQL(sql,
                    new Object[]
                            { userInfo.userID , userInfo.userName , userInfo.password , userInfo.userSign});
        }
    }

    //更新用户信息
    public synchronized void updateUser(UserInfo userInfo){
        String sql = "UPDATE tb_User set usLoginName=?,usPassWord=?,usSign where usID=?";
        db.execSQL(sql,
                new Object[]{userInfo.userName, userInfo.password, userInfo.userSign,
                         userInfo.userID});
    }

    //////////////////////////////////////tb_User操作///////////////////////////////////////

}
