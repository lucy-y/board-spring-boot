package com.lucy.board.config.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Result<T> {

    private String result;

    private String errormsg;

    private T object;

    public Result(ErrorCode errorCode) {
        this.result = errorCode.getErrno();
        this.errormsg = errorCode.getMessage();
    }

    public Result(T object, ErrorCode errorCode) {
        this.result = errorCode.getErrno();
        this.errormsg = errorCode.getMessage();
        this.object = object;
    }

    public Result(T object, String result, String errormsg) {
        this.result = result;
        this.errormsg = errormsg;
        this.object = object;
    }
}
