package com.lucy.board.config.exception;

import com.lucy.board.config.domain.ErrorCode;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorCode code;

    public CustomException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ErrorCode getCode() {
        return this.code;
    }
}
