package mine.community.service;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Member;
import mine.community.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public void join(Member member) {


        memberRepository.save(member);
    }

    public Member duplicateMail(String mail) {

        return memberRepository.findByMail(mail).orElse(null);
    }

    public Member duplicateName(String nickname) {

        return memberRepository.findByNickname(nickname).orElse(null);
    }

}
