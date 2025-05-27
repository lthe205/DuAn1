package com.example.da_1_nhom4.controller;

import com.example.da_1_nhom4.model.User;
import com.example.da_1_nhom4.service.UserService;
import com.example.da_1_nhom4.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String fullName,
            Model model) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setRole("USER");
            user.setEnabled(true);

            userService.createUser(user);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        try {
            User user = userService.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                if (user.isEnabled()) {
                    session.setAttribute("user", user);
                    return "redirect:/home";
                } else {
                    model.addAttribute("error", "Tài khoản của bạn đã bị khóa.");
                    return "login";
                }
            } else {
                model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", session.getAttribute("user"));
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/account")
    public String showAccountPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", session.getAttribute("user"));
        return "account";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    @PostMapping("/contact/send")
    public String sendContactEmail(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String message,
            Model model) {
        try {
            emailService.sendContactEmail(name, email, message);
            model.addAttribute("success", "Cảm ơn bạn đã liên hệ. Chúng tôi sẽ phản hồi sớm nhất có thể!");
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi gửi tin nhắn. Vui lòng thử lại sau.");
        }
        return "contact";
    }
}