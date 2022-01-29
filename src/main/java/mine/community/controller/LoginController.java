package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Member;
import mine.community.service.LoginService;
import mine.community.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {

        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (!StringUtils.hasText(loginForm.getMail())) {
            bindingResult.rejectValue("mail", "required", null, null);
        }

        if (!StringUtils.hasText(loginForm.getPassword())) {
            bindingResult.rejectValue("password", "required", null, null);
        }
        
        Member loginMember = loginService.login(loginForm.getMail(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("wrong", null, null);
        }

        if (bindingResult.hasErrors()) {

            return "members/loginForm";
        }

        log.info("loginMember in loginController = {}", loginMember);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
            log.info("session = {}", session);
        }

        return "redirect:/";
    }

}
