package com.wtz.webflux.web.demo.test;

import org.jboss.logging.Param;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author witt
 * @fileName HelloWebFluxControll
 * @date 2018/7/2 12:46
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

@RestController
@RequestMapping("/hello")
public class HelloWebFluxController {

    @GetMapping("/{abc}")
    public Mono<String> helloWebFlux(@PathVariable("abc") String name, @Param String pwd) {
        return Mono.just("welcome study webflux " + name + " / " + pwd);
    }

    @GetMapping("/uuid")
    public Mono<String> uuid() {
        return Mono.just(UUID.randomUUID().toString());
    }

    @GetMapping("/random")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data ->
                        ServerSentEvent.<Integer>builder()
                        .id(Long.toString(data.getT1()))
                        .event("random")
                        .data(data.getT2())
                        .build());
    }

}
