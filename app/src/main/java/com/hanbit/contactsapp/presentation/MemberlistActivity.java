package com.hanbit.contactsapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hanbit.contactsapp.R;
import com.hanbit.contactsapp.dao.ListQuery;
import com.hanbit.contactsapp.domain.MemberBean;
import com.hanbit.contactsapp.service.ListService;

import java.util.ArrayList;

public class MemberlistActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberlist);
        final MemberBean member=new MemberBean();
        final ArrayList<MemberBean>list=new ArrayList<>();
        ListService service=new ListService() {
            @Override
            public ArrayList<MemberBean> list() {
                member.setName("홍길동");
                list.add(member);
                return list;
            }
        };
        service.list();
        findViewById(R.id.btGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MemberList mlist=new MemberList(MemberlistActivity.this);
                ListService service=new ListService() {
                    @Override
                    public ArrayList<?> list() {
                        return mlist.list("select _id AS id, name, phone, age, address, salary from member;");
                    }
                };
                ArrayList<MemberBean>list= (ArrayList<MemberBean>) service.list();
                Intent intent=new Intent(MemberlistActivity.this,MemberdetailActivity.class);
                intent.putExtra("id",list.get(0).getName());
                startActivity(intent);
            }
        });
    }
    class MemberList extends ListQuery{

        public MemberList(Context context) {
            super(context);
        }

        @Override
        public ArrayList<?> list(String sql) {
            ArrayList<MemberBean> list=new ArrayList<>();
            MemberBean bean=null;
            SQLiteDatabase db=super.getDatabase();
            Cursor cursor=db.rawQuery(sql,null);
            if(cursor!=null) {
                if (cursor.moveToFirst()){
                    do{
                        bean=new MemberBean();
                        String id=cursor.getString(cursor.getColumnIndex("id"));
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String age=cursor.getString(cursor.getColumnIndex("age"));
                        String phone=cursor.getString(cursor.getColumnIndex("phone"));
                        String address=cursor.getString(cursor.getColumnIndex("address"));
                        String salary=cursor.getString(cursor.getColumnIndex("salary"));
                        bean.setId(id);
                        bean.setName(name);
                        bean.setAddress(address);
                        bean.setAge(age);
                        bean.setPhone(phone);
                        bean.setSalary(salary);
                        list.add(bean);
                    }while(cursor.moveToNext());
                }
            }
            return list;
        }
    }
}
