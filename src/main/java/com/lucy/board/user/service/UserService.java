package com.lucy.board.user.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucy.board.config.domain.ErrorCode;
import com.lucy.board.config.exception.CustomException;
import com.lucy.board.user.domain.User;
import com.lucy.board.user.domain.UserDTO;
import com.lucy.board.user.repository.UserRepository;
import com.lucy.board.util.JwtUtil;
import com.lucy.board.util.MailSender;
import com.lucy.board.util.SHAEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    public User login(UserDTO dto, HttpServletResponse httpResponse) {

        if (dto.getEmail() == null || dto.getPwd() == null) {
            throw new CustomException(ErrorCode.INVALIDPARAMTER);
        }

        try {
            User user;

            user = userRepository.findByEmailAndPwd(dto.getEmail(), SHAEncoder.encode(dto.getPwd()));

            if (user != null) {
                String token = JwtUtil.createToken(user.getSeq());
                user.setToken(token);
                Cookie setCookie = new Cookie("board-access-key", URLEncoder.encode(token, "UTF-8")); // TODO 인증 토큰
                setCookie.setMaxAge(60 * 60 * 24 * 30);
                setCookie.setPath("/");
                httpResponse.addCookie(setCookie);
                return user;
            } else {
                throw new CustomException(ErrorCode.USER_NOTFOUND);
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new CustomException(ErrorCode.UNKNOWNERROR);
        }
    }

    public User signin(UserDTO dto) {

        try {

            User user = new User();

            if (dto.getEmail() == null || dto.getPwd() == null) {
                throw new CustomException(ErrorCode.INVALIDPARAMTER);
            }

            User existsCheck = userRepository.findByEmail(dto.getEmail());

            if (existsCheck != null) {
                throw new CustomException(ErrorCode.DATAEXIST);
            } else {
                try {
                    user.setEmail(dto.getEmail());
                    user.setPwd(SHAEncoder.encode(dto.getPwd()));
                    user.setNickname(dto.getNickname());
                    user.setAdmin(0);
                    user.setRegDate(new java.sql.Timestamp(new java.util.Date().getTime()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                userRepository.save(user);
                return user;
            }

        } catch (Exception e) {
            throw new CustomException(ErrorCode.SYSTEMERROR);
        }

    }

    public ErrorCode sendTempPassword(UserDTO dto) {

        try {
            User user = userRepository.findByEmail(dto.getEmail());
            if (user != null) {
                String tempPassword = UUID.randomUUID().toString().replace(" ", "").substring(0, 8);
                mailSender.send(user.getEmail(), tempPassword);
                user.setPwd(SHAEncoder.encode(tempPassword));
                user.setUpdDate(new java.sql.Timestamp(new java.util.Date().getTime()));
                userRepository.save(user);
                return ErrorCode.OK;
            } else {
                throw new CustomException(ErrorCode.USER_NOTFOUND);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(ErrorCode.SYSTEMERROR);
        }
    }

    public User edit(UserDTO dto) {

        try {
            User userExist = userRepository.findBySeq(dto.getSeq());

            if (userExist != null) {
                if (!dto.getNickname().isEmpty()) {
                    userExist.setNickname(dto.getNickname());
                }
                if (!dto.getPwd().isEmpty()) {
                    try {
                        userExist.setPwd(SHAEncoder.encode(dto.getPwd()));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
                if (dto.getAdmin() != userExist.getAdmin()) {
                    userExist.setAdmin(dto.getAdmin());
                }
                userExist.setUpdDate(new java.sql.Timestamp(new java.util.Date().getTime()));
                return userRepository.save(userExist);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SYSTEMERROR);
        }
        return null;
    }

    public User getInfo(Long userSeq, HttpServletRequest request) {
    	User result = null;
    	if(!userSeq.equals("")) {
    		try {
    			Optional<User> userExist = userRepository.findById(userSeq);
            	if (userExist.isPresent()) {
            		return userExist.get();
            	}
            } catch (Exception e) {
            	throw new CustomException(ErrorCode.USER_NOTFOUND);
            }
    	} else {
	    	Cookie[] getCookie = request.getCookies();
			if(getCookie != null){ // 만약 쿠키가 없으면 쿠키 생성
				for(int i=0; i<getCookie.length; i++){
	    			Cookie c = getCookie[i];
	    			if(c.getName().equals("board-access-key")){
	    				String token = c.getValue();
	    				try {
	    	    			Optional<User> userExist = userRepository.findById(JwtUtil.verifyToken(token));
	    	            	if (userExist.isPresent()) {
	    	            		return userExist.get();
	    	            	}
	    	            } catch (Exception e) {
	    	            	throw new CustomException(ErrorCode.USER_NOTFOUND);
	    	            }
	    			}
				}
			}
    	}
    	return result;
    }

    public void delete(User dto) {
        userRepository.deleteById(dto.getSeq());
    }
}
