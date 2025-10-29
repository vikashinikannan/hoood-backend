package Hoodsignup.Hoodsignup.OAuth2;

import Hoodsignup.Hoodsignup.Entity.User;
import Hoodsignup.Hoodsignup.Repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        // SOCIAL SIGNUP STEP
        if (email != null && !userRepository.existsByEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setFullName(name);
            user.setPasswordHash("SOCIAL_SIGNUP"); // or null if preferred
            userRepository.save(user);
            System.out.println("Social signup: Created new user with email " + email);
        }

        return oAuth2User;
    }
}
