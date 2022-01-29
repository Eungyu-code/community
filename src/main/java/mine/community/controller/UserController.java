package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Member;
import mine.community.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final MemberService memberService;

    @PostMapping("/myinfo")
    public String info(@ModelAttribute Member member) {

        log.info("member = {}", member);

        return "members/memberInfo";
    }
}
