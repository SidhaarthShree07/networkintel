package com.example.simnetwork.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.example.simnetwork.model.User;
import com.example.simnetwork.repository.UserRepository;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String picture = oauth2User.getAttribute("picture");
        String googleId = oauth2User.getAttribute("sub");

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isEmpty()) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPicture(picture);
            user.setGoogleId(googleId);
            
            // First user is admin, rest are regular users
            if (userRepository.count() == 0) {
                user.getRoles().add("ROLE_ADMIN");
            }
            user.getRoles().add("ROLE_USER");
            
            user = userRepository.save(user);
        } else {
            user = userOptional.get();
        }

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(oauth2User.getAttributes());
        
        return userPrincipal;
    }
} 