package com.wtz.webflux.util;

import com.wtz.webflux.exceptions.CheckException;

import java.util.stream.Stream;

/**
 * @author witt
 * @fileName CheckUtil
 * @date 2018/8/19 21:11
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

public class CheckUtil {

    private static final String[] INVALID_NAMES = {"admin", "root", "password"};

    /** 
     * @Description: 检查名字不合法性
     * @Param: [name] 
     * @return: void 
     * @Date: 2018/8/19 
     */ 
    public static void checkName(String name) {
        Stream.of(INVALID_NAMES).filter(s -> s.equalsIgnoreCase(name))
                .findAny()
                .ifPresent(s -> {
                    throw new CheckException("特殊的 name",name);
                });
    }
}
