package com.ensak.emploi.web;

import com.ensak.emploi.conf.SecurityConfig;
import com.ensak.emploi.constents.EmploiConstants;
import com.ensak.emploi.model.AuthResponse;
import com.ensak.emploi.model.IdTokenRequest;
import com.ensak.emploi.model.Person;
import com.ensak.emploi.services.UserService;
import com.ensak.emploi.utils.EmploiUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

/*
 * if a request map is not being sent you probably need to add the @RequestBody
 * */
@RestController
public class UserController {

    @Autowired
    UserService userService;
    /*
     * @PostMapping(path = "/signup")
     * public ResponseEntity<String> signUp(@RequestBody Map<String, String>
     * requestMap) {
     * try {
     * System.out.println(requestMap);
     * return userService.signUp(requestMap);
     * } catch (Exception ex) {
     * ex.printStackTrace();
     * }
     * return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG,
     * HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     */

    private static final String CLIENT_ID = "868806523294-jngbc1tel77o9352h52og9c14u8hrt5j.apps.googleusercontent.com";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final NetHttpTransport TRANSPORT = new NetHttpTransport();

    @PostMapping("/google")
    public ResponseEntity<AuthResponse> authenticateWithGoogle(@RequestBody IdTokenRequest request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(TRANSPORT, JSON_FACTORY)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();
            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            System.out.println("request " + request);
            System.out.println("token" + request.getIdToken());
            if (idToken != null) {
                System.out.println("not null");
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Extract user details
                String email = payload.getEmail();
                System.out.println("emil : " + email);
                Person person = new Person();
                try {
                    person = userService.findByEmailId(email);
                } catch (UsernameNotFoundException e) {
                    e.printStackTrace();
                }

                System.out.println("User roles from database: " + person.getRole());

                String userId = person.getId().toString();
                String role = person.getRole();
                String name = person.getName();
                System.out.println("User id from database: " + userId);

                // Generate a JWT for the authenticated user
                String jwt = generateJwt(userId, role, email, name);

                return ResponseEntity.ok(new AuthResponse(jwt));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/logout")
    public Boolean logout() {
        System.out.println("true");
        return true;
    }

    // TODO: put this in another folder
    private String generateJwt(String userId, String role, String email, String name) {
        SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        long expirationTime = 86400000; // 1

        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .claim("email", email)
                .claim("name", name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
        // TODO: Implement JWT generation (e.g., using jjwt library)
        // return "mock-jwt-token-for-" + email;
    }

    @GetMapping("/oauth2/callback")
    public ResponseEntity<?> loginSuccess(@AuthenticationPrincipal OidcUser user) {
        String email = user.getAttribute("email");
        String name = user.getAttribute("name");
        String idToken = String.valueOf(user.getIdToken()); // This works if the token is part of user attributes.
        System.out.println("id token " + idToken + " " + name + " " + email);
        return ResponseEntity.ok("Token: " + idToken);
    }
    // TODO: Validate the Token on the Backend

    @GetMapping(path = "/get")
    public ResponseEntity<List<Person>> getAllUser() {

        try {
            return userService.getAllUser();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Person>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /*
     * @PostMapping(path = "/update")
     * public ResponseEntity<String> update(Person requestMap) {
     * try {
     * return userService.update(requestMap);
     * } catch (Exception ex) {
     * ex.printStackTrace();
     * }
     * return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG,
     * HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     */

}
