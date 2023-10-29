package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.TokenDetail;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.form.LoginForm;
import com.api.learning.ElearningBE.security.JwtUtils;
import com.api.learning.ElearningBE.security.UserAuthenticationToken;
import com.api.learning.ElearningBE.security.impl.UserDetailsImpl;
import com.api.learning.ElearningBE.security.impl.UserDetailsServiceImpl;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public ApiMessageDto<TokenDetail> login(@Valid @RequestBody LoginForm loginForm){
        ApiMessageDto<TokenDetail> apiMessageDto = new ApiMessageDto<>();
        try {
            authenticationManager.authenticate(new UserAuthenticationToken(loginForm.getEmail(), loginForm.getPassword(), true));
        }catch (BadCredentialsException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            return apiMessageDto;
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("[Ex2]: "+e.getMessage());
        }
        UserDetailsImpl userDetails = userDetailService.loadUserByUsername(loginForm.getEmail());
        TokenDetail tokenDetail = jwtUtils.getTokenDetail(userDetails);
        apiMessageDto.setData(tokenDetail);
        return apiMessageDto;
    }

    @PostMapping("/google")
    public ApiMessageDto<TokenDetail> googleLogin(@RequestHeader(name = "accessToken") String accessToken){
        ApiMessageDto<TokenDetail> apiMessageDto = new ApiMessageDto<>();
        String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token="+accessToken;
        String email;
        String avatar;
        try {
            ResponseEntity<HashMap> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, HashMap.class);
            HashMap<String,String> map = responseEntity.getBody();
            email = map.get("email");
            avatar = map.get("picture");
            authenticationManager.authenticate(new UserAuthenticationToken(email, null, false));
        }catch (InvalidException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Invalid token");
            return apiMessageDto;
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("[Ex2]: "+e.getMessage());
            return apiMessageDto;
        }
        UserDetailsImpl userDetails = userDetailService.loadUserByUsername(email);
        TokenDetail tokenDetail = jwtUtils.getTokenDetail(userDetails);
        tokenDetail.setAvatar(avatar);
        apiMessageDto.setData(tokenDetail);
        return apiMessageDto;
    }
}
