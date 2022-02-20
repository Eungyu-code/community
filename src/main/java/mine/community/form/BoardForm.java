package mine.community.form;

import lombok.Getter;
import lombok.Setter;
import mine.community.domain.Member;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardForm {

    private Long id;
    private Member member;
    private String title;
    private String boardText;
    private Long likes;
    private LocalDateTime writeDate;
}
