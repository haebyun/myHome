package com.studyproject.myhome.repository;

import com.studyproject.myhome.model.Board;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleOrContent(String title, String content);
    Page<Board> findByTitleContainingOrContentContainingOrderByIdDesc(String title, String content, Pageable pageable);
}
