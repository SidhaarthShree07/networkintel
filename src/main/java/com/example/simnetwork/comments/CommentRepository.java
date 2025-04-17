package com.example.simnetwork.comments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simnetwork.model.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser(User user);
}

