package mine.community.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentForm {

    private Long id;
    private String nickname;
    private String commentText;
    private LocalDateTime writeTime;
}
