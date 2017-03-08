package com.hanbit.contactsapp.serviceImpl;

import com.hanbit.contactsapp.dao.MemberDao;
import com.hanbit.contactsapp.domain.MemberBean;
import com.hanbit.contactsapp.service.MemberService;

import java.util.List;

/**
 * Created by hb2008 on 2017-03-08.
 */

public class MemberServiceImpl implements MemberService {
    MemberDao dao=new MemberDao();
    @Override
    public void add(MemberBean bean) {
        dao.create(bean);
    }

    @Override
    public MemberBean findOne(MemberBean bean) {
        return dao.selectOne(bean);
    }

    @Override
    public List<MemberBean> findSemd(MemberBean bean) {
        return dao.selectSome(bean);
    }

    @Override
    public List<MemberBean> findAll() {
        return dao.selectAll();
    }

    @Override
    public void delete(MemberBean bean) {
        dao.delete(bean);
    }

    @Override
    public void update(MemberBean bean) {
        dao.update(bean);
    }
}
