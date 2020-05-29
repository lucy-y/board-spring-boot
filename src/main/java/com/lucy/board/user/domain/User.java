package com.lucy.board.user.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "TB_USER")
@JsonInclude(Include.NON_NULL)
@ToString(exclude = "pwd")
@DynamicUpdate
public class User implements Serializable {
    private static final long serialVersionUID = -3018230194655287097L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String email;

    @JsonIgnore
    private String pwd;

    private String nickname;

    private int admin;

    private Timestamp regDate;

    private Timestamp updDate;

    @Transient
    private String token;

}
