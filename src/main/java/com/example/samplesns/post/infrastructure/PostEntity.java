package com.example.samplesns.post.infrastructure;

import com.example.samplesns.common.Timestamp;
import com.example.samplesns.member.infrastructure.MemberEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "member_id")
    private MemberEntity member;

    private String title;

    private String contents;

}
