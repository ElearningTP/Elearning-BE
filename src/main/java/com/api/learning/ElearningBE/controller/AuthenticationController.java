package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.TokenDetail;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    @Value("${google.verifyUrl}")
    private String googleVerifyUrl;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsServiceImpl userDetailService;
    private final JwtUtils jwtUtils;
    private final RestTemplate restTemplate = new RestTemplate();

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailService, JwtUtils jwtUtils, AccountRepository accountRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }
    private void createAccount(String email, String fullName, String avatar, Role role){
        Account account = new Account();
        account.setEmail(email);
        account.setFullName(fullName);
        account.setAvatarPath(avatar);
        account.setKind(ELearningConstant.ACCOUNT_KIND_STUDENT);
        account.setLastLogin(new Date());
        account.setRole(role);
        accountRepository.save(account);
    }
    @PostMapping("/login")
    public ApiMessageDto<TokenDetail> login(@Valid @RequestBody LoginForm loginForm){
        ApiMessageDto<TokenDetail> apiMessageDto = new ApiMessageDto<>();
        try {
            authenticationManager.authenticate(new UserAuthenticationToken(loginForm.getEmail(), loginForm.getPassword(), true));

            Account account = accountRepository.findByEmail(loginForm.getEmail());
            account.setLastLogin(new Date());
            accountRepository.save(account);

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
        return apiMessageDto;
    }

    @PostMapping("/google")
    public ApiMessageDto<TokenDetail> googleLogin(@RequestParam(name = "accessToken") String accessToken){
        ApiMessageDto<TokenDetail> apiMessageDto = new ApiMessageDto<>();
        String url = googleVerifyUrl+accessToken;
        try {
            ResponseEntity<HashMap> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, HashMap.class);
            HashMap<String,String> map = responseEntity.getBody();
            String email = map.get("email");
            String avatar = map.get("picture");
            String fullName = map.get("name");
            if (email != null){
                Boolean existsAccount = accountRepository.existsAccountByEmail(email);
                if (!existsAccount){
                    final String nameRole = "Student";
                    Role role = roleRepository.findByName(nameRole);
                    createAccount(email, fullName, avatar, role);
                }
            }
            authenticationManager.authenticate(new UserAuthenticationToken(email, null, false));

            Account account = accountRepository.findByEmail(email);
            account.setLastLogin(new Date());
            account.setAvatarPath(avatar);
            accountRepository.save(account);

            UserDetailsImpl userDetails = userDetailService.loadUserByUsername(email);
            TokenDetail tokenDetail = jwtUtils.getTokenDetail(userDetails);
            tokenDetail.setAvatar(avatar);
            apiMessageDto.setData(tokenDetail);
        }catch (HttpClientErrorException e) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Invalid Credentials");
            apiMessageDto.setCode(e.getStatusCode().toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            return apiMessageDto;
        }
        return apiMessageDto;
    }
}
