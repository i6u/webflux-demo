package com.wtz.webflux.web.router.function;

import com.wtz.webflux.entity.User;
import com.wtz.webflux.repository.UserRepository;
import com.wtz.webflux.util.CheckUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;

/**
 * @author witt
 * @fileName UserHandler
 * @date 2018/8/19 21:31
 * @description 基于 router function 的 web 接口
 * <p>
 * router function 方式的开发，会把
 * servlet 时期的 HTTPServletRequest & HTTPServletResponse
 * 以及
 * router function 底层的 请求 & 响应
 * 抽象成
 * ServerRequest & ServerResponse
 * <p>
 * router function 开发步骤
 * 1. 开发 HandlerFUnction （输入 ServerRequest 返回 ServerResponse）
 * 2. 开发 RouterFunction 把请求 URL 和 HandlerFunction 对应起来
 * 3. 把 RouterFunction 包装成 HttpHandler
 * 4. 最后交给 Server（netty or 支持响应式的 servlet 容器） 处理
 * @history <author>    <time>    <version>    <desc>
 */

@Component
public class UserHandler {

    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @Description: 得到所有用户
     * @Param: [request]
     * @return: reactor.core.publisher.Mono<org.springframework.web.reactive.function.server.ServerResponse>
     * @Date: 2018/8/19
     */
    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON_UTF8)
                .body(this.userRepository.findAll(), User.class);
    }

    /**
     * @Description: 创建用户
     */
    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return userMono.flatMap(
                user -> {
                    CheckUtil.checkName(user.getName());
                    return ok()
                            .contentType(APPLICATION_JSON_UTF8)
                            .body(this.userRepository.save(user), User.class);
                });
    }

    /**
     * @Description: 根据 Id 删除用户
     */
    public Mono<ServerResponse> deleteUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        return this.userRepository.findById(id)
                .flatMap(user -> this.userRepository.delete(user).then(ok().build()))
                .switchIfEmpty(notFound().build());
    }

    /**
     * @Description: 根据 Id 查找用户
     */
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        return this.userRepository.findById(id)
                .flatMap(user -> ok().body(Mono.just(user), User.class))
                .switchIfEmpty(notFound().build());
    }
}
