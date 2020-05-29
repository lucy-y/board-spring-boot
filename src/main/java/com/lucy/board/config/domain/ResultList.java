package com.lucy.board.config.domain;

import java.util.List;

import lombok.Data;

@Data
public class ResultList {

    private String result = null;

    private String errormsg = null;

    private List<?> list = null;

    private Long totalCnt = null;

}
