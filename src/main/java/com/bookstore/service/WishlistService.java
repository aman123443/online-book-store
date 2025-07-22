package com.bookstore.service;

import com.bookstore.model.Wishlist;
import com.bookstore.model.User;
import com.bookstore.model.Book;
import com.bookstore.repository.WishlistRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

  
    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<Wishlist> getWishlistByUser(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    public void addToWishlist(Long userId, Long bookId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (userOpt.isPresent() && bookOpt.isPresent()) {
            Wishlist wishlist = new Wishlist();
            wishlist.setUser(userOpt.get());
            wishlist.setBook(bookOpt.get());
            wishlistRepository.save(wishlist);
        }
    }

    public void removeFromWishlist(Long userId, Long bookId) {
        List<Wishlist> wishlists = wishlistRepository.findByUserId(userId);
        for (Wishlist wishlist : wishlists) {
            if (wishlist.getBook().getId().equals(bookId)) {
                wishlistRepository.delete(wishlist);
                break;
            }
        }
    }
}