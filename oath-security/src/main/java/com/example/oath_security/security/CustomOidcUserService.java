package com.example.oath_security.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;


/**
 * Logs in or registers a user after OpenID Connect SignIn/Up [authenticaiton]
 */
/**
 * This class, CustomOidcUserService, extends OidcUserService and customizes the process of loading user details from an OpenID Connect (OIDC) provider. Here's a breakdown of the class:
 *
 * 1. **Constructor**:
 *    - It takes an instance of CustomOAuth2UserService as a dependency in its constructor.
 *    - It initializes a logger instance.
 *
 * 2. **loadUser method**:
 *    - Overrides the loadUser method from the parent class, OidcUserService.
 *    - It first calls the super.loadUser method to obtain the OIDC user details.
 *    - Then, it calls the buildPrincipal method of CustomOAuth2UserService (injected dependency) to construct a
 *    CustomUserDetails object from the obtained OIDC user details.
 *    - After building the principal, it sets additional information like claims,
 *    ID token, and user info obtained from the OIDC user to the CustomUserDetails object.
 *    - Finally, it returns the CustomUserDetails object as the loaded user.
 *
 * In summary, this class serves as an intermediary between the OIDC provider and your
 * application's user management system. It enhances the default behavior of loading OIDC user
 * details by customizing it to fit your application's specific requirements,
 * such as incorporating additional user information or applying custom logic during
 * the user loading process.
 *
 * ***/
public class CustomOidcUserService extends OidcUserService {

    private static final Log log = LogFactory.getLog(CustomOidcUserService.class);

    private CustomOAuth2UserService oauth2UserService;

    public CustomOidcUserService(CustomOAuth2UserService oauth2UserService) {

        this.oauth2UserService = oauth2UserService;
        log.debug("Created");
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);
        CustomUserDetails principal = oauth2UserService.buildPrincipal(oidcUser);

        principal.setClaims(oidcUser.getClaims());
        principal.setIdToken(oidcUser.getIdToken());
        principal.setUserInfo(oidcUser.getUserInfo());

        return principal;
    }
}
