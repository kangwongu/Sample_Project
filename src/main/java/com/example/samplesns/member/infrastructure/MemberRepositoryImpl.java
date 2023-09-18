package com.example.samplesns.member.infrastructure;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import com.example.samplesns.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email)
                .map(memberEntity -> memberEntity.toModel());
    }

    @Override
    public Optional<Member> findById(long memberId) {
        return memberJpaRepository.findById(memberId)
                .map(memberEntity -> memberEntity.toModel());
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(MemberEntity.from(member)).toModel();
    }

    @Override
    public Optional<Member> findByEmailAndStatus(String email, MemberStatus status) {
        return memberJpaRepository.findByEmailAndStatus(email, status)
                .map(memberEntity -> memberEntity.toModel());
    }

}
