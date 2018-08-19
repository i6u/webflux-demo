package com.wtz.webflux;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author witt
 * @fileName ReactorTest
 * @date 2018/8/16 19:04
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

public class ReactorTest {

    @Test
    public void fun() {
        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::println);
    }

    @Test
    public void fun1() {
        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::print);
    }

    @Test
    public void fun2() {
        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
                if (i == 5) {
                    sink.error(new RuntimeException("abc"));
                }
            }
            sink.complete();
        }).subscribe(
                System.out::println,
                System.out::println,
                () -> System.out.println("complete..."));
    }

    @Test
    public void fun3() {
        //Flux.just("Hello", "World").subscribe(System.out::println);
        //Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        //Flux.empty().subscribe(System.out::println);
        //Flux.range(1, 10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
    }

    @Test
    public void fun4() {
        //Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);
        //Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
    }

    @Test
    public void fun5() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(threadLocalRandom.nextInt(5,50));
        }
    }

    @Test
    public void fun6() {

    }

    public static void main(String[] args) {
        String[] strs = {"11", "22", "111"};
        Flux.fromArray(strs).map(Integer::parseInt)
                .subscribe(new Subscriber<Integer>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        this.subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("deal with " + integer);
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        this.subscription.cancel();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("over");
                    }
                });

    }
}
