package triple_stack.route_in_backend.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OAuth2PrincipalService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String provider=  userRequest.getClientRegistration().getRegistrationId();
        String providerUserId = null;

        switch (provider) {
            case "google":
                providerUserId = attributes.get("sub").toString();
                break;
            case "naver":
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                providerUserId = response.get("id").toString();
                break;
        }

        Map<String, Object> newAttributes = Map.of(
                "providerUserId", providerUserId,
                "provider", provider
        );

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new DefaultOAuth2User(authorities, newAttributes, "providerUserId");
    }
}
