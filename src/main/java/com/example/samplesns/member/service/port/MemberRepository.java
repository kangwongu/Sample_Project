package com.example.samplesns.member.service.port;

import com.example.samplesns.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByEmail(String email);
}
