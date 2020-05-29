package com.lucy.board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lucy.board.config.domain.ErrorCode;
import com.lucy.board.config.exception.CustomException;
import com.lucy.board.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION); // TODO board-access-key header
        log.info("Header Authorization Token : {}", token);

        if (token == null && "".equals(token)) {
            throw new CustomException(ErrorCode.NEED_AUTHORIZATION_TOKEN);
        } else {
            Long seq = JwtUtil.verifyToken(token);
            request.setAttribute("userSeq", seq);
        }
        return true;
    }
}
