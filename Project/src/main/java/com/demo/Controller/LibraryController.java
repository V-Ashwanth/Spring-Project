package com.demo.Controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.Model.Book;
import com.demo.Service.LibraryService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class LibraryController {
	@Autowired
    private LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(libraryService.searchBooksByKeyword(search));
        }
        return ResponseEntity.ok(libraryService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        Book savedBook = libraryService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> searchBookById(@PathVariable String bookId) {
        return libraryService.searchBookById(bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> searchBooksByAuthor(@PathVariable String author) {
        List<Book> books = libraryService.searchBooksByAuthor(author);
        if (!books.isEmpty()) {
            return ResponseEntity.ok(books);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/name/{bookName}")
    public ResponseEntity<List<Book>> searchBooksByName(@PathVariable String bookName) {
        List<Book> books = libraryService.searchBooksByName(bookName);
        if (!books.isEmpty()) {
            return ResponseEntity.ok(books);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{bookId}/availability")
    public ResponseEntity<Void> updateAvailability(
            @PathVariable String bookId, 
            @RequestParam boolean available) {
        boolean updated = libraryService.updateAvailability(bookId, available);
        if (updated) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        boolean deleted = libraryService.deleteBook(bookId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/author/{author}")
    public ResponseEntity<Void> deleteBooksByAuthor(@PathVariable String author) {
        long deletedCount = libraryService.deleteBooksByAuthor(author);
        if (deletedCount > 0) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }

    @DeleteMapping("/name/{bookName}")
    public ResponseEntity<Void> deleteBooksByName(@PathVariable String bookName) {
        long deletedCount = libraryService.deleteBooksByName(bookName);
        if (deletedCount > 0) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }
}
