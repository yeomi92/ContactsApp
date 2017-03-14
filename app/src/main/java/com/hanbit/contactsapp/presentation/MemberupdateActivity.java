package com.hanbit.contactsapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hanbit.contactsapp.R;
import com.hanbit.contactsapp.dao.DetailQuery;
import com.hanbit.contactsapp.dao.UpdateQuery;
import com.hanbit.contactsapp.domain.MemberBean;
import com.hanbit.contactsapp.service.FindOneService;
import com.hanbit.contactsapp.service.UpdateService;

import java.util.HashMap;
import java.util.Map;

public class MemberupdateActivity extends AppCompatActivity {
    TextView tvId,tvName,tvAge;
    EditText etPhone,etAddress,etSalary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberupdate);
        Intent intent=this.getIntent();
        final Map<String,String>map=new HashMap<>();
        final String id=intent.getExtras().getString("id");
        final MemberBean member;
        final MemberDetail detail=new MemberDetail(MemberupdateActivity.this);
        final MemberUpdate update=new MemberUpdate(MemberupdateActivity.this);
        FindOneService service=new FindOneService() {
            @Override
            public Object findOne(Map<?,?> params) {
                return detail.detail("select _id AS id,name,phone,age,address,salary from member where _id='"+params.get("id")+"';");
            }
        };
        map.put("id",id);
        member= (MemberBean) service.findOne(map);
        final UpdateService updateService=new UpdateService() {
            @Override
            public void update(Map<?, ?> map) {
                String phone= map.get("phone").equals("")?member.getPhone():(String) map.get("phone");
                String address= map.get("address").equals("")?member.getAddress():(String) map.get("address");
                String salary= map.get("salary").equals("")?member.getSalary():(String) map.get("salary");
                String sql=String.format("update member set phone='%s',address='%s',salary='%s' where _id='%s'",phone,address,salary,map.get("id"));
                update.update(sql);
            }
        };
        tvId= (TextView) findViewById(R.id.tvId);
        tvName= (TextView) findViewById(R.id.tvName);
        etPhone= (EditText) findViewById(R.id.etPhone);
        tvAge= (TextView) findViewById(R.id.tvAge);
        etAddress= (EditText) findViewById(R.id.etAddress);
        etSalary= (EditText) findViewById(R.id.etSalary);
        tvId.setText(member.getId());
        tvName.setText(member.getName());
        etPhone.setHint(member.getPhone());
        tvAge.setText(member.getAge());
        etAddress.setHint(member.getAddress());
        etSalary.setHint(member.getSalary());

        findViewById(R.id.btConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("phone",etPhone.getText().toString());
                map.put("address",etAddress.getText().toString());
                map.put("salary",etSalary.getText().toString());
                updateService.update(map);
                Intent intent=new Intent(MemberupdateActivity.this,MemberdetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemberupdateActivity.this,MemberlistActivity.class));
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
    class MemberUpdate extends UpdateQuery {
        public MemberUpdate(Context context) {
            super(context);
        }
        @Override
        public void update(String sql) {
            super.getDatabase().execSQL(sql);
        }
    }
}
