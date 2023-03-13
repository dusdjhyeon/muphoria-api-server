package com.hyuns.muphoria.api.global.config.oauth2;

import com.hyuns.muphoria.api.domain.user.Role;
import com.hyuns.muphoria.api.domain.user.User;
import com.hyuns.muphoria.api.domain.user.repository.UserRepository;
import com.hyuns.muphoria.api.global.config.security.Token;
import com.hyuns.muphoria.api.global.config.security.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Value("${website.url}")
    private String websiteURL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();  // CustomOAuth2UserService에서 넘어옴.
        String email = (String) oAuth2User.getAttribute("email");// 그냥 email로 비교 할 예정
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getRole() == Role.USER) {
                Token token = tokenService.generateToken(String.valueOf(user.getId()), Role.USER.getKey());
                ResponseCookie cookie = ResponseCookie.from("accessToken", token.getToken())
                        .maxAge(7 * 24 * 60 * 60)
                        .domain(".ce.khu.ac.kr/ce/user/main/view.do")
                        .path("/")
                        .httpOnly(true)
                        .build();

                response.setHeader("Set-Cookie", cookie.toString());
                getRedirectStrategy().sendRedirect(request, response, websiteURL);
            } else if (user.getRole() == Role.GUEST) {
                Token token = tokenService.generateToken(String.valueOf(user.getId()), Role.USER.getKey());
                ResponseCookie cookie = ResponseCookie.from("accessToken", token.getToken())
                        .maxAge(7 * 24 * 60 * 60)
                        .domain(".ce.khu.ac.kr/ce/user/main/view.do")
                        .path("/")
                        .httpOnly(true)
                        .build();
                response.setHeader("Set-Cookie", cookie.toString());
                getRedirectStrategy().sendRedirect(request, response, websiteURL + "/user/register");
            } else {
                getRedirectStrategy().sendRedirect(request, response, websiteURL + "/user/banned");
            }
        } else {
            User user = userRepository.save(
                    User.builder()
                            .role(Role.GUEST)
                            .name("Guest")
                            .name((String) oAuth2User.getAttribute("name"))
                            .profileImage((String) oAuth2User.getAttribute("picture"))
                            .build()
            );

            Token token = tokenService.generateToken(String.valueOf(user.getId()), Role.USER.getKey());
            ResponseCookie cookie = ResponseCookie.from("accessToken", token.getToken())
                    .maxAge(7 * 24 * 60 * 60)
                    .path("/")
                    .httpOnly(true)
                    .build();

            response.setHeader("Set-Cookie", cookie.toString());
            getRedirectStrategy().sendRedirect(request, response, websiteURL + "/user/register");
        }
    }
}
