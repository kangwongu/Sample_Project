package com.example.samplesns.follow.infrastructure;

import com.example.samplesns.common.CreateTimestamp;
import com.example.samplesns.follow.domain.Follow;
import com.example.samplesns.member.infrastructure.MemberEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowEntity extends CreateTimestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id")
    private MemberEntity fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id")
    private MemberEntity toMember;

    public static FollowEntity from(Follow follow) {
        FollowEntity followEntity = new FollowEntity();
        followEntity.id = follow.getId();
        followEntity.fromMember = MemberEntity.from(follow.getFromMember());
        followEntity.toMember = MemberEntity.from(follow.getToMember());

        return followEntity;
    }

    public Follow toModel() {
        return Follow.builder()
                .id(id)
                .fromMember(fromMember.toModel())
                .toMember(toMember.toModel())
                .createDate(getCreateDate())
                .build();
    }
}
