package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Board;
import mine.community.domain.Member;
import mine.community.domain.Reply;
import mine.community.form.BoardForm;
import mine.community.form.CustomUser;
import mine.community.form.MemberForm;
import mine.community.form.ReplyForm;
import mine.community.service.BoardService;
import mine.community.service.MemberService;
import mine.community.service.ReplyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final ReplyService replyService;

    @GetMapping("/boards/write")
    public String writeForm(Model model) {

        model.addAttribute("boardForm", new BoardForm());
        return "boards/writeForm";
    }

    @PostMapping("/boards/write")
    public String write(@Validated @ModelAttribute BoardForm boardForm, BindingResult bindingResult, @AuthenticationPrincipal CustomUser user) {

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


        return "redirect:/boards/board/" + board.getId();
    }

    @GetMapping("/boards/board/{boardId}")
    public String board(@PathVariable("boardId") Long boardId, Model model, @AuthenticationPrincipal CustomUser user) {

        Board board = boardService.findOneById(boardId);

        log.info("board.getReplyNumber = {}", board.getReplyNumber());

        BoardForm boardForm = new BoardForm();
        boardForm.setId(board.getId());
        boardForm.setMember(board.getMember());
        boardForm.setTitle(board.getTitle());
        boardForm.setBoardText(board.getBoardText());
        boardForm.setWriteDate(board.getWriteDate());

        BoardForm replyBoardForm = new BoardForm();
        addReplyForm(replyBoardForm, board);

        model.addAttribute("boardForm", boardForm);
        model.addAttribute("replyForm", new ReplyForm());
        model.addAttribute("replyBoardForm", replyBoardForm);

        Member boardMember = board.getMember();
        Member userMember = user.getMember();

        if (boardMember.getId().equals(userMember.getId())) {

            return "boards/writerBoard";
        }

        log.info("memberBoard");

        return "boards/memberBoard";
    }

    @GetMapping("/boards/board/{boardId}/edit")
    public String editForm(@PathVariable("boardId") Long boardId, Model model, @AuthenticationPrincipal CustomUser user) {

        Board board = boardService.findOneById(boardId);
        BoardForm boardForm = new BoardForm();
        boardForm.setId(board.getId());
        boardForm.setTitle(board.getTitle());
        boardForm.setMember(board.getMember());
        boardForm.setBoardText(board.getBoardText());
        boardForm.setWriteDate(board.getWriteDate());

        Member boardMember = board.getMember();
        Member userMember = user.getMember();

        if (!boardMember.getId().equals(userMember.getId())) {
            return "redirect:/boards/board/" + boardId;
        }

        model.addAttribute("boardForm", boardForm);

        return "boards/updateBoard";
    }

    @PostMapping("/boards/board/{boardId}/edit")
    public String edit(@PathVariable("boardId") Long boardId, @Validated @ModelAttribute BoardForm boardForm, BindingResult bindingResult) {

        if (!StringUtils.hasText(boardForm.getBoardText())) {
            bindingResult.rejectValue("boardText", "required", null, null);
        }

        if (bindingResult.hasErrors()) {
            return "boards/updateBoard";
        }

        Board board = boardService.findOneById(boardForm.getId());

        board.change(boardForm.getTitle(), boardForm.getBoardText());
        boardService.save(board);


        return "redirect:/boards/board/" + board.getId();
    }

    @GetMapping("/boards")
    public String boardList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        model.addAttribute("boardList", boardService.findAllPage(pageable));

        return "boards/boardList";
    }

    @GetMapping("/members/boards/{nickname}")
    public String memberBoard(@PageableDefault(page = 1, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("nickname") String nickname, Model model) {

        Member member = memberService.findByNickname(nickname);

        Page<Board> boardList = boardService.findBoardByMember(member, pageable);

        MemberForm memberForm = new MemberForm();
        memberForm.setId(member.getId());
        memberForm.setNickname(member.getNickname());
        memberForm.setMail(member.getMail());

        model.addAttribute("memberForm", memberForm);
        model.addAttribute("boardList", boardList);

        return "boards/boardList";
    }

    @GetMapping("/boards/board/{boardId}/delte")
    public String delete(@PathVariable("boardId") Long boardId) {

        boardService.delete(boardId);

        return "redirect:/boards";
    }

    private void addReplyForm(BoardForm boardForm, Board board) {

        for (Reply reply : replyService.findByBoard(board)) {

            boardForm.setReplyList(reply);
        }
        boardForm.setReplyNumber(board.getReplyNumber());
    }
}
