package mine.community.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyForm {

    private Long id;
    private String nickname;
    private String commentText;
    private LocalDateTime writeTime;
}
