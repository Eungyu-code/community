package mine.community.service;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Member;
import mine.community.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean validateMember(Member member) {

        if (memberRepository.findByMail(member.getMail()) != null) return false;
        if (memberRepository.findByName(member.getNickname()) != null) return false;

        return true;
    }

    public void join(Member member) {

        memberRepository.join(member);
    }

    public Member login(String nickname, String password) {

        return memberRepository.findByName(nickname).filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

}
