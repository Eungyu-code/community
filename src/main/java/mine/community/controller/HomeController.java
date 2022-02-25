package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Board;
import mine.community.form.BoardForm;
import mine.community.service.BoardService;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(Model model) {

        List<Board> boards = boardService.findAll();
        Collections.reverse(boards);

        List<BoardForm> boardFormList = new ArrayList<>();


        for (Board board : boards) {

            BoardForm boardForm = new BoardForm();
            boardForm.setId(board.getId());
            boardForm.setTitle(board.getTitle());
            boardForm.setMember(board.getMember());
            boardForm.setBoardText(board.getBoardText());
            boardForm.setWriteDate(board.getWriteDate());
            boardForm.setReplyNumber(board.getReplyNumber());

            boardFormList.add(boardForm);

            if (boardFormList.size() >= 3) break;
        }

        model.addAttribute("boardFormList", boardFormList);


        return "home";
    }
}
