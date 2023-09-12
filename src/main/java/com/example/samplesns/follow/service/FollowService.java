package com.example.samplesns.follow.service;

import com.example.samplesns.follow.domain.Follow;
import com.example.samplesns.follow.dto.FollowRequest;
import com.example.samplesns.follow.service.port.FollowRepository;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.member.exception.status.MemberStatus;
import com.example.samplesns.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public void follow(Member fromMember, FollowRequest request) {
//        Member fromMember = memberRepository.findById(fromMemberId)
//                .orElseThrow(() -> new MemberException(MemberStatus.NOT_EXIST_MEMBER));

        Member toMember = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberException(MemberStatus.NOT_EXIST_MEMBER));

        Follow follow = Follow.of(fromMember, toMember);
        followRepository.save(follow);
    }

}
