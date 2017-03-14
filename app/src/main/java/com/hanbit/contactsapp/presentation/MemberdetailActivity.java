package com.hanbit.contactsapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hanbit.contactsapp.R;
import com.hanbit.contactsapp.dao.DetailQuery;
import com.hanbit.contactsapp.domain.MemberBean;
import com.hanbit.contactsapp.service.FindOneService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberdetailActivity extends AppCompatActivity {
    TextView tvId,tvName,tvPhone,tvAge,tvAddress,tvSalary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context=MemberdetailActivity.this;
        setContentView(R.layout.activity_memberdetail);
        Intent intent=this.getIntent();
        Map<String,String> map;
        List<Button>buttons=new ArrayList<>();
        buttons.add((Button) findViewById(R.id.btDial));
        buttons.add((Button) findViewById(R.id.btCall));
        buttons.add((Button) findViewById(R.id.btGoList));
        buttons.add((Button) findViewById(R.id.btGoUpdate));
        final MemberBean member;
        final MemberDetail detail=new MemberDetail(context);
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
        map.put("phoneNum",member.getPhone());
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
        new ButtonObserver(context,buttons,map).onClick(findViewById(android.R.id.content));
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
    class ButtonObserver implements View.OnClickListener{

        Context context;
        Map<String,String>map;
        List<Button>buttons;
        public ButtonObserver(Context context,List<Button>buttons,Map<?,?>map) {
            this.context = context;
            this.buttons=buttons;
            this.map= (Map<String, String>) map;
            for(Button b:buttons){
                b.setOnClickListener(this);
            }
        }
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.btDial:
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + map.get("phoneNum")));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.btCall:
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + map.get("phoneNum")));
                    if (ActivityCompat.checkSelfPermission(MemberdetailActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MemberdetailActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 2);
                    }
                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case R.id.btGoList:
                    startActivity(new Intent(MemberdetailActivity.this, MemberlistActivity.class));
                    break;
                case R.id.btGoUpdate:
                    intent=new Intent(MemberdetailActivity.this, MemberupdateActivity.class);
                    intent.putExtra("id",map.get("id"));
                    startActivity(intent);
                    break;

            }
        }
    }
}
