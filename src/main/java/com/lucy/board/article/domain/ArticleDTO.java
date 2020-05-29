package com.lucy.board.article.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ArticleDTO{
    private Long seq;
    private Integer page;
    private String title;
    private String content;
    private Long writerSeq;
    private String type;
    private String text;

    public Integer getPage() {
        if (this.page == null) {
            return 1;
        } else {
            return this.page;
        }
    }
}
