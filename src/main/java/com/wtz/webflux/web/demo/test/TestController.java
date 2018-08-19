package com.wtz.webflux.web.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author witt
 * @fileName TestController
 * @date 2018/8/19 15:35
 * @description servlet vs  reactor
 * @history <author>    <time>    <version>    <desc>
 */
@RestController
@RequestMapping("/hi")
@Slf4j
public class TestController {

    private String fake(String str) {
        log.info("fake start ...");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("fake end ...");
        return str;
    }

    @GetMapping("/1")
    private String get1() {
        log.info("start ...");
        String result = fake("servlet");
        log.info("end .....");
        return result;
    }

    @GetMapping("/2")
    private Mono<String> get2() {
        log.info("start ...");
        Mono<String> result = Mono.fromSupplier(() -> fake("servlet"));
        log.info("end .....");
        return result;
    }

    @GetMapping(value = "/3",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> get3() {
        return Flux.fromStream(IntStream.range(1, 10)
                .mapToObj(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "result" + i;
                }));
    }

}
