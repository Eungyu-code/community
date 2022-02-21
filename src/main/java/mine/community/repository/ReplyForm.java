package mine.community.repository;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Reply;
import mine.community.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ReplyForm {

    private final EntityManager em;

    public void save(Reply comment) {
        em.persist(comment);
    }

    public void delete(Reply comment) {
        em.remove(comment);
    }

    public List<Reply> findAll() {
        return em.createQuery("select c from Reply c", Reply.class)
                .getResultList();
    }

    public List<Reply> findAllByMember(Member member) {
        return em.createQuery("select c from Reply c where c.member = :member", Reply.class)
                .getResultList();
    }

}
