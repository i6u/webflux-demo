package com.wtz.webflux.advice;

import com.wtz.webflux.exceptions.CheckException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * @author witt
 * @fileName CheckAdvice
 * @date 2018/8/19 21:02
 * @description 异常处理切面
 * @history <author>    <time>    <version>    <desc>
 */
@ControllerAdvice
public class CheckAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity handleBindException(WebExchangeBindException ex) {
        return new ResponseEntity(toStr(ex), HttpStatus.BAD_REQUEST);
    }

    private String toStr(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ":"+fieldError.getDefaultMessage())
                .reduce("",(s1, s2) -> s1 + "\n" + s2);
    }

    @ExceptionHandler(CheckException.class)
    public ResponseEntity handleCheckException(CheckException ex) {
        return new ResponseEntity(toStr(ex), HttpStatus.BAD_REQUEST);
    }

    private String toStr(CheckException ex) {
        return ex.getFieldName() + "::" + ex.getFieldValue();
    }

}
