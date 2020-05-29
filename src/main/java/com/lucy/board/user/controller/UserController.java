package com.lucy.board.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucy.board.article.domain.ArticleDTO;
import com.lucy.board.article.service.ArticleService;
import com.lucy.board.config.domain.ErrorCode;
import com.lucy.board.config.domain.Result;
import com.lucy.board.user.domain.User;
import com.lucy.board.user.domain.UserDTO;
import com.lucy.board.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    // 유저 로그인
    @PostMapping("/login")
    public Result<User> login(@RequestBody UserDTO dto, HttpServletResponse response) {
        User result = userService.login(dto, response);
        return new Result<>(result, ErrorCode.OK);
    }

    // 유저 등록
    @PostMapping("/signup")
    public Result<User> signin(@RequestBody UserDTO dto) {
        User result = userService.signin(dto);
        return new Result<>(result, ErrorCode.OK);
    }

    // 임시 비밀번호 발송
    @PostMapping("/send-temp-pwd")
    public Result<Void> sendTempPassword(@RequestBody UserDTO dto) {
        ErrorCode code = userService.sendTempPassword(dto);
        return new Result<>(code);
    }

    // 정보수정
    @PutMapping
    public Result<User> edit(@RequestBody UserDTO dto, @RequestAttribute("userSeq") Long userSeq) {
        dto.setSeq(userSeq);
        User result = userService.edit(dto);
        return new Result<>(result, ErrorCode.OK);
    }

    // 로그인한 유저 정보
    @GetMapping
    public Result<User> userInfo(@RequestAttribute("userSeq") Long userSeq, HttpServletRequest request) {
        User result = userService.getInfo(userSeq, request);
        return new Result<>(result, ErrorCode.OK);
    }
    
    // 삭제
    @DeleteMapping(value = "/{seq}")
    public Result<Void> delete(@PathVariable Long seq) {
        User dto = new User();
        dto.setSeq(seq);
        userService.delete(dto);
        return new Result<>(ErrorCode.OK);
    }

}
