package com.example.samplesns.post.infrastructure;

import com.example.samplesns.common.Timestamp;
import com.example.samplesns.common.util.BooleanToYNConverter;
import com.example.samplesns.member.infrastructure.MemberEntity;
import com.example.samplesns.post.domain.Post;
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
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private String title;

    private String contents;

    private Long likeCount;

    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isDelete;

    public static PostEntity from(Post post) {
        PostEntity postEntity = new PostEntity();
        postEntity.id = post.getId();
        postEntity.member = MemberEntity.from(post.getMember());
        postEntity.title = post.getTitle();
        postEntity.contents = post.getContents();
        postEntity.likeCount = post.getLikeCount();
        postEntity.isDelete = post.getIsDelete();

        return postEntity;
    }

    public Post toModel() {
        return Post.builder()
                .id(id)
                .member(member.toModel())
                .title(title)
                .contents(contents)
                .likeCount(likeCount)
                .isDelete(isDelete)
                .createDate(getCreateDate())
                .modifyDate(getModifyDate())
                .build();
    }

}
