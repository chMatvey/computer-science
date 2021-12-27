package com.github.chMatvey.booksService.controller;

import com.github.chMatvey.booksService.model.Book;
import com.github.chMatvey.booksService.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("api/book")
public record BookController(BookService bookService) {

    @GetMapping("/{id}")
    ResponseEntity<Book> find(@PathVariable Long id) {
        return of(bookService.findById(id));
    }

    @GetMapping
    ResponseEntity<List<Book>> findAll() {
        return ok(bookService.findAll());
    }

    @PostMapping
    ResponseEntity<Book> create(@RequestBody Book book) {
        Book result = bookService.create(book);
        URI uri = URI.create("api/book/" + result.getId());
        return created(uri).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        return ok(bookService.update(book, id));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Book> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ok(bookService.update(updates, id));
    }
}
