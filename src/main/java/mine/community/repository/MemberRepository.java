package mine.community.repository;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

    public Optional<Member> findByMail(String mail) {
        return findAll().stream()
                .filter(m -> m.getMail().equals(mail))
                .findFirst();
    }

    public Optional<Member> findByName(String nickname) {

        return findAll().stream()
                .filter(m -> m.getNickname().equals(nickname))
                .findFirst();
    }
}
