package com.studyproject.myhome.service;

import com.studyproject.myhome.model.Board;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {
    List<Board> findBoards();

    List<Board> findByTitleOrContent(String title, String content);

    Board saveBoard(Board newBoard);

    Board findBoardById(Long id);

    Board replaceBoard(Board newBoard, Long id);

    void deleteBoardById(Long id);

    Page<Board> findByTitleContainingOrContentContainingOrderByIdDesc(String searchText, String searchText1, Pageable pageable);
}
