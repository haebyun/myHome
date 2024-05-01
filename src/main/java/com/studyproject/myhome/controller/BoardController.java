package com.studyproject.myhome.controller;

import com.studyproject.myhome.exception.PageValidationException;
import com.studyproject.myhome.model.Board;
import com.studyproject.myhome.service.BoardService;
import com.studyproject.myhome.validator.BoardValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
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
    private final BoardService boardService;
    private final BoardValidator boardValidator;
    private static final Integer pagingNumber = 5;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<Board> boards = boardService
                .findByTitleContainingOrContentContainingOrderByIdDesc(searchText, searchText, pageable);
        validatePageNumber(boards, page);
        int startPage = 1;
        int endPage = 1;
        if(boards.hasContent()) {
            startPage = pagingNumber*(page/pagingNumber)+1;
            endPage = getEndPage(boards, page);
        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pagingNumber", pagingNumber);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    private void validatePageNumber(Page<Board> boards, int pageNumber) {
        if (pageNumber < 0 || (pageNumber >= boards.getTotalPages() && boards.getTotalPages() > 0)) {
            throw new PageValidationException("Invalid page number.");
        }
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
            Board board = boardService.findBoardById(id);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) {
            return "board/form";
        }
        String userName = authentication.getName();
        boardService.saveBoard(userName, board);
        return "redirect:/board/list";
    }
}
