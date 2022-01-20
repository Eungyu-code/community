package mine.community.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String text;
    private Long address;

    public void save(String text, Long address) {
        this.text = text;
        this.address = address;
    }

    public void changeComment(String text) {
        this.text = text;
    }
}
