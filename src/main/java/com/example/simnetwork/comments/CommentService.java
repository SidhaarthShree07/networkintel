package com.example.simnetwork.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.simnetwork.model.User;
import com.example.simnetwork.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository to fetch user details

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public void deleteComment(Long id, Long userId, boolean isAdmin) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            if (comment.get().getUser().getId().equals(userId) || isAdmin) {
                commentRepository.deleteById(id);
            } else {
                throw new UnauthorizedException("You can only delete your own comments");
            }
        } else {
            throw new CommentNotFoundException("Comment not found");
        }
    }
    
}
