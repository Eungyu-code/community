package mine.community.form;

import lombok.Getter;
import lombok.Setter;
import mine.community.domain.Member;
import mine.community.domain.Reply;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardForm {

    private Long id;
    private Member member;
    private List<Reply> replyList = new ArrayList<>();
    private String title;
    private String boardText;
    private Long likes;
    private LocalDateTime writeDate;

    public void setReplyList(Reply reply) {
        replyList.add(reply);
    }
}
