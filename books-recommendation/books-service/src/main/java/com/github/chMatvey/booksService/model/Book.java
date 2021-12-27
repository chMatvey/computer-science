package com.github.chMatvey.booksService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    private Long id;
    private String title;
    private String author;
}
