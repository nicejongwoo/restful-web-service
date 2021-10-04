package com.example.rws.user;

import com.example.rws.post.Post;
import com.example.rws.post.PostRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jpa")
public class UserJPAController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/users")
    public MappingJacksonValue userList() {
        List<User> users = userRepository.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "posts");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFountException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "posts");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public MappingJacksonValue userList(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFountException(String.format("ID{%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("posts");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);
        return mapping;
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<User> createUser(@PathVariable int id, @Valid @RequestBody Post post) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFountException(String.format("ID{%s] not found", id));
        }

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
