package com.example.samplesns.member.infrastructure;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Member getByEmail(String email) {
        return memberJpaRepository.findByEmail(email)
                .map(memberEntity -> memberEntity.toModel())
                .orElseThrow(() -> new MemberException(com.example.samplesns.member.exception.status.MemberStatus.NOT_EXIST_MEMBER));
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

    @Override
    public List<Member> findAllByIds(List<Long> memberIds) {
        return memberJpaRepository.findAllByIdIn(memberIds)
                .stream()
                .map(memberEntity -> memberEntity.toModel())
                .collect(Collectors.toList());
    }

}
