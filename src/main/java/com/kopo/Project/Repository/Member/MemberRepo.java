package com.kopo.Project.Repository.Member;

import com.kopo.Project.Domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepo {
    // C
    Member save(Member member);
    // R
    Optional<Member> findByMemberCode(Long member_code);
    Optional<Member> findByMail(String e_mail);
    List<Member> findAll();
    Member findOne(String e_mail);
}