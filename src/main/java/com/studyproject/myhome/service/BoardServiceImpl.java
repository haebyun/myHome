package com.studyproject.myhome.service;

import com.studyproject.myhome.model.Board;
import com.studyproject.myhome.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    @Override
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    @Override
    public List<Board> findByTitleOrContent(String title, String content) {
        return boardRepository.findByTitleOrContent(title, content);
    }

    @Override
    public Board saveBoard(Board newBoard) {
        return boardRepository.save(newBoard);
    }

    @Override
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Override
    public Board replaceBoard(Board newBoard, Long id) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardRepository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return boardRepository.save(newBoard);
                });
    }

    @Override
    public void deleteBoardById(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Page<Board> findByTitleContainingOrContentContainingOrderByIdDesc(String title, String content,
                                                                             Pageable pageable) {
        return boardRepository.findByTitleContainingOrContentContainingOrderByIdDesc(title, content, pageable);
    }
}
