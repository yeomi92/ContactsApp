package com.hanbit.contactsapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hanbit.contactsapp.R;
import com.hanbit.contactsapp.dao.DetailQuery;
import com.hanbit.contactsapp.domain.MemberBean;
import com.hanbit.contactsapp.service.FindOneService;

import java.util.HashMap;
import java.util.Map;

public class MemberdetailActivity extends AppCompatActivity {
    TextView tvId,tvName,tvPhone,tvAge,tvAddress,tvSalary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberdetail);
        Intent intent=this.getIntent();
        Map<String,String> map;
        MemberBean member;
        final MemberDetail detail=new MemberDetail(MemberdetailActivity.this);
        String id=intent.getExtras().getString("id");
        FindOneService service=new FindOneService() {
            @Override
            public Object findOne(Map<?,?> params) {
                return detail.detail("select _id AS id,name,phone,age,address,salary from member where _id='"+params.get("id")+"';");
            }
        };
        map=new HashMap<>();
        map.put("id",id);
        member= (MemberBean) service.findOne(map);
        tvId= (TextView) findViewById(R.id.tvId);
        tvName= (TextView) findViewById(R.id.tvName);
        tvPhone= (TextView) findViewById(R.id.tvPhone);
        tvAge= (TextView) findViewById(R.id.tvAge);
        tvAddress= (TextView) findViewById(R.id.tvAddress);
        tvSalary= (TextView) findViewById(R.id.tvSalary);
        tvId.setText(member.getId());
        tvName.setText(member.getName());
        tvPhone.setText(member.getPhone());
        tvAge.setText(member.getAge());
        tvAddress.setText(member.getAddress());
        tvSalary.setText(member.getSalary());


        findViewById(R.id.btGoList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemberdetailActivity.this,MemberlistActivity.class));
            }
        });

        findViewById(R.id.btGoUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemberdetailActivity.this,MemberupdateActivity.class));
            }
        });
    }
    class MemberDetail extends DetailQuery {

        public MemberDetail(Context context) {
            super(context);
        }

        @Override
        public Object detail(String sql) {
            MemberBean bean=null;
            SQLiteDatabase db=super.getDatabase();
            Cursor cursor=db.rawQuery(sql,null);
            if(cursor!=null) {
                if (cursor.moveToNext()) {
                    bean = new MemberBean();
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String age = cursor.getString(cursor.getColumnIndex("age"));
                    String phone = cursor.getString(cursor.getColumnIndex("phone"));
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    String salary = cursor.getString(cursor.getColumnIndex("salary"));
                    bean.setId(id);
                    bean.setName(name);
                    bean.setAddress(address);
                    bean.setAge(age);
                    bean.setPhone(phone);
                    bean.setSalary(salary);
                }
            }
            return bean;
        }
    }
}
