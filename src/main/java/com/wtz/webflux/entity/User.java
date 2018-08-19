package com.wtz.webflux.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

/**
 * @author witt
 * @fileName User
 * @date 2018/8/19 16:46
 * @description 用户表
 * @history <author>    <time>    <version>    <desc>
 */

@Document(collection = "user")
@Data
public class User {
    @Id
    private String id;
    @NotBlank(message = "不能为空")
    private String name;
    @Range(min = 10, max = 150)
    private int age;
}
