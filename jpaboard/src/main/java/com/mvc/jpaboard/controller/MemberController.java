package com.mvc.jpaboard.controller;

import com.mvc.jpaboard.domain.Member;
import com.mvc.jpaboard.persistence.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member/")
public class MemberController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository repo;

    @GetMapping("/join")
    public void join() {
    }

    @PostMapping("/join")
    public String joinPost(@ModelAttribute Member member){
        log.info("Member : "+ member);
        String encryptPw = passwordEncoder.encode(member.getUpw());
        log.info("encryptPw : "+encryptPw);
        member.setUpw(encryptPw);
        repo.save(member);

        return "/member/joinResult";
    }
}
