package com.studyproject.myhome.controller;

import com.studyproject.myhome.model.Board;
import com.studyproject.myhome.repository.BoardRepository;
import com.studyproject.myhome.validator.BoardValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardValidator boardValidator;
    private static final Integer pagingNumber = 5;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<Board> boards = boardRepository
                .findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = pagingNumber*(page/pagingNumber)+1;
        int endPage = getEndPage(boards, page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pagingNumber", pagingNumber);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    private int getEndPage(Page<Board> boards, int page) {
        int cmpPagingNumber = pagingNumber*(page/pagingNumber)+pagingNumber;
        return Math.min(boards.getTotalPages(), cmpPagingNumber);
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if(id == null){
            model.addAttribute("board", new Board());
        } else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
