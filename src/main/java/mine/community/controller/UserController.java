package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Member;
import mine.community.form.CustomUser;
import mine.community.form.MemberForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {


    @GetMapping("/members/info")
    public String info(@AuthenticationPrincipal CustomUser user, Model model) {

        Member member = user.getMember();

        MemberForm memberForm = new MemberForm();
        memberForm.setId(member.getId());
        memberForm.setNickname(member.getNickname());
        memberForm.setMail(member.getMail());

        model.addAttribute("memberForm", memberForm);

        return "members/memberInfo";
    }
}
