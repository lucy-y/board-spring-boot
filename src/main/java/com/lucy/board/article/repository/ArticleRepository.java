package com.lucy.board.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucy.board.article.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    public Page<Article> findAll(Pageable request);

    public Page<Article> findByWriterSeq(Long writerSeq, Pageable request);

    public Page<Article> findByTitleIsContaining(String title, Pageable request);

    public Page<Article> findByContentIsContaining(String content, Pageable request);

	public Page<Article> findByWriterIsContaining(String writer, Pageable request);

}
