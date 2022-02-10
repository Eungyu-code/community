package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Board;
import mine.community.form.BoardForm;
import mine.community.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String write(@Validated @ModelAttribute BoardForm boardForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

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

        Board board = new Board();
        board.save(boardForm.getTitle(), boardForm.getBoardText());
        boardService.save(board);

        return "redirect:/boards/board" + board.getTitle();
    }

    @GetMapping("/boards/board/{boardTitle}")
    public String board(@PathVariable("boardTitle") String boardTitle, Model model) {


        Board board = boardService.findOneByTitle(boardTitle);
        model.addAttribute("board", board);

        return "boards/board";
    }


}
