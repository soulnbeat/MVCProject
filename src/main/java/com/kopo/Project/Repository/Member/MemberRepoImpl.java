package com.kopo.Project.Repository.Member;

import com.kopo.Project.Domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class MemberRepoImpl implements MemberRepo {
    private final EntityManager em;

    public MemberRepoImpl(EntityManager em) {
        this.em = em;
    }

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Optional<Member> findByMemberCode(Long member_code) {
        Member member = em.find(Member.class, member_code);
        return Optional.ofNullable(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findByMail(String e_mail) {
        List<Member> result = em.createQuery("select m from Member m where m.e_mail = :e_mail", Member.class)
                .setParameter("e_mail", e_mail)
                .getResultList();
        return result.stream().findAny();
    }
}