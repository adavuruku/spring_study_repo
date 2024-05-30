package com.example.oath_security.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
/**
 * Verify every request for authorisation of token passed -> authorization
 * **/

/**
 * It looks like you're implementing a custom OAuth2UserService in Java. This class extends DefaultOAuth2UserService and overrides the loadUser method to customize user loading logic. Here's a breakdown of what's happening:
 * <p>
 * Constructor: The class takes an instance of CustomUserDetailsService in its constructor, indicating a dependency on a service for handling user details.
 * <p>
 * loadUser: This method overrides the loadUser method from DefaultOAuth2UserService. It first calls the super.loadUser method to obtain the OAuth2User details. Then, it calls the buildPrincipal method to construct a CustomUserDetails object from the OAuth2User.
 * <p>
 * buildPrincipal: This method extracts necessary information from the OAuth2User object (like email) and uses it to load user details from CustomUserDetailsService. It then returns a CustomUserDetails object.
 * <p>
 * getOAuth2Email: This method extracts the email attribute from the OAuth2 user attributes map.
 * <p>
 * Your CustomOAuth2UserService integrates OAuth2 user authentication with your application's user management system via CustomUserDetailsService. It fetches user details from OAuth2 provider, then loads or registers the user in your application's database as needed.
 * <p>
 * #login using gmail -> it first redirect to gmail [if login succeed] grab details[specified] and load into OAuth2User
 * we then extract the email from it and use it to fetch record from our db into our custom user entity
 * for authentication user -> customer user service we throw error if user doesnt exist
 */

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private CustomUserDetailsService userDetailsService;

    public CustomOAuth2UserService(CustomUserDetailsService futureDAOUserDetailsService) {
        this.userDetailsService = futureDAOUserDetailsService;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oath2User = super.loadUser(userRequest);
        return buildPrincipal(oath2User);
    }

    /**
     * Builds the security principal from the given userReqest.
     * Registers the user if not already reqistered
     */
    public CustomUserDetails buildPrincipal(OAuth2User oath2User) {

        Map<String, Object> attributes = oath2User.getAttributes();
        String email = getOAuth2Email(attributes);

        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        return user;
    }

    public String getOAuth2Email(Map<String, Object> attributes) {

        return (String) attributes.get(StandardClaimNames.EMAIL);
    }


}
