package com.bookstore.controller;

import com.bookstore.dto.OpenLibraryBookDTO;
import com.bookstore.service.OpenLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookApiController {

    @Autowired
    private OpenLibraryService service;

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<OpenLibraryBookDTO> getBook(@PathVariable String isbn) {
        return ResponseEntity.ok(service.getBookByISBN(isbn));
    }
}
