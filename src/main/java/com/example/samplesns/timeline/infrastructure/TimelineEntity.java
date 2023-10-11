package com.example.samplesns.timeline.infrastructure;

import com.example.samplesns.common.CreateTimestamp;
import com.example.samplesns.member.infrastructure.MemberEntity;
import com.example.samplesns.post.infrastructure.PostEntity;
import com.example.samplesns.timeline.domain.Timeline;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "timeline")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimelineEntity extends CreateTimestamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public static TimelineEntity from(Timeline timeline) {
        TimelineEntity timelineEntity = new TimelineEntity();
        timelineEntity.id = timeline.getId();
        timelineEntity.member = MemberEntity.from(timeline.getMember());
        timelineEntity.post = PostEntity.from(timeline.getPost());

        return timelineEntity;
    }

    public Timeline toModel() {
        return Timeline.builder()
                .id(id)
                .member(member.toModel())
                .post(post.toModel())
                .createDate(getCreateDate())
                .build();
    }
}
