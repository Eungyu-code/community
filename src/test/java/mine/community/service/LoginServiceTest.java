package mine.community.service;

import mine.community.domain.Member;
import mine.community.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class LoginServiceTest {


    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LoginService loginService;

    @BeforeEach
    void clear() {
        em.clear();
    }

    @Test
    void login() {

        //given
        Member member = new Member();
        String nickname = "이은규";
        String mail = "agyu";
        String password = "1234";

        //when
        member.create(mail, nickname, password, null);
        memberRepository.join(member);

        Member findMember = memberRepository.findByMail(mail);

        //then
        assertThat(loginService.login(mail, password)).isEqualTo(findMember);
    }

    @Test
    void noResultException() {

        assertThat(loginService.login("mail", "password")).isEqualTo(null);
    }
}