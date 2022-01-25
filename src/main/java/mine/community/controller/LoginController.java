package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@Transactional
@RequiredArgsConstructor
@RequestMapping("/members")
public class LoginController {

    private final EntityManager em;
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model) {

        model.addAttribute("loginForm", new LoginForm());

        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginform") LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request) {



        if (bindingResult.hasErrors()) {
            log.info("ERRORS = {}", bindingResult);
            return "members/loginForm";
        }

        return "redirect:/";
    }
}
