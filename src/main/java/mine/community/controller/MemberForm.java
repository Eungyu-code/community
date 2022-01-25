package mine.community.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    private String nickname;

    private String password;

    private String mail;

    private String country;
    private String city;
    private String street;
    private String zipcode;
}
