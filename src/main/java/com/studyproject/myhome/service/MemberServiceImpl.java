package com.studyproject.myhome.service;

import com.studyproject.myhome.model.Member;
import com.studyproject.myhome.model.Role;
import com.studyproject.myhome.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Member save(Member member) {
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        member.setEnabled(true);
        Role role = new Role();
        role.setId(1L);
        member.getRoles().add(role);
        return memberRepository.save(member);
    }
}
