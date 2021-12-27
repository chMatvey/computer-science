package com.github.chMatvey.ratingService.service;

import com.github.chMatvey.ratingService.model.Rating;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RatingService {
    Optional<Rating> findById(Long id);

    List<Rating> findAll();

    Rating create(Rating book);

    Rating update(Long id, Rating book);

    Rating update(Long id, Map<String, Object> updates);

    void delete(Long id);
}
