package com.example.samplesns.post.infrastructure;

import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.DailyPostResponse;
import com.example.samplesns.post.exception.PostException;
import com.example.samplesns.post.exception.status.PostStatus;
import com.example.samplesns.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
    public List<DailyPostResponse> groupByCreateDate(long memberId, LocalDate firstDate, LocalDate lastDate) {
        return postJpaRepository.groupByCreateDate(memberId, Date.valueOf(firstDate), Date.valueOf(lastDate));
    }
}
