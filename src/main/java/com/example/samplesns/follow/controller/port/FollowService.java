package com.example.samplesns.follow.controller.port;

import com.example.samplesns.follow.dto.FollowRequest;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.dto.MemberResponse;

import java.util.List;

public interface FollowService {
    void follow(Member fromMember, FollowRequest request);
    List<MemberResponse> getFollowingMembers(Member fromMember);
}
