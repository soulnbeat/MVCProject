package com.kopo.Project.repository;

import com.kopo.Project.Domain.Member;
import com.kopo.Project.Repository.Member.MemberRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepoImplTest {

    @Autowired
    MemberRepoImpl repository;

    @Test
    public void save(){
        Member member = new Member();

        member.setMember_code(System.currentTimeMillis());
        member.setE_mail("test@test.com");
        member.setPassword("password");
        member.setPhone_number(92855922);
        //when : member 저장하면 sava메소드에서 sequence로 id를 자동 저장한다
        repository.save(member);
        //then : findByIda메소드로 member의 id를 찾아와서 result에 저장
        Member result = repository.findByMemberCode(member.getMember_code()).get();
        // member와 result가 같은지 비교
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByMemberCode() {
        Member member = new Member();
        long tmp = System.currentTimeMillis();
        member.setMember_code(tmp);
        member.setE_mail("test@test.com");
        member.setPassword("password");
        member.setPhone_number(92855922);
        repository.save(member);
        Member member1 = new Member();
        member1.setMember_code(System.currentTimeMillis());
        member1.setE_mail("test2@test2.com");
        member1.setPassword("pw");
        member1.setPhone_number(12345678);
        repository.save(member1);

        Member result = repository.findByMemberCode(tmp).get();
        assertThat(result.getMember_code()).isEqualTo(member.getMember_code());
    }

    @Test
    public void findByMail() {
        Member member = new Member();
        member.setMember_code(System.currentTimeMillis());
        member.setE_mail("test@test.com");
        member.setPassword("password");
        member.setPhone_number(92855922);
        repository.save(member);
        Member member1 = new Member();
        member1.setMember_code(System.currentTimeMillis());
        member1.setE_mail("test2@test2.com");
        member1.setPassword("pw");
        member1.setPhone_number(12345678);
        repository.save(member1);

        Member result = repository.findByMail("test@test.com").get();
        assertThat(result.getE_mail()).isEqualTo(member.getE_mail());
    }

    @Test
    public void findAll() {
        Member member = new Member();
        member.setMember_code(System.currentTimeMillis());
        member.setE_mail("test@test.com");
        member.setPassword("password");
        member.setPhone_number(92855922);
        repository.save(member);
        Member member1 = new Member();
        member1.setMember_code(System.currentTimeMillis());
        member1.setE_mail("test2@test2.com");
        member1.setPassword("pw");
        member1.setPhone_number(12345678);
        repository.save(member1);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}