package com.example.simnetwork.comments;

import com.example.simnetwork.model.User;
import com.example.simnetwork.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "https:/", allowCredentials = "true")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    // Endpoint to create a comment
    @PostMapping("/")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest commentRequest) {
        // Fetch the currently logged-in user's email
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserEmail = userDetails.getUsername();

        // Get the user from the database
        User user = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Create and set the new comment
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setUserName(user.getName());  // This will use the 'name' field of the User entity

        comment.setCommentText(commentRequest.getText());
        comment.setCreatedAt(LocalDateTime.now());

        // Save the comment
        Comment savedComment = commentService.addComment(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    // Endpoint to delete a comment
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        // Fetch the currently logged-in user's email
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserEmail = userDetails.getUsername();

        // Get the user from the database
        User user = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Long userId = user.getId();
        boolean isAdmin = user.getRoles().contains("ADMIN");

        // Find the comment to delete
        Comment comment = commentService.getCommentById(id)
            .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        // Check if the user is the owner of the comment or an admin
        if (isAdmin || comment.getUser().getId().equals(userId)) {
            commentService.deleteComment(id, userId, isAdmin);
            return ResponseEntity.ok("Comment deleted successfully");
        } else {
            throw new UnauthorizedException("You are not authorized to delete this comment");
        }
    }
}
