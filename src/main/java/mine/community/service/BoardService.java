package mine.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.community.domain.Board;
import mine.community.domain.Member;
import mine.community.repository.BoardRepository;
import mine.community.repository.LikesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    }

    public List<Board> findByTitle(String title) {

        return boardRepository.findByTitle(title);
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

    public Page<Board> findAllPage(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    public Page<Board> findBoardByMember(Member member, Pageable pageable) {

        return boardRepository.findBoardByMember(member, pageable);
    }

    public void liked(Member member, Board board) {


    }

    public void delete(Long boardId) {

        boardRepository.deleteById(boardId);
    }
}
