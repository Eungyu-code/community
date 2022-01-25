package mine.community.service;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Member;
import mine.community.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    private Member login(String mail, String password) {

        return memberRepository.findByMail(mail).filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
