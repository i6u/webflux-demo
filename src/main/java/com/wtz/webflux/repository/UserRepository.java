package com.wtz.webflux.repository;

import com.wtz.webflux.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author witt
 * @fileName UserRepository
 * @date 2018/8/19 16:47
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {

    /** 
     * @Description: 根据年龄查询用户
     * @Param: [start, end] 
     * @return: reactor.core.publisher.Flux<com.wtz.webflux.entity.User> 
     * @Date: 2018/8/19 
     */ 
    Flux<User> findByAgeBetween(int start, int end);
}
