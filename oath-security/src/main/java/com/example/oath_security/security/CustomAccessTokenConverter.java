package com.example.oath_security.security;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

/**
 * This Java class `CustomAccessTokenConverter` extends `DefaultAccessTokenConverter` and overrides the `extractAuthentication` method. Here's what it does:
 *
 * 1. **Purpose**:
 *    - This class is likely used in an OAuth 2.0 authentication and authorization scenario, where it customizes the process of extracting authentication information from an access token.
 *
 * 2. **Method Override**:
 *    - `extractAuthentication(Map<String, ?> claims)`: Overrides the method from the parent class, `DefaultAccessTokenConverter`, which is responsible for extracting authentication details from claims (data contained within the access token).
 *    - It first delegates the extraction process to the superclass by calling `super.extractAuthentication(claims)`.
 *    - Then, it sets additional details to the extracted `OAuth2Authentication` object by calling `authentication.setDetails(claims)`, where `claims` represent additional information extracted from the access token.
 *
 * 3. **Customization**:
 *    - By setting additional details to the authentication object, this class allows for custom information contained within the access token's claims to be included in the authentication process.
 *
 * 4. **Usage**:
 *    - This class would typically be configured as part of an OAuth 2.0 authentication server setup, where it customizes how authentication details are extracted from access tokens.
 *    - It might be used in scenarios where additional information needs to be carried along with the authentication process, beyond what is provided by default.
 *
 * In summary, `CustomAccessTokenConverter` provides a way to customize the extraction of authentication details from OAuth 2.0 access tokens, allowing for additional information from token claims to be included in the authentication process.
 * ***/
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication
                = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }
}