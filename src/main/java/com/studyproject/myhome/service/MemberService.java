package com.studyproject.myhome.service;

import com.studyproject.myhome.model.Member;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Member save(Member member);
}
