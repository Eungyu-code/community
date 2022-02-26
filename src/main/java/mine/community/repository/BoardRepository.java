package mine.community.repository;

import mine.community.domain.Board;
import mine.community.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);

    Optional<Board> findById(Long id);

    List<Board> findByMember(Member member);

    Page<Board> findBoardByMember(Member member, Pageable pageable);

    @Override
    Page<Board> findAll(Pageable pageable);

    @Override
    void deleteById(Long id);

    @Override
    List<Board> findAll();
}
