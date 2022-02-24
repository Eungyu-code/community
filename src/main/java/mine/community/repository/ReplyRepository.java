package mine.community.repository;

import mine.community.domain.Board;
import mine.community.domain.Member;
import mine.community.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByBoard(Board board);
    List<Reply> findByMember(Member member);
}
