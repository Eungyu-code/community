package mine.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Board;
import mine.community.domain.Member;
import mine.community.repository.BoardRepository;
import mine.community.repository.LikesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;

    public void save(Board board) {

        boardRepository.save(board);
        likesRepository.save(board.getLikes());
    }

    public Board findOneByTitle(String title) {

        return boardRepository.findByTitle(title).orElse(null);
    }

    public Board findOneById(Long id) {

        return boardRepository.findById(id).orElse(null);
    }

    public List<Board> findByMember(Member member) {

        return boardRepository.findByMember(member);
    }

    public List<Board> findAll() {

        return boardRepository.findAll();
    }

    public void liked(Member member, Board board) {


    }
}
