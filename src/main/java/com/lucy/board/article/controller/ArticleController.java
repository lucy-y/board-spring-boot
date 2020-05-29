package com.lucy.board.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucy.board.article.domain.Article;
import com.lucy.board.article.domain.ArticleDTO;
import com.lucy.board.article.service.ArticleService;
import com.lucy.board.config.domain.ErrorCode;
import com.lucy.board.config.domain.Result;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 전체 목록, 유저의 목록, 검색
    @GetMapping
    public Result<Page<Article>> getList(@ModelAttribute ArticleDTO dto) {
    	Page<Article> articles;
    	
        if (dto.getText()!=null && dto.getType().equals("T")) {
          articles = articleService.getListsTitle(dto);
        } else if (dto.getText()!=null && dto.getType().equals("C")) {
          articles = articleService.getListsContent(dto);
        } else if (dto.getText()!=null && dto.getType().equals("W")) {
        	articles = articleService.getListsWriter(dto);
        } else {
          articles = articleService.getList(dto);
        }
        return new Result<>(articles, ErrorCode.OK);
    }

    // 단건 조회
    @GetMapping(value = "/{seq}")
    public Result<Article> getListOne(@PathVariable("seq") Long seq) {
        Article article = articleService.getListOne(seq);
        return new Result<>(article, ErrorCode.OK);
    }

    // 생성
    @PostMapping
    public Result<Void> add(@RequestBody Article dto) {
        articleService.add(dto);
        return new Result<>(ErrorCode.OK);
    }

    // 수정
    @PutMapping
    public Result<Void> edit(@RequestBody Article dto) {
        articleService.edit(dto);
        return new Result<>(ErrorCode.OK);
    }

    // 삭제
    @DeleteMapping(value = "/{seq}")
    public Result<Void> delete(@PathVariable Long seq) {
        ArticleDTO dto = new ArticleDTO();
        dto.setSeq(seq);
        articleService.delete(dto);
        return new Result<>(ErrorCode.OK);
    }

}