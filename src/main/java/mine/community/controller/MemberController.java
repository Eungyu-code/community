package mine.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Address;
import mine.community.domain.Member;
import mine.community.form.MemberForm;
import mine.community.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@Slf4j
@Transactional
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/add")
    public String memberForm(@ModelAttribute("memberForm") MemberForm memberForm) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String joinMember(@Validated @ModelAttribute("memberForm") MemberForm memberForm,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (!StringUtils.hasText(memberForm.getNickname())) {
            bindingResult.rejectValue("nickname", "required", null, null);
        }

        if (!StringUtils.hasText(memberForm.getMail())) {
            bindingResult.rejectValue("mail", "required", null, null);
        }

        if (!StringUtils.hasText(memberForm.getPassword())) {
            bindingResult.rejectValue("password", "required", null, null);
        }


        if (memberService.duplicateMail(memberForm.getMail()) != null) {

            bindingResult.rejectValue("mail", "duplicate", null, null);
        }

        if (memberService.duplicateName(memberForm.getNickname()) != null) {

            bindingResult.rejectValue("nickname", "duplicate", null, null);
        }


        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/addMemberForm";
        }

        Member member = new Member();
        Address address = new Address(memberForm.getCountry(), memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        member.create(memberForm.getMail(), memberForm.getNickname(), memberForm.getPassword(), address);
        memberService.join(member);

        return "redirect:/";
    }
}
