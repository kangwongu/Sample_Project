package com.example.samplesns.follow.service;

import com.example.samplesns.follow.domain.Follow;
import com.example.samplesns.follow.dto.FollowRequest;
import com.example.samplesns.follow.service.port.FollowRepository;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.dto.MemberResponse;
import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.member.exception.status.MemberStatus;
import com.example.samplesns.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void follow(Member fromMember, FollowRequest request) {
        Member toMember = memberRepository.findByEmailAndStatus(request.getEmail(), com.example.samplesns.member.domain.MemberStatus.ACTIVE)
                .orElseThrow(() -> new MemberException(MemberStatus.NOT_EXIST_MEMBER));

        Follow follow = Follow.of(fromMember, toMember);
        followRepository.save(follow);
    }

    public List<MemberResponse> getFollowingMembers(Member fromMember) {
        List<Follow> follows = followRepository.findAllByfromMemberId(fromMember.getId());
        List<Long> followingMemberIds = follows.stream()
                .map(f -> f.getToMember().getId())
                .collect(Collectors.toList());

        return memberRepository.findAllByIds(followingMemberIds)
                .stream()
                .map(member -> MemberResponse.from(member))
                .collect(Collectors.toList());
    }

}
