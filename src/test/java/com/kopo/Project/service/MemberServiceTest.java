package com.kopo.Project.service;

import com.kopo.Project.Domain.Member;
import com.kopo.Project.Repository.Member.MemberRepoImpl;
import com.kopo.Project.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService service;

    @Autowired
    MemberRepoImpl repository;

    @Test
    public void join() throws Exception {
        Member member = new Member();
        member.setMember_code(System.currentTimeMillis());
        Long saveId = service.join(member);
        Member findMember = repository.findByMemberCode(saveId).get();
        assertEquals(member.getMember_code(), findMember.getMember_code());
        System.out.println(member.getMember_code());
        System.out.println(findMember.getMember_code());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        member1.setMember_code(System.currentTimeMillis());
        service.join(member1);
        Member member2 = new Member();
        member2.setMember_code(System.currentTimeMillis());
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> service.join(member1));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        System.out.println(member1.getMember_code());
        System.out.println(member2.getMember_code());
    }
}