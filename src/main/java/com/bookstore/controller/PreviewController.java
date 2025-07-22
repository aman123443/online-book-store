package com.bookstore.controller;

import com.bookstore.service.GoogleBooksService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PreviewController {

    @Autowired
    private GoogleBooksService googleBooksService;

    @GetMapping("/preview")
    public String preview(@RequestParam String isbn, Model model) {
        JSONObject book = googleBooksService.fetchBookByISBN(isbn);
        if (book != null) {
            JSONObject volumeInfo = book.getJSONObject("volumeInfo");
            model.addAttribute("title", volumeInfo.optString("title"));
            model.addAttribute("authors", volumeInfo.optJSONArray("authors"));
            model.addAttribute("description", volumeInfo.optString("description"));
            model.addAttribute("publisher", volumeInfo.optString("publisher"));
            model.addAttribute("previewLink", volumeInfo.optString("previewLink"));
        }
        return "preview";
    }
}