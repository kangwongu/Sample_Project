package com.example.samplesns.follow.domain;

import com.example.samplesns.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Follow {

    private final Long id;
    private final Member fromMember;
    private final Member toMember;
    private final LocalDateTime createDate;

    @Builder
    public Follow(Long id, Member fromMember, Member toMember, LocalDateTime createDate) {
        this.id = id;
        this.fromMember = Objects.requireNonNull(fromMember);
        this.toMember = Objects.requireNonNull(toMember);
        this.createDate = createDate;
    }

}
