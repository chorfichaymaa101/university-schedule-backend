package com.ensak.emploi.conf;

import com.ensak.emploi.model.Person;
import com.ensak.emploi.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public final com.ensak.emploi.conf.CustomOAuth2UserService customOAuth2UserService;

    protected OidcUser oidcUser;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        //TODO : enable security csrf  and https
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200")); // Frontend URL
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Explicitly allow DELETE
                    config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Allow specific headers
                    config.setAllowCredentials(true); // Allow cookies or credentials
                    return config;
                })).csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
                )
                .authorizeHttpRequests(auth ->
                        auth
                                //.requestMatchers("/oauth2/**", "/login/**", "/logout/**", "/api/google").permitAll()
                                //.requestMatchers("/api/admins/**", "/api/professors/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/students/**").permitAll()
                                .requestMatchers("/api/professors/**").permitAll()
                                .requestMatchers("/api/admins/**").permitAll()
                                //.anyRequest().authenticated()
                                .anyRequest().permitAll()

                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK); // Status OK without redirection
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

        ;


        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oauth2UserService() {

        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            oidcUser = delegate.loadUser((OidcUserRequest) userRequest);

            String email = oidcUser.getAttribute("email");
            System.out.println("Authenticated user email: " + email);
            Person person = customOAuth2UserService.userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            System.out.println("User roles from database: " + person.getRole());

            // Map user roles to authorities
            var authorities = Collections.singleton(
                    new SimpleGrantedAuthority(person.getRole().toUpperCase())
            );
            System.out.println("Mapped authority: " + authorities);

            OAuth2AccessToken accessToken = userRequest.getAccessToken();

            System.out.println("token " + accessToken);

            oidcUser = new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

            return oidcUser;
        };
    }

    public OidcUser getOidcUser() {
        return oidcUser;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
// Enables method-level security annotations like @PreAuthorize or @PostAuthorize to control access to specific methods in your application.
/*public class SecurityConfig {

    private final AppUserDetailsService appUserDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(AppUserDetailsService UserDetailsService, JwtFilter jwtFilter) {
        this.appUserDetailsService = UserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    // TODO: change the encoder to BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString(); // Return the raw password unchanged
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword); // Compare the raw password with the stored password
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(appUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/").permitAll()
                                .anyRequest().authenticated() // Allow all requests
                )
                .oauth2Login(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }


    //TODO: the correct functon
    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/signup", "/user/forgotPassword").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }*/

