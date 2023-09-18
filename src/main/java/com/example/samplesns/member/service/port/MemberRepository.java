package com.example.samplesns.member.service.port;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByEmail(String email);

    Optional<Member> findById(long memberId);

    Member save(Member member);

    Optional<Member> findByEmailAndStatus(String email, MemberStatus status);
}
