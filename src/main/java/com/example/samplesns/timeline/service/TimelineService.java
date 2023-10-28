package com.example.samplesns.timeline.service;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.service.port.MemberRepository;
import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.PostResponse;
import com.example.samplesns.post.service.port.PostLikeRepository;
import com.example.samplesns.post.service.port.PostRepository;
import com.example.samplesns.timeline.domain.Timeline;
import com.example.samplesns.timeline.service.port.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimelineService {

//    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TimelineRepository timelineRepository;
    private final PostLikeRepository postLikeRepository;

    // pull model
//    public Slice<PostResponse> getTimelinesByPullModel(Member member, Pageable pageable) {
//        List<Long> followingMemberIds = followRepository.findAllByfromMemberId(member.getId()).stream()
//                .map(f -> f.getToMember().getId())
//                .collect(Collectors.toList());
//
//        return postRepository.getTimelines(followingMemberIds, pageable).map(p -> PostResponse.from(p));
//
//    }

    // push model
    public Slice<PostResponse> getTimelinesByPushModel(Member member, Pageable pageable) {
        Slice<Timeline> timelines = timelineRepository.getTimelines(member.getId(), pageable);
        List<Long> postIds = timelines.getContent().stream()
                .map(t -> t.getPost().getId())
                .collect(Collectors.toList());
        return postRepository.findAllByIds(postIds, pageable)
                .map(post -> PostResponse.of(post, postLikeRepository.count(post.getId())));
    }

    @Transactional
    public void deliveryTimeline(long postId, List<Long> toMemberIds) {
        Post post = postRepository.getById(postId);
        List<Timeline> timelines = toMemberIds.stream()
                .map(m -> toTimeline(post, m))
                .collect(Collectors.toList());

        timelineRepository.saveAllByBulk(timelines);
    }

    private Timeline toTimeline(Post post, Long memberId) {
        Member member = memberRepository.getById(memberId);
        return Timeline.of(member, post);
    }
}
