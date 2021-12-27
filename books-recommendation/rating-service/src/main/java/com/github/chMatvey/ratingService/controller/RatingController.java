package com.github.chMatvey.ratingService.controller;

import com.github.chMatvey.ratingService.model.Rating;
import com.github.chMatvey.ratingService.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("api/rating")
public record RatingController(RatingService ratingService) {

    @GetMapping("/{id}")
    ResponseEntity<Rating> find(@PathVariable Long id) {
        return of(ratingService.findById(id));
    }

    @GetMapping
    ResponseEntity<List<Rating>> findAll() {
        return ok(ratingService.findAll());
    }

    @PostMapping
    ResponseEntity<Rating> create(@RequestBody Rating rating) {
        Rating result = ratingService.create(rating);
        URI uri = URI.create("api/rating/" + result.getId());
        return created(uri).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Rating> update(@PathVariable Long id, @RequestBody Rating rating) {
        return ok(ratingService.update(id, rating));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Rating> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ok(ratingService.update(id, updates));
    }
}
