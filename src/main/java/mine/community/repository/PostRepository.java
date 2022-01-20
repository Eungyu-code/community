package mine.community.repository;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Member;
import mine.community.domain.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public List<Post> findAllByMember(Member member) {
        return em.createQuery("select p from Post p where p.member = :member", Post.class)
                .getResultList();
    }
}
