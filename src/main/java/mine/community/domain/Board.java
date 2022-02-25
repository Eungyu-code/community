package mine.community.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String boardText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime writeDate;


    private LikeStatus likeStatus;

    @OneToMany(mappedBy = "board")
    private List<Reply> replyList = new ArrayList<>();

    private Long replyNumber = 0L;

    public void save(Member member, String title, String boardText) {
        this.member = member;
        this.title = title;
        this.boardText = boardText;
        this.writeDate = LocalDateTime.now();
    }

    public void change(String title, String boardText) {
        this.title = title;
        this.boardText = boardText;
    }

    public void addReply() {
        replyNumber++;
    }

}
