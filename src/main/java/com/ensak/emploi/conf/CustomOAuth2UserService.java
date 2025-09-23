package com.ensak.emploi.conf;

import com.ensak.emploi.model.Person;
import com.ensak.emploi.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    public final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("CustomOAuth2UserService instantiated");
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("OAuth2UserRequest: " + userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extract email from OAuth2 user attributes
        String email = oAuth2User.getAttribute("email");
        System.out.println("Authenticated user email: " + email);

        // Fetch the user from the database
        Person person = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("User roles from database: " + person.getRole());

        // Map user roles to authorities
        var authorities = Collections.singleton(
                new SimpleGrantedAuthority(person.getRole().toUpperCase())
        );
        System.out.println("Mapped authority: " + authorities);
        // Return a new OAuth2User with updated authorities
        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                "email" // Ensure this matches the unique identifier field in your OAuth2 provider
        );
    }
}
