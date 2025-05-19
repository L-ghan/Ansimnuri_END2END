package com.end2end.ansimnuri.util.config;

import com.end2end.ansimnuri.member.dto.LoginResultDTO;
import com.end2end.ansimnuri.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;

    public OAuth2LoginSuccessHandler(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String kakaoId = oAuth2User.getAttribute("id").toString();
        String nickname = (String) ((Map<String, Object>) oAuth2User.getAttribute("properties")).get("nickname");

        try {
            LoginResultDTO result = memberService.registerOAuthIfNeeded(kakaoId, nickname);
            String token = result.getToken();
            String redirectUrl = "http://localhost:3000/login/oauth2/redirect?token=" + token + "&id=" + kakaoId;
            response.sendRedirect(redirectUrl);
        } catch (IllegalArgumentException e) {
            // 회원이 없는 경우, 프론트에서 alert 창 띄우고 로그인 페이지로 이동하도록 설정
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(
                    "<script>alert('회원이 존재하지 않습니다. 회원가입 페이지로 이동합니다.');" +
                            "window.location.href='http://localhost:3000/RegisterPage';</script>"
            );
        }
    }
}

