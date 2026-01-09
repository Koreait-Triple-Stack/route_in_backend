package triple_stack.route_in_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import triple_stack.route_in_backend.dto.user.SigninReqDto;
import triple_stack.route_in_backend.dto.user.SignupReqDto;
import triple_stack.route_in_backend.service.OAuth2AuthService;

@RestController
public class OAuth2AuthController {
    @Autowired
    private OAuth2AuthService oAuth2AuthService;

    @PostMapping("/oauth2/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) throws JsonProcessingException {
        return ResponseEntity.ok(oAuth2AuthService.signup(signupReqDto));
    }

    @GetMapping("/oauth2/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok(oAuth2AuthService.signin(signinReqDto));
    }
}
