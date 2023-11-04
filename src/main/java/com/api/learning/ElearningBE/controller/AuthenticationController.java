package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.TokenDetail;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.LoginForm;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.RoleRepository;
import com.api.learning.ElearningBE.security.JwtUtils;
import com.api.learning.ElearningBE.security.UserAuthenticationToken;
import com.api.learning.ElearningBE.security.impl.UserDetailsImpl;
import com.api.learning.ElearningBE.security.impl.UserDetailsServiceImpl;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    @Value("${google.verifyUrl}")
    private String googleVerifyUrl;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailService;
    private final JwtUtils jwtUtils;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailService, JwtUtils jwtUtils, AccountRepository accountRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }
    @PostMapping("/login")
    public ApiMessageDto<TokenDetail> login(@Valid @RequestBody LoginForm loginForm, @RequestParam Integer userKind){
        ApiMessageDto<TokenDetail> apiMessageDto = new ApiMessageDto<>();
        try {
            authenticationManager.authenticate(new UserAuthenticationToken(loginForm.getEmail(), loginForm.getPassword(), true, userKind));
            UserDetailsImpl userDetails = userDetailService.loadUserByUsername(loginForm.getEmail());
            TokenDetail tokenDetail = jwtUtils.getTokenDetail(userDetails);
            apiMessageDto.setData(tokenDetail);
        }catch (BadCredentialsException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            return apiMessageDto;
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("[Ex2]: "+e.getMessage());
        }
//        UserDetailsImpl userDetails = userDetailService.loadUserByUsername(loginForm.getEmail());
//        TokenDetail tokenDetail = jwtUtils.getTokenDetail(userDetails);
//        apiMessageDto.setData(tokenDetail);
        return apiMessageDto;
    }

    @PostMapping("/google")
    public ApiMessageDto<TokenDetail> googleLogin(@RequestParam(name = "accessToken") String accessToken){
        ApiMessageDto<TokenDetail> apiMessageDto = new ApiMessageDto<>();
        String url = googleVerifyUrl+accessToken;
        String email;
        String avatar;
        try {
            ResponseEntity<HashMap> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, HashMap.class);
            HashMap<String,String> map = responseEntity.getBody();
            email = map.get("email");
            avatar = map.get("picture");
            if (email != null){
                Account account = accountRepository.findByEmail(email);
                if (account == null){
                    Role role = roleRepository.findById(3L).orElseThrow(() -> new NotFoundException("Role not found"));
                    account = new Account();
                    account.setEmail(email);
                    account.setFullName("Test");
                    account.setAvatarPath(avatar);
                    account.setRole(role);
                    accountRepository.save(account);
                }
            }
            authenticationManager.authenticate(new UserAuthenticationToken(email, null, false, 1));
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
        System.out.println("Controller google: "+jwtUtils.getUserKindFromToken(jwtUtils.generateToken(userDetails)));
        UserDetailsImpl userDetailsTeacher = userDetailService.loadTeacherByEmail(email);
        System.out.println("Full name teacher google: "+ userDetailsTeacher.getFullName());
        System.out.println("Email teacher google: "+userDetailsTeacher.getEmail());
        System.out.println("Email teacher google kind: "+userDetailsTeacher.getUserKind());
        return apiMessageDto;
    }

//    @PostMapping("/login/google")
//    public ApiMessageDto<TokenDetail> googleLogin(@RequestParam(name = "kind") Integer kind,
//                                                  @RequestParam(name = "accessToken") String accessToken){
//        ApiMessageDto<TokenDetail> apiMessageDto = new ApiMessageDto<>();
//        String url = googleVerifyUrl+accessToken;
//        String email;
//        String avatar;
//        try {
//            ResponseEntity<HashMap> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, HashMap.class);
//            HashMap<String,String> map = responseEntity.getBody();
//            email = map.get("email");
//            avatar = map.get("picture");
//            if (email != null){
//                Account account = accountRepository.findByEmail(email);
//                if (account == null){
//
//                }
//            }
//            authenticationManager.authenticate(new UserAuthenticationToken(email, null, false));
//        }catch (InvalidException e){
//            apiMessageDto.setResult(false);
//            apiMessageDto.setMessage("Invalid token");
//            return apiMessageDto;
//        }catch (Exception e){
//            apiMessageDto.setResult(false);
//            apiMessageDto.setMessage("[Ex2]: "+e.getMessage());
//            return apiMessageDto;
//        }
//        UserDetailsImpl userDetails = userDetailService.loadUserByUsername(email);
//        TokenDetail tokenDetail = jwtUtils.getTokenDetail(userDetails);
//        tokenDetail.setAvatar(avatar);
//        apiMessageDto.setData(tokenDetail);
//        return apiMessageDto;
//    }
//
//    private void checkLogin(Integer kind, String email){
//        if (kind.equals(ELearningConstant.ROLE_KIND_TEACHER)){
//
//        }
//    }
}
