package mine.community.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Reply {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime writeTime;

    private String replyText;


    public void addReply(String replyText, Member member, Board board) {
        this.replyText = replyText;
        this.member = member;
        this.board = board;
        this.writeTime = LocalDateTime.now();
    }
}
