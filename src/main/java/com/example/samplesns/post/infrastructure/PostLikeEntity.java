package com.example.samplesns.post.infrastructure;

import com.example.samplesns.common.CreateTimestamp;
import com.example.samplesns.member.infrastructure.MemberEntity;
import com.example.samplesns.post.domain.PostLike;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeEntity extends CreateTimestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    public static PostLikeEntity from(PostLike postLike) {
        PostLikeEntity postLikeEntity = new PostLikeEntity();
        postLikeEntity.id = postLike.getId();
        postLikeEntity.post = PostEntity.from(postLike.getPost());
        postLikeEntity.member = MemberEntity.from(postLike.getMember());

        return postLikeEntity;
    }

    public PostLike toModel() {
        return PostLike.builder()
                .id(id)
                .post(post.toModel())
                .member(member.toModel())
                .createDate(getCreateDate())
                .build();
    }

}
