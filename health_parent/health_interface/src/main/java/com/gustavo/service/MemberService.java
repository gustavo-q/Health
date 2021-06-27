package com.gustavo.service;

import com.gustavo.pojo.Member;

public interface MemberService {
    public Member findByTelephone(String telephone) ;

    public void add(Member member);
}
