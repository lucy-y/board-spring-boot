package com.lucy.board.article.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucy.board.article.domain.Article;
import com.lucy.board.article.domain.ArticleDTO;
import com.lucy.board.article.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Page<Article> getList(ArticleDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, 10, Sort.by(Direction.DESC, "seq"));
        return articleRepository.findAll(pageable);
    }

    public Article getListOne(long seq) {
        Optional<Article> optional = articleRepository.findById(seq);
        return optional.isPresent() ? optional.get() : null;
    }

    public Page<Article> getListsTitle(ArticleDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, 10, Sort.by(Direction.DESC, "seq"));
        return articleRepository.findByTitleIsContaining(dto.getText(), pageable);
    }

    public Page<Article> getListsContent(ArticleDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, 10, Sort.by(Direction.DESC, "seq"));
        return articleRepository.findByContentIsContaining(dto.getText(), pageable);
    }
    
    public Page<Article> getListsWriter(ArticleDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, 10, Sort.by(Direction.DESC, "seq"));
        return articleRepository.findByWriterIsContaining(dto.getText(), pageable);
    }

    public Page<Article> getListByWriter(ArticleDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, 10, Sort.by(Direction.DESC, "seq"));
        return articleRepository.findByWriterSeq(dto.getWriterSeq(), pageable);
    }

    public void add(Article dto) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setWriter(dto.getWriter());
        article.setWriterSeq(dto.getWriterSeq());
        article.setRegDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        articleRepository.save(article);
    }

    public void edit(Article dto) {
        Optional<Article> articleExist = articleRepository.findById(dto.getSeq());

        articleExist.ifPresent(article -> {
            article.setTitle(dto.getTitle());
            article.setContent(dto.getContent());
            article.setWriter(dto.getWriter());
            article.setUpdDate(new java.sql.Timestamp(new java.util.Date().getTime()));
            article.setUpdateSeq(dto.getUpdateSeq());
            articleRepository.save(article);
        });
    }

    public void delete(ArticleDTO dto) {
        articleRepository.deleteById(dto.getSeq());
    }

}
