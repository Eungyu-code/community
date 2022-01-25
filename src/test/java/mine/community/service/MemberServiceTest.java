package mine.community.service;

import mine.community.domain.Address;
import mine.community.domain.Member;
import mine.community.exception.DuplicateMemberException;
import mine.community.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void clear() {
        em.clear();
    }

    @Test
    void 회원가입() {

        //given
        Member member = new Member();

        String nickname = "이은규";
        String password = "1234";
        String mail = "agyu";
        Address address = new Address("Korea", "Incheon", "Yongdusan-ro13th", "22724");

        //when
        member.create(mail, nickname, password, address);
        memberService.join(member);

        //then

        Long getId = member.getId();
        Member findMember = memberRepository.findById(getId);

        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void 메일중복() {

        //given
        Member memberA = new Member();
        Member memberB = new Member();

        String nickname = "이은규";
        String password = "1234";
        String mail = "agyu";
        Address address = new Address("Korea", "Incheon", "Yongdusan-ro13th", "22724");

        //when
        memberA.create(mail, nickname, password, address);
        memberB.create(mail, nickname, password, address);
        memberService.join(memberA);

        //then


    }

    @Test
    void 닉네임중복() {

    }

}