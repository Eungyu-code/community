package mine.community.domain;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String mail;
    private String nickname;
    private String password;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> postList = new ArrayList<>();


    public void create(String mail, String nickname, String password, Address address) {

        this.mail = mail;
        this.nickname = nickname;
        this.password = enCodePW(password);
        this.address = address;
    }

    public String enCodePW(String password) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

}
