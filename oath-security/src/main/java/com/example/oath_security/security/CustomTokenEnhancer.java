package com.example.oath_security.security;


import com.example.oath_security.entities.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * This Java class `CustomTokenEnhancer` implements the `TokenEnhancer` interface, which is used to enhance OAuth2 access tokens with additional information. Let's break down what this class does:
 *
 * 1. **Purpose**:
 *    - This class is used to add custom information to OAuth2 access tokens during the token enhancement process.
 *
 * 2. **Method Override**:
 *    - `enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication)`: Overrides the `enhance` method defined in the `TokenEnhancer` interface. This method is called when enhancing an access token with additional information.
 *    - It takes an `OAuth2AccessToken` object and an `OAuth2Authentication` object as parameters.
 *    - Inside this method:
 *      - It extracts the user information (presumably a `CustomUserDetails` object) from the `authentication` object, which represents the current authenticated user.
 *      - It creates a `Map<String, Object>` named `additionalInfo` to store custom key-value pairs that will be added to the access token.
 *      - It adds custom information such as user ID, email, and full name to the `additionalInfo` map.
 *      - It sets the `additionalInfo` map to the access token using `setAdditionalInformation` method.
 *
 * 3. **Customization**:
 *    - This class allows you to customize the content of the OAuth2 access token by adding custom information to it. In this case, it adds user-related information like ID, email, and full name.
 *
 * 4. **Usage**:
 *    - Typically, this class would be used in conjunction with an OAuth2 authorization server configuration to enhance access tokens with custom information.
 *    - After configuration, whenever an access token is issued, this `TokenEnhancer` implementation is invoked to add the specified custom information to the token.
 *
 * In summary, `CustomTokenEnhancer` provides a way to enrich OAuth2 access tokens with additional user-related information, making the tokens more useful for downstream services that consume them.
 * ***/

public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        User user = (CustomUserDetails) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();

        additionalInfo.put("userId", user.getId());
        additionalInfo.put("userEmail", user.getEmail());
        additionalInfo.put("userFullName", user.toString());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }


}