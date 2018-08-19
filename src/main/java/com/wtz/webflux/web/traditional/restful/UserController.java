package com.wtz.webflux.web.traditional.restful;

import com.wtz.webflux.entity.User;
import com.wtz.webflux.repository.UserRepository;
import com.wtz.webflux.util.CheckUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author witt
 * @fileName UserController
 * @date 2018/8/19 16:51
 * @description
 * @history <author>    <time>    <version>    <desc>
 */

@RestController
@RequestMapping("/one/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public Mono<User> createUser(@Valid @RequestBody User user) {
        CheckUtil.checkName(user.getName());
        return this.userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id) {
        return this.userRepository.findById(id)
                .flatMap(
                        user -> this.userRepository.delete(user)
                                .then(
                                        Mono.just(new ResponseEntity<Void>(HttpStatus.OK))
                                )
                )
                .defaultIfEmpty(
                        new ResponseEntity<>(HttpStatus.NOT_FOUND)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id, @Valid @RequestBody User user) {
        CheckUtil.checkName(user.getName());
        return this.userRepository.findById(id)
                .flatMap(u -> {
                    u.setName(user.getName());
                    u.setAge(user.getAge());
                    return this.userRepository.save(u);
                })
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable("id") String id) {
        return this.userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/age/{start}/{end}")
    public Flux<User> findUserByAge(@PathVariable("start") int start, @PathVariable("end") int end) {
        return this.userRepository.findByAgeBetween(start, end);
    }

    @GetMapping(value = "/stream/age/{start}/{end}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindUserByAge(@PathVariable("start") int start, @PathVariable("end") int end) {
        return this.userRepository.findByAgeBetween(start, end);
    }
}
