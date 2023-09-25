package com.example.samplesns.post.service.port;

import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.DailyPostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;

public interface PostRepository {

    Post save(Post post);

    Post getById(long postId);

    Slice<DailyPostResponse> groupByCreateDate(long memberId, LocalDate firstDate, LocalDate lastDate, Pageable pageable);

    Slice<Post> getMyPosts(long memberId, Pageable pageable);
}
