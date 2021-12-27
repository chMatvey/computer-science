package com.github.chMatvey.booksService.service;

import com.github.chMatvey.booksService.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {
    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book create(Book book);

    Book update(Book book, Long id);

    Book update(Map<String, Object> updates, Long id);

    void delete(Long id);
}
