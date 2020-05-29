package com.lucy.board.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucy.board.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmailAndPwd(String email, String pwd);

    public User findBySeq(Long seq);

    public User findByEmail(String email);
}
