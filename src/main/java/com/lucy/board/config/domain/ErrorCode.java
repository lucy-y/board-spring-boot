package com.lucy.board.config.domain;

/**
 * @author lucy
 * 처리결과 enum
 */
public enum ErrorCode {

    OK("00", "정상처리되었습니다."), DATAEXIST("1", "데이터가 이미 존재합니다."), NORESULT("2", "결과가 없습니다"),
    USER_NOTFOUND("3", "유저 정보가 없습니다."), TOKEN_ERROR("95", "토큰 오류"), INVALIDAUTH("96", "인증 값 오류입니다."),

    INVALIDPARAMTER("97", "입력값의 형식이 잘못 되었습니다."), SYSTEMERROR("98", "시스템 오류가 발생하였습니다."),
    UNKNOWNERROR("99", "알 수 없는 오류가 발생하였습니다"), NEED_AUTHORIZATION_TOKEN("100", "인증 토큰이 누락되었습니다.");

    private String errno;
    private String message;

    private ErrorCode(String errno, String message) {
        this.errno = errno;
        this.message = message;
    };

    public String getErrno() {
        return this.errno;
    }

    public String getMessage() {
        return this.message;
    }

}
