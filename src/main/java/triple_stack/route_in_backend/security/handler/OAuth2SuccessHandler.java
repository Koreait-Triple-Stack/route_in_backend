package triple_stack.route_in_backend.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import triple_stack.route_in_backend.entity.User;
import triple_stack.route_in_backend.repository.UserRepository;
import triple_stack.route_in_backend.security.jwt.JwtUtils;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String provider = defaultOAuth2User.getAttribute("provider");
        String providerUserId = defaultOAuth2User.getAttribute("providerUserId");

        Optional<User> foundUser = userRepository.getUserByProviderAndProviderUserId(provider, providerUserId);

        if (foundUser.isEmpty()) {
            response.sendRedirect("http://localhost:5173/auth/oauth2?provider="+provider+"&providerUserId="+providerUserId);
            return;
        }

        String accessToken = jwtUtils.generateAccessToken(foundUser.get().getUserId().toString());

        response.sendRedirect("http://localhost:5173/auth/oauth2/signin?accessToken="+accessToken);
    }
}
