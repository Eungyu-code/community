package mine.community.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardForm {

    private Long id;
    private String title;
    private String boardText;
    private Long likes;
    private LocalDateTime writeDate;
}
