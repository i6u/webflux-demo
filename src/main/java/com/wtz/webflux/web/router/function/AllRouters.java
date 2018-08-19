package com.wtz.webflux.web.router.function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

/**
 * @author witt
 * @fileName AllRouters
 * @date 2018/8/19 21:54
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return nest(
                path("/two/user"),
                route(GET("/"), userHandler::getAllUser)
                        .andRoute(POST("/").and(accept(APPLICATION_JSON_UTF8)), userHandler::createUser)
                        .andRoute(DELETE("/{id}"), userHandler::deleteUserById)
                        .andRoute(GET("/{id}"), userHandler::getUserById)
        );
    }
}
