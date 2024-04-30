package com.studyproject.myhome.config;

import com.studyproject.myhome.repository.BoardRepository;
import com.studyproject.myhome.repository.MemberRepository;
import com.studyproject.myhome.service.BoardService;
import com.studyproject.myhome.service.BoardServiceImpl;
import com.studyproject.myhome.service.MemberService;
import com.studyproject.myhome.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public SpringConfig(BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Bean
    public BoardService boardService() {
        return new BoardServiceImpl(boardRepository);
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository);
    }
}
