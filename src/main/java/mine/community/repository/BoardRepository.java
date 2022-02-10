package mine.community.repository;

import mine.community.domain.Board;
import mine.community.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByTitle(String title);

    List<Board> findByMember(Member member);

    @Override
    List<Board> findAll();
}
