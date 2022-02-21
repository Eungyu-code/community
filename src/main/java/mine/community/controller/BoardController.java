package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Board;
import mine.community.domain.Member;
import mine.community.form.BoardForm;
import mine.community.form.CustomUser;
import mine.community.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards/write")
    public String writeForm(Model model) {

        model.addAttribute("boardForm", new BoardForm());
        return "boards/writeForm";
    }

    @PostMapping("/boards/write")
    public String write(@Validated @ModelAttribute BoardForm boardForm, BindingResult bindingResult, @AuthenticationPrincipal CustomUser user) throws UnsupportedEncodingException {

        if (!StringUtils.hasText(boardForm.getTitle())) {
            bindingResult.rejectValue("title", "required", null, null);
        }

        if (!StringUtils.hasText(boardForm.getBoardText())) {
            bindingResult.rejectValue("boardText", "required", null, null);
        }

        if (bindingResult.hasErrors()) {
            log.info("board error {}", bindingResult.hasErrors());
            return "boards/writeForm";
        }

        log.info("@PostMapping write, user.getMember() = {}", user.getMember());

        Board board = new Board();
        board.save(user.getMember(), boardForm.getTitle(), boardForm.getBoardText());
        boardService.save(board);

        log.info("@PostMapping write, board.getMember() = {}", board.getMember());

        String encodedParam = URLEncoder.encode(board.getTitle(), "UTF-8");


        return "redirect:/boards/board/" + encodedParam;
    }

    @GetMapping("/boards/board/{boardTitle}")
    public String board(@PathVariable("boardTitle") String boardTitle, Model model, @AuthenticationPrincipal CustomUser user) {

        Board board = boardService.findOneByTitle(boardTitle);

        BoardForm boardForm = new BoardForm();
        boardForm.setId(board.getId());
        boardForm.setMember(board.getMember());
        boardForm.setTitle(board.getTitle());
        boardForm.setLikes(board.getLikes().getLikes());
        boardForm.setBoardText(board.getBoardText());
        boardForm.setWriteDate(board.getWriteDate());

        model.addAttribute("boardForm", boardForm);

        Member boardMember = board.getMember();
        Member userMember = user.getMember();

        if (boardMember.getId().equals(userMember.getId())) {

            return "boards/writerBoard";
        }

        log.info("memberBoard");

        return "boards/memberBoard";
    }

    @GetMapping("/boards/board/{boardTitle}/edit")
    public String editForm(@PathVariable("boardTitle") String boardTitle, Model model, @AuthenticationPrincipal CustomUser user) {


        Board board = boardService.findOneByTitle(boardTitle);
        BoardForm boardForm = new BoardForm();
        boardForm.setId(board.getId());
        boardForm.setTitle(board.getTitle());
        boardForm.setLikes(board.getLikes().getLikes());
        boardForm.setMember(board.getMember());
        boardForm.setBoardText(board.getBoardText());
        boardForm.setWriteDate(board.getWriteDate());

        Member boardMember = board.getMember();
        Member userMember = user.getMember();

        if (!boardMember.getId().equals(userMember.getId())) {
            return "redirect:/boards/board/" + boardTitle;
        }

        model.addAttribute("boardForm", boardForm);

        return "boards/updateBoard";
    }

    @PostMapping("/boards/board/{boardTitle}/edit")
    public String edit(@Validated @ModelAttribute BoardForm boardForm, BindingResult bindingResult) throws UnsupportedEncodingException {

        if (!StringUtils.hasText(boardForm.getBoardText())) {
            bindingResult.rejectValue("boardText", "required", null, null);
        }

        if (bindingResult.hasErrors()) {
            return "boards/updateBoard";
        }

        Board board = boardService.findOneById(boardForm.getId());

        board.change(boardForm.getTitle(), boardForm.getBoardText());
        boardService.save(board);

        String encodedParam = URLEncoder.encode(board.getTitle(), "UTF-8");

        return "redirect:/boards/board/" + encodedParam;
    }

    @PostMapping("/boards/board/{boardTitle}")
    public String liked(@ModelAttribute BoardForm boardForm, @AuthenticationPrincipal CustomUser user) {

        Board board = boardService.findOneById(boardForm.getId());

        board.liked();

        return "redirect:/boards/board/" + board.getTitle();
    }
}
