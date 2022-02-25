package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Board;
import mine.community.domain.Member;
import mine.community.domain.Reply;
import mine.community.form.BoardForm;
import mine.community.form.CustomUser;
import mine.community.form.ReplyForm;
import mine.community.service.BoardService;
import mine.community.service.MemberService;
import mine.community.service.ReplyService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final BoardService boardService;
    private final ReplyService replyService;

    @PostMapping("/boards/board/{boardId}")
    public String reply(@PathVariable("boardId") Long boardId, @Validated @ModelAttribute ReplyForm replyForm, BindingResult bindingResult, Model model,
                        @AuthenticationPrincipal CustomUser user) {


        Member boardMember = boardService.findOneById(boardId).getMember();
        Member userMember = user.getMember();

        if (!StringUtils.hasText(replyForm.getReplyText())) {
            bindingResult.rejectValue("replyText", "required", null, null);
        }

        if (bindingResult.hasErrors()) {

            if (boardMember.getId().equals(userMember.getId())) {

                return "boards/writerBoard";
            } else {

                return "boards/memberBoard";
            }
        }

        boardService.findOneById(boardId).addReply();

        Reply reply = new Reply();
        reply.addReply(replyForm.getReplyText(), userMember, boardService.findOneById(boardId));
        replyService.save(reply);

        BoardForm replyBoardForm = new BoardForm();
        addReplyForm(replyBoardForm, boardService.findOneById(boardId));
        model.addAttribute("replyBoardForm", replyBoardForm);

        return "redirect:/boards/board/" + boardId;
    }

    private void addReplyForm(BoardForm boardForm, Board board) {

        for (Reply reply : replyService.findByBoard(board)) {

            boardForm.setReplyList(reply);
            board.addReply();
        }
        boardForm.setReplyNumber(board.getReplyNumber());
    }
}
