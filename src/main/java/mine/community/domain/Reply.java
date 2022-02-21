package mine.community.domain;

import lombok.Getter;

import javax.persistence.*;

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

    private String replyText;
    private Long address;

    public void save(String replyText, Long address) {
        this.replyText = replyText;
        this.address = address;
    }

    public void changeComment(String replyText) {
        this.replyText = replyText;
    }
}
