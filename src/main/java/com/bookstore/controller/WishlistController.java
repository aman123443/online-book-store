package com.bookstore.controller;

import com.bookstore.model.User;
// import com.bookstore.model.Wishlist;
import com.bookstore.service.WishlistService;
import jakarta.servlet.http.HttpSession;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    // @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }
        @GetMapping("/wishlist")
    public String showWishlist(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("wishlist", wishlistService.getWishlistByUser(user.getId()));
        return "wishlist";
    }

    @PostMapping("/wishlist/add/{bookId}")
    public String addToWishlist(@PathVariable Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            wishlistService.addToWishlist(user.getId(), bookId);
        }
        return "redirect:/";
    }

    @PostMapping("/wishlist/remove/{bookId}")
    public String removeFromWishlist(@PathVariable Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            wishlistService.removeFromWishlist(user.getId(), bookId);
        }
        return "redirect:/wishlist";
    }
}
