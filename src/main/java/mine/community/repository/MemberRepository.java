package mine.community.repository;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    @Transactional
    public void join(Member member) {
        em.persist(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findById(Long id) {
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Member findByMail(String mail) {

        Member member;

        try {
            member = em.createQuery("select m from Member m where m.mail = :mail", Member.class)
                    .setParameter("mail", mail).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return member;
    }

    public Member findByName(String nickname) {

        Member member;

        try {
            member = em.createQuery("select m from Member m where m.nickname = :nickname", Member.class)
                    .setParameter("nickname", nickname).getSingleResult();
        } catch (NoResultException e) {

            return null;
        }

        return member;
    }
}
