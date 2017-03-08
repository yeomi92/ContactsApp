package com.hanbit.contactsapp.dao;

import com.hanbit.contactsapp.domain.MemberBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hb2008 on 2017-03-08.
 */

public class MemberDao {
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
