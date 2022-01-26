package mine.community.service;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Member;
import mine.community.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String mail, String password) {

        Member loginMember = memberRepository.findByMail(mail);

        if (loginMember == null || password.equals(loginMember.getPassword())) {
            return null;
        }

        return loginMember;
    }

}
