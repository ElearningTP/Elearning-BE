package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.TokenDetail;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.form.LoginForm;
import com.api.learning.ElearningBE.security.JwtUtils;
import com.api.learning.ElearningBE.security.UserAuthenticationToken;
import com.api.learning.ElearningBE.security.impl.UserDetailsImpl;
import com.api.learning.ElearningBE.security.impl.UserDetailsServiceImpl;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailService;
    private final JwtUtils jwtUtils;
    private final RestTemplate restTemplate = new RestTemplate();

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDetail> login(@Valid @RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(new UserAuthenticationToken(loginForm.getEmail(), loginForm.getPassword(), true));
        UserDetailsImpl userDetails = userDetailService.loadUserByUsername(loginForm.getEmail());
        String jwt = jwtUtils.generateToken(userDetails);
        return new ResponseEntity<>(new TokenDetail(userDetails.getFullName(),jwt), HttpStatus.OK);
    }

    @PostMapping("/google")
    public ResponseEntity<TokenDetail> googleLogin(@RequestHeader(name = "accessToken") String accessToken){
        String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token="+accessToken;
        String email;
        try {
            ResponseEntity<HashMap> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, HashMap.class);
            HashMap<String,String> map = responseEntity.getBody();
            email = map.get("email");
        }catch (Exception e){
            throw new InvalidException("Invalid token");
        }
        Authentication authentication = authenticationManager.authenticate(new UserAuthenticationToken(email, null, false));
        UserDetailsImpl userDetails = userDetailService.loadUserByUsername(email);
        String jwt = jwtUtils.generateToken(userDetails);
        return new ResponseEntity<>(new TokenDetail(userDetails.getFullName(),jwt), HttpStatus.OK);
    }

//    @PostMapping("/google")
//    public ResponseEntity<String> exchangeCodeForAccessToken(@RequestParam("code") String code, @RequestParam("redirect_uri") String redirectUri) {
//        RestTemplate restTemplate = new RestTemplate();
//        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
//        request.add("code", code);
//        request.add("client_id", "");
//        request.add("client_secret", "");
//        request.add("redirect_uri", redirectUri);
//        request.add("grant_type", "authorization_code");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(request, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange("https://accounts.google.com/o/oauth2/token", HttpMethod.POST, entity, String.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            // Lấy "access token" từ response
//            String accessToken = response.getBody();
//            return new ResponseEntity<>(accessToken, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Failed to exchange code for access token", HttpStatus.BAD_REQUEST);
//        }
//    }
}
