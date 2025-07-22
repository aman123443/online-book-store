package com.bookstore.dto;

import java.util.List;

public class OpenLibraryBookDTO {
    private String title;
    private List<Author> authors;

    public static class Author {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Author> getAuthors() { return authors; }
    public void setAuthors(List<Author> authors) { this.authors = authors; }
}
