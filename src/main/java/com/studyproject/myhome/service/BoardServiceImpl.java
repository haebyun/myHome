package com.studyproject.myhome.service;

import com.studyproject.myhome.model.Board;
import com.studyproject.myhome.model.Member;
import com.studyproject.myhome.repository.BoardRepository;
import com.studyproject.myhome.repository.MemberRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public BoardServiceImpl(BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    @Override
    public List<Board> findByTitleOrContent(String title, String content) {
        return boardRepository.findByTitleOrContent(title, content);
    }

    @Override
    @Transactional
    public Board saveBoard(String username, Board newBoard) {
        Member member = memberRepository.findByUsername(username);
        newBoard.setMember(member);
        return boardRepository.save(newBoard);
    }

    @Override
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteBoardById(Long id, String username) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
        if(!board.getMember().getUsername().equals(username)) {
            throw new IllegalStateException("Cannot delete other user's post");
        }
        boardRepository.deleteById(id);
    }

    @Override
    public Page<Board> findByTitleContainingOrContentContainingOrderByIdDesc(String title, String content, Pageable pageable) {
        return boardRepository.findByTitleContainingOrContentContainingOrderByIdDesc(title, content, pageable);
    }
}