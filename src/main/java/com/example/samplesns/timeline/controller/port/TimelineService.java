package com.example.samplesns.timeline.controller.port;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.post.dto.PostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TimelineService {
    Slice<PostResponse> getTimelinesByPushModel(Member member, Pageable pageable);
    void deliveryTimeline(long postId, List<Long> toMemberIds);
}
