package com.revature.disseminator.project.controller;

import com.revature.disseminator.project.dto.AuthenticationResponse;
import com.revature.disseminator.project.dto.LoginRequest;
import com.revature.disseminator.project.dto.RefreshTokenRequest;
import com.revature.disseminator.project.dto.RegisterRequest;
import com.revature.disseminator.project.service.AuthService;
import com.revature.disseminator.project.service.RefreshTokenService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
//@Slf4j
@AllArgsConstructor
public class AuthController {
	//private  static final Logger logger= LoggerFactory.getLogger(AuthController.class);
	//private static final Logger LOGGER = (Logger) LogManager.getLogger(AuthController.class);
	
	//logger.info("starting controller");
	//LOGGER.info("kjfdjksdfhjkkjdfs");
	//log.info("stattsysy");
	 private static final Logger LOGGER= LoggerFactory.getLogger(AuthController.class);
	

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        LOGGER.info("User Registration Successful");
        return new ResponseEntity<>("User Registration Successful",
                OK);
       
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        LOGGER.info("Account Activated Successfully");
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
    	LOGGER.info("Requesting to login");
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        LOGGER.warn("logging out");
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
