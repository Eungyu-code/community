package mine.community.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String text;
    private Long likes = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void save(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public void change(String text) {
        this.text = text;
    }

    public void like_post() {
        likes++;
    }
}
