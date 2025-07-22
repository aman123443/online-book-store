package com.bookstore.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Scanner;

@Service
public class GoogleBooksService {
    public JSONObject fetchBookByISBN(String isbn) {
        HttpURLConnection conn = null;
        try {
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
           conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            try (InputStream stream = conn.getInputStream();
                 Scanner scanner = new Scanner(stream).useDelimiter("\\A")) {
                String result = scanner.hasNext() ? scanner.next() : "";
                JSONObject json = new JSONObject(result);
                JSONArray items = json.optJSONArray("items");
                return (items != null && items.length() > 0) ? items.getJSONObject(0) : null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}