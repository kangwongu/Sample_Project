package com.example.samplesns.post.service;

import com.example.samplesns.follow.service.port.FollowRepository;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.post.dto.PostResponse;
import com.example.samplesns.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimelineService {

    private final FollowRepository followRepository;
    private final PostRepository postRepository;

    public Slice<PostResponse> getTimelines(Member member, Pageable pageable) {
        List<Long> followingMemberIds = followRepository.findAllByfromMemberId(member.getId()).stream()
                .map(f -> f.getToMember().getId())
                .collect(Collectors.toList());

        return postRepository.getTimelines(followingMemberIds, pageable).map(p -> PostResponse.from(p));

    }
}
