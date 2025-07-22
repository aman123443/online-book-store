package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 6);
        Page<Book> bookPage = (keyword != null && !keyword.isEmpty())
                ? bookRepo.findByTitleContainingIgnoreCase(keyword, pageable)
                : bookRepo.findAll(pageable);

        List<Book> books = bookPage.getContent();
        model.addAttribute("books", books);
        model.addAttribute("totalBooks", bookRepo.count());
        model.addAttribute("totalCategories", bookRepo.findAll().stream()
                .map(Book::getCategory).collect(Collectors.toSet()).size());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());

        List<Book> allBooks = bookRepo.findAll();
        Collections.shuffle(allBooks, new Random());
        model.addAttribute("featuredBooks", allBooks.stream().limit(2).collect(Collectors.toList()));

        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book, @RequestParam("imageFile") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String uploadDir = new File(System.getProperty("user.dir"), "uploads").getAbsolutePath();
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) uploadDirFile.mkdirs();
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                File saveFile = new File(uploadDir, fileName);
                file.transferTo(saveFile);
                book.setImageName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/add?error";
            }
        }
        bookRepo.save(book);
        return "redirect:/?success";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepo.deleteById(id);
        return "redirect:/?deleted";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Book> allBooks = bookRepo.findAll();
        int totalBooks = allBooks.size();
        int totalCategories = allBooks.stream().map(Book::getCategory).collect(Collectors.toSet()).size();
        Book latestBook = allBooks.stream().max((b1, b2) -> b1.getId().compareTo(b2.getId())).orElse(null);

        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("latestBook", latestBook);
        return "dashboard";
    }
}