package mine.community.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String boardText;
    private Long likes = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime writeDate;

    public void save(Member member, String title, String boardText) {
        this.member = member;
        this.title = title;
        this.boardText = boardText;
        this.writeDate = LocalDateTime.now();
    }

    public void change(String text) {
        this.boardText = boardText;
    }

    public void like_post() {
        likes++;
    }
}
