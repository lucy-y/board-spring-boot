package com.lucy.board.article.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_ARTICLE")
@JsonInclude(Include.NON_NULL)
@DynamicUpdate
public class Article implements Serializable {

    private static final long serialVersionUID = 518467264036374657L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String writer;
    private Long writerSeq;
    private String title;
    private String content;
    private Timestamp regDate;
    private Timestamp updDate;
    private Long updateSeq;

}
