package com.example.simnetwork.security;

import com.example.simnetwork.model.User;
import com.example.simnetwork.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public OAuth2SuccessHandler(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauthToken.getPrincipal();

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String picture = oauth2User.getAttribute("picture");
        String googleId = oauth2User.getAttribute("sub");

        User user = userService.processOAuthPostLogin(email, name, picture, googleId);
        
        // Generate both access and refresh tokens
        String accessToken = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        // Redirect to React frontend with both tokens
        String redirectUrl = String.format("http://localhost:3000/home?access_token=%s&refresh_token=%s", 
            accessToken, refreshToken);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}


// package com.example.simnetwork.security;

// import com.example.simnetwork.model.User;
// import com.example.simnetwork.service.UserService;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
// import org.springframework.stereotype.Component;

// import java.io.IOException;

// @Component
// public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

//     private final UserService userService;

//     public OAuth2SuccessHandler(UserService userService) {
//         this.userService = userService;
//     }

//     @Override
//     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                         Authentication authentication) throws IOException, ServletException {

//         OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//         OAuth2User oauth2User = oauthToken.getPrincipal();

//         String email = oauth2User.getAttribute("email");
//         String name = oauth2User.getAttribute("name");
//         String picture = oauth2User.getAttribute("picture");
//         String googleId = oauth2User.getAttribute("sub");

//         userService.processOAuthPostLogin(email, name, picture, googleId);

//         response.setContentType("text/plain");
//         response.getWriter().write("Login successful!");
//     }
// }
