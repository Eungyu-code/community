package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/members/login")
    public String loginForm() {

        return "members/loginForm";
    }

    @GetMapping("/members/login/error")
    public String loginerrors(Model model) {

        model.addAttribute("loginError", true);
    }
}
