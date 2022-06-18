package com.jungeunhong.datajpa.common.exception;

import com.jungeunhong.datajpa.common.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalException{

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> commonEx_500(RuntimeException exception){
      log.error("에러발생 : {}", Arrays.toString(exception.getStackTrace()));
      return new Result<>(-1, exception.getLocalizedMessage());
    }

}
