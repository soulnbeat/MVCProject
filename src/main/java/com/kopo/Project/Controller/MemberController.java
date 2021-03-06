package com.kopo.Project.Controller;

import com.kopo.Project.Domain.Member;
import com.kopo.Project.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }


    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setMember_code(System.currentTimeMillis());
        member.setE_mail(form.getE_mail());
        member.setPassword(form.getPassword());
        member.setPhone_number(form.getPhone_number());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members/tmp")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/member";
    }

//    @PostMapping(value = "/members/tmp")
//    public String findMembers(Model model) {
//        List<Member> members = memberService.findMembers();
//        model.addAttribute("members", members);
//        return "members/member";
//    }

}