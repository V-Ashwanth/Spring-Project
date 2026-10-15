package com.demo.Service;

import org.springframework.stereotype.Service;

import com.demo.Model.Book;

import jakarta.annotation.PostConstruct;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private Map<String, Book> bookStore = new ConcurrentHashMap<>();
    @PostConstruct
    public void init() {
        addBook(new Book("BKG-001", "Spring Boot in Action", 39, "Craig Walls", true));
        addBook(new Book("BKG-002", "Clean Code", 45, "Robert C. Martin", false));
        addBook(new Book("BKG-003", "Effective Java", 50, "Joshua Bloch", true));
    }
    public Book addBook(Book book) {
        if (book.getBookId() == null || book.getBookId().trim().isEmpty()) {
        }
        bookStore.put(book.getBookId(), book);
        return book;
    }

    public Optional<Book> searchBookById(String bookId) {
        return Optional.ofNullable(bookStore.get(bookId));
    }

    public List<Book> searchBooksByKeyword(String keyword) {
        String lowerCaseKeyword = keyword.toLowerCase();
        return bookStore.values().stream()
                .filter(b -> b.getBookName().toLowerCase().contains(lowerCaseKeyword) ||
                             b.getAuthor().toLowerCase().contains(lowerCaseKeyword))
                .collect(Collectors.toList());
    }
    
    public List<Book> searchBooksByAuthor(String author) {
        String lowerCaseAuthor = author.toLowerCase();
        return bookStore.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(lowerCaseAuthor))
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksByName(String bookName) {
        String lowerCaseName = bookName.toLowerCase();
        return bookStore.values().stream()
                .filter(b -> b.getBookName().toLowerCase().contains(lowerCaseName))
                .collect(Collectors.toList());
    }

    public boolean updateAvailability(String bookId, boolean available) {
        Book book = bookStore.get(bookId);
        if (book != null) {
            book.setAvailable(available);
            return true;
        }
        return false;
    }

    public boolean deleteBook(String bookId) {
        return bookStore.remove(bookId) != null;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookStore.values());
    }
    
    public long deleteBooksByAuthor(String author) {
        long initialSize = bookStore.size();
        bookStore.values().removeIf(b -> b.getAuthor().equalsIgnoreCase(author));
        return initialSize - bookStore.size(); // returns number of books deleted
    }

    public long deleteBooksByName(String bookName) {
        long initialSize = bookStore.size();
        bookStore.values().removeIf(b -> b.getBookName().equalsIgnoreCase(bookName));
        return initialSize - bookStore.size(); // returns number of books deleted
    }
}