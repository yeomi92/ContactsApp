package com.hanbit.contactsapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hanbit.contactsapp.domain.MemberBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hb2008 on 2017-03-08.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    final static String DATABASE_NAME="";
    final static int DATABASE_VERSION=1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    //데이터베이스 안에 테이블과 초기 데이터를 생성한다.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="";
        db.execSQL(sql);
    }

    //데이터베이스를 업그레이드한다.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Member");
        onCreate(db);
    }
    public void create(MemberBean bean){

    }
    public MemberBean selectOne(MemberBean bean){
        MemberBean temp=new MemberBean();
        return temp;
    }
    public List<MemberBean> selectSome(MemberBean bean){
        List<MemberBean> temp=new ArrayList<>();
        return temp;
    }
    public List<MemberBean> selectAll(){
        List<MemberBean> temp=new ArrayList<>();
        return temp;
    }
    public void delete(MemberBean bean){

    }

    public void update(MemberBean bean){

    }
}
