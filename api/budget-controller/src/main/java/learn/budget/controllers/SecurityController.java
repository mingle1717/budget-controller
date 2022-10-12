package learn.budget.controllers;

import learn.budget.domain.Result;
import learn.budget.models.AppUser;
import learn.budget.models.viewmodels.LoginRequest;
import learn.budget.models.viewmodels.RegisterRequest;
import learn.budget.security.AppUserService;
import learn.budget.security.JwtConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/security")
@CrossOrigin
public class SecurityController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtConverter converter;

    @Autowired
    AppUserService service;



    @PostMapping
    ResponseEntity login(@RequestBody LoginRequest request){

        UsernamePasswordAuthenticationToken rawToken
                = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication authentication = authManager.authenticate( rawToken );

        AppUser user = (AppUser)authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = converter.buildJwt(user);

        HashMap<String, String> tokenHolder = new HashMap<>();
        tokenHolder.put("jwt_token", token);

        return ResponseEntity.ok( tokenHolder );
    }

    @PostMapping("/register")
    ResponseEntity<AppUser> register(@RequestBody RegisterRequest request) {
        Result<AppUser> newUser = service.register(request);
        return ResponseEntity.ok(newUser.getPayload());
    }
}
