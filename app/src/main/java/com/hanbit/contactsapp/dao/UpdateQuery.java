package com.hanbit.contactsapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hb2008 on 2017-03-14.
 */

public abstract class UpdateQuery extends QueryFactory{
    SQLiteOpenHelper helper;

    public UpdateQuery(Context context) {
        super(context);
        helper = new DatabaseHelper(context);
    }


    @Override
    public SQLiteDatabase getDatabase() {
        return helper.getWritableDatabase();
    }

    public abstract void update(String sql);
}
