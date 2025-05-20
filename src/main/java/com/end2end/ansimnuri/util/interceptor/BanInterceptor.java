package com.end2end.ansimnuri.util.interceptor;

import com.end2end.ansimnuri.admin.domain.entity.Block;
import com.end2end.ansimnuri.admin.domain.repository.BlockRepository;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.util.exception.BanUserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class BanInterceptor implements HandlerInterceptor {
    private final MemberRepository memberRepository;
    private final BlockRepository blockRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginId = (String) request.getAttribute("loginId");
        if (loginId == null) {
            return true;
        }

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 로그인 아이디는 존재하지 앖습니다."));
        Block block = blockRepository.findByMember(member);

        if (block == null) {
            return true;
        }

        throw new BanUserException(member.getNickname(), block.getReason(), block.getEndDate());
    }
}
