package com.gustavo.dao;

import com.gustavo.pojo.Member;

public interface MemberDao {
    public Member findByTelephone(String telephone) ;

    public void add(Member member);
}
