package com.example.simnetwork.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final OAuth2 oauth2 = new OAuth2();

    public OAuth2 getOauth2() {
        return oauth2;
    }

    public static class OAuth2 {
        private String authorizedRedirectUri;

        public String getAuthorizedRedirectUri() {
            return authorizedRedirectUri;
        }

        public void setAuthorizedRedirectUri(String authorizedRedirectUri) {
            this.authorizedRedirectUri = authorizedRedirectUri;
        }
    }
}
