package com.bookstore.repository;

import com.bookstore.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserId(Long userId);
    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
