package com.studyproject.myhome.config;

import com.studyproject.myhome.repository.BoardRepository;
import com.studyproject.myhome.repository.MemberRepository;
import com.studyproject.myhome.service.BoardService;
import com.studyproject.myhome.service.BoardServiceImpl;
import com.studyproject.myhome.service.MemberService;
import com.studyproject.myhome.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringConfig {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SpringConfig(BoardRepository boardRepository, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public BoardService boardService() {
        return new BoardServiceImpl(boardRepository, memberRepository);
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository, passwordEncoder);
    }
}
