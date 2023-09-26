package com.example.samplesns.post.infrastructure;

import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.DailyPostResponse;
import com.example.samplesns.post.exception.PostException;
import com.example.samplesns.post.exception.status.PostStatus;
import com.example.samplesns.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toModel();
    }

    @Override
    public Post getById(long postId) {
        throw new PostException(PostStatus.NOT_EXIST_POST);
    }

    @Override
    public Slice<DailyPostResponse> groupByCreateDate(long memberId, LocalDate firstDate, LocalDate lastDate, Pageable pageable) {
        return postJpaRepository.groupByCreateDate(memberId, Date.valueOf(firstDate), Date.valueOf(lastDate), pageable);
    }

    @Override
    public Slice<Post> getMemberPosts(long memberId, Pageable pageable) {
        return postJpaRepository.findAllByMemberIdOrderByCreateDateDesc(memberId, pageable)
                .map(postEntity -> postEntity.toModel());
    }

}
