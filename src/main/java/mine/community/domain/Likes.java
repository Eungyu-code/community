package mine.community.domain;

import javax.persistence.*;

@Entity
public class Likes {

    @Id
    @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    private Long likes = 0L;

    @OneToOne(mappedBy = "likes")
    private Board board;

    public void like_post() {
        likes++;
    }

    public Long getLikes() {
        return likes;
    }
}
