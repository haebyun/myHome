package com.studyproject.myhome.controller;

import com.studyproject.myhome.model.Member;
import com.studyproject.myhome.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "account/signup";
    }

    @PostMapping("/signup")
    public String signup(Member member) {
        memberService.save(member);
        return "redirect:/";
    }
}
