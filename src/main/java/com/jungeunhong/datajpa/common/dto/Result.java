package com.jungeunhong.datajpa.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {

    private int code;
    private T data;

}
