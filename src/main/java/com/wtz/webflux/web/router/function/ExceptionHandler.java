package com.wtz.webflux.web.router.function;

import com.wtz.webflux.exceptions.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author witt
 * @fileName ExceptionHandler
 * @date 2018/8/19 22:34
 * @description 异常处理类
 * @history <author>    <time>    <version>    <desc>
 */

@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();

        serverHttpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
        serverHttpResponse.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        String errorStr = toStr(ex);
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(errorStr.getBytes());

        return serverHttpResponse.writeWith(Mono.just(dataBuffer));
    }

    private String toStr(Throwable throwable) {
        if (throwable instanceof CheckException) {
            CheckException checkException = (CheckException) throwable;
            return checkException.getFieldName() + ":  -  : " + ((CheckException) throwable).getFieldValue();
        } else {
            throwable.printStackTrace();
            return throwable.toString();
        }
    }
}
