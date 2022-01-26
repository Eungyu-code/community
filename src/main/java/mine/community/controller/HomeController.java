package mine.community.controller;

import lombok.RequiredArgsConstructor;
import mine.community.authentication.Login;
import mine.community.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(@Login Member loginMember, Model model) {

        if (loginMember == null) {
            return "homes/home";
        }

        model.addAttribute("member", loginMember);
        return "homes/loginHome";
    }
}
