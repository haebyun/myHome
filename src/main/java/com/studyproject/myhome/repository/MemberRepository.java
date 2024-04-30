package com.studyproject.myhome.repository;

import com.studyproject.myhome.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
