package com.example.km.security;

import com.example.km.entity.User;
import com.example.km.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Custom OAuth2 User Service for handling user login
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oauth2User.getAttributes();

        User user = saveOrUpdateUser(registrationId, attributes);

        log.info("User logged in via {}: {}", registrationId, user.getUsername());

        return new CustomOAuth2User(oauth2User, user);
    }

    private User saveOrUpdateUser(String provider, Map<String, Object> attributes) {
        String providerId;
        String username;
        String email;
        String name;
        String avatar;

        if ("github".equals(provider)) {
            providerId = String.valueOf(attributes.get("id"));
            username = (String) attributes.get("login");
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            avatar = (String) attributes.get("avatar_url");
        } else if ("google".equals(provider)) {
            providerId = (String) attributes.get("sub");
            username = (String) attributes.get("email");
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            avatar = (String) attributes.get("picture");
        } else {
            throw new OAuth2AuthenticationException("Unsupported OAuth2 provider: " + provider);
        }

        return userRepository.findByProviderAndProviderId(provider, providerId)
            .map(existingUser -> {
                // Update existing user
                existingUser.setName(name);
                existingUser.setAvatar(avatar);
                if (email != null) {
                    existingUser.setEmail(email);
                }
                return userRepository.save(existingUser);
            })
            .orElseGet(() -> {
                // Create new user
                User newUser = User.builder()
                    .username(username)
                    .email(email)
                    .name(name)
                    .avatar(avatar)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
                return userRepository.save(newUser);
            });
    }
}
