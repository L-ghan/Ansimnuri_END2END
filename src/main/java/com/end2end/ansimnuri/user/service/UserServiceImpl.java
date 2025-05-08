package com.end2end.ansimnuri.user.service;

import com.end2end.ansimnuri.user.dao.UserDAO;
import com.end2end.ansimnuri.user.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final MemberRepository memberRepository;
}
