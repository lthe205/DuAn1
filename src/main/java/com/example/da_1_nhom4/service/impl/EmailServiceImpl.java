package com.example.da_1_nhom4.service.impl;

import com.example.da_1_nhom4.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendContactEmail(String name, String email, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(email);
        mailMessage.setTo("duan1n4@gmail.com");
        mailMessage.setSubject("Liên hệ mới từ " + name);
        mailMessage.setText("Tên: " + name + "\nEmail: " + email + "\n\nNội dung tin nhắn:\n" + message);

        emailSender.send(mailMessage);
    }
}