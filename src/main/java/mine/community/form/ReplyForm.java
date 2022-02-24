package mine.community.form;

import lombok.Getter;
import lombok.Setter;
import mine.community.domain.Board;
import mine.community.domain.Member;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyForm {

    private Long id;
    private Member member;
    private Board board;
    private String replyText;
    private LocalDateTime writeTime;
}
