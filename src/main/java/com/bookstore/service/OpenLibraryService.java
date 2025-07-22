package com.bookstore.service;

import com.bookstore.dto.OpenLibraryBookDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OpenLibraryService {
    private final RestTemplate restTemplate = new RestTemplate();

    public OpenLibraryBookDTO getBookByISBN(String isbn) {
    String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";
    ResponseEntity<Map<String, OpenLibraryBookDTO>> response =
        restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {});
    Map<String, OpenLibraryBookDTO> body = response.getBody();
    return (body != null) ? body.get("ISBN:" + isbn) : null;
}
}
