package mine.community.controller;

import lombok.RequiredArgsConstructor;
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
@Transactional
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final EntityManager em;


    @GetMapping("/add")
    public String memberForm(Model model) {

        model.addAttribute("memberForm", new MemberForm());
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String joinMember(@Validated @ModelAttribute("memberForm") MemberForm memberForm,
                             BindingResult bindingResult, HttpServletRequest request) {

        return "redirect:/";
    }
}
