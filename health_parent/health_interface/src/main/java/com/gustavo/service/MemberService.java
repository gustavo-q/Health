package com.gustavo.service;

import com.gustavo.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    public Member findByTelephone(String telephone) ;

    public void add(Member member);

    public List<Integer> findMemberCountByMonths(List<String> months);

}
