package com.lucy.board.user.domain;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -7490651797002793366L;

    @Size(min = 8, max = 30)
    @Email
    private String email;

    @Size(min = 8, max = 30)
    private String pwd;

    @Size(min = 8, max = 30)
    private String pwdRe;

    private String nickname;

    private int admin;

    private Long seq;

}
