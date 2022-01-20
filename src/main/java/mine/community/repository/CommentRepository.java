package mine.community.repository;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Comment;
import mine.community.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }

    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    public List<Comment> findAllByMember(Member member) {
        return em.createQuery("select c from Comment c where c.member = :member", Comment.class)
                .getResultList();
    }

}
