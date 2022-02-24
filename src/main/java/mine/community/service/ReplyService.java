package mine.community.service;

import lombok.RequiredArgsConstructor;
import mine.community.domain.Board;
import mine.community.domain.Member;
import mine.community.domain.Reply;
import mine.community.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public void save(Reply reply) {

        replyRepository.save(reply);
    }

    public List<Reply> findByMember(Member member) {

        return replyRepository.findByMember(member);
    }

    public List<Reply> findByBoard(Board board) {

        return replyRepository.findByBoard(board);
    }
}
