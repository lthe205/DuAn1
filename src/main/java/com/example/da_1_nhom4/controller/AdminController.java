package com.example.da_1_nhom4.controller;

import com.example.da_1_nhom4.model.User;
import com.example.da_1_nhom4.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showAdminPage(HttpSession session, Model model,
            @RequestParam(value = "section", defaultValue = "dashboard") String section) {
        User user = (User) session.getAttribute("user");
        if (user != null && "ADMIN".equals(user.getRole())) {
            model.addAttribute("currentSection", section);

            List<User> allUsers = userService.getAllUsers();
            model.addAttribute("allUsers", allUsers);

            return "admin";
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/home";
        }

        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "edit-user";
        } catch (RuntimeException e) {
            return "redirect:/admin?section=users&error=" + e.getMessage();
        }
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute User user, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/home";
        }

        try {
            userService.updateUser(user);
            return "redirect:/admin?section=users&success=User updated successfully!";
        } catch (RuntimeException e) {
            return "redirect:/admin?section=users&error=" + e.getMessage();
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/home";
        }

        try {
            userService.deleteUser(id);
            return "redirect:/admin?section=users&success=User deleted successfully!";
        } catch (RuntimeException e) {
            return "redirect:/admin?section=users&error=" + e.getMessage();
        }
    }
}