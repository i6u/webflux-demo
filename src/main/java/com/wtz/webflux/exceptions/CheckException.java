package com.wtz.webflux.exceptions;

import lombok.Data;

/**
 * @author witt
 * @fileName CheckException
 * @date 2018/8/19 21:11
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

@Data
public class CheckException extends RuntimeException {

    private String fieldName;
    private String fieldValue;

    public CheckException(String fieldName, String fieldValue) {
        super();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
