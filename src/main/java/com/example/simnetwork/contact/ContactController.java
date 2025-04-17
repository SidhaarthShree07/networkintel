package com.example.simnetwork.contact;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "https:/", allowCredentials = "true")
public class ContactController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public ResponseEntity<String> submitForm(@Valid @RequestBody ContactForm form) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("iamsak886588@gmail.com");
            message.setSubject("New Contact Form Submission from " + form.getName());
            message.setText(
                "Name: " + form.getName() + "\n" +
                "Email: " + form.getEmail() + "\n" +
                "Phone: " + form.getPhone() + "\n\n" +
                "Message:\n" + form.getMessage()
            );

            mailSender.send(message);

            return ResponseEntity.ok()
                .body("✅ Your message has been sent successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.status(500)
                .body("❌ Failed to send message. Please try again later.");
        }
    }
}
