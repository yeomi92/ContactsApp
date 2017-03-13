package com.hanbit.contactsapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
        ListView mList= (ListView) findViewById(R.id.mList);
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
        class MemberAdapter extends BaseAdapter {
            ArrayList<?>list;
            LayoutInflater inflater;
            private int[] photos={R.drawable.cupcake,R.drawable.donut,R.drawable.eclair,R.drawable.froyo,R.drawable.gingerbread,R.drawable.honeycomb,R.drawable.icecream,R.drawable.jellybean,R.drawable.kitkat,R.drawable.lollipop};
            public MemberAdapter(ArrayList<?> list, Context context) {
                this.list = list;
                this.inflater=LayoutInflater.from(context);//inflater에 바로 context값 전달
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int i) {
                return list.get(i);
            }

            @Override
            public long getItemId(int i) { //index
                return i;
            }

            //외부에서 받는 View를 변환해서 반환한다.
            @Override
            public View getView(int i, View v, ViewGroup g) {
                ViewHoler holder;
                if(v==null){
                    v=inflater.inflate(R.layout.member_item,null);
                    holder=new ViewHoler();
                    holder.profileImg= (ImageView) v.findViewById(R.id.profileImg);
                    holder.tvName= (TextView) v.findViewById(R.id.tvName);
                    holder.tvPhone= (TextView) v.findViewById(R.id.tvPhone);
                    v.setTag(holder);
                }else{
                    holder= (ViewHoler) v.getTag();
                }
                holder.profileImg.setImageResource(photos[i]);
                holder.tvName.setText(((MemberBean)list.get(i)).getName());
                holder.tvPhone.setText(((MemberBean)list.get(i)).getPhone());
                return v;
            }

        }
    }
    static class ViewHoler{ //getter, setter 안쓰기위해서 static 사용
        ImageView profileImg;
        TextView tvName,tvPhone;
    }
}
