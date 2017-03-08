package com.hanbit.contactsapp.service;

import com.hanbit.contactsapp.domain.MemberBean;
import java.util.List;

/**
 * Created by hb2008 on 2017-03-08.
 */

public interface MemberService {
    public void add(MemberBean bean);
    public MemberBean findOne(MemberBean bean);
    public List<MemberBean> findSemd(MemberBean bean);
    public List<MemberBean> findAll();
    public void delete(MemberBean bean);
    public void update(MemberBean bean);
}
