package com.github.chMatvey.ratingService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rating {
    private Long id;
    private Long bookId;
    private Integer stars;
}
