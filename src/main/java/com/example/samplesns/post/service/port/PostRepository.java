package com.example.samplesns.post.service.port;

import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.DailyPostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository {

    Post save(Post post);

    Post getById(long postId);

    Slice<Post> findAllByIds(List<Long> postIds, Pageable pageable);

    Slice<DailyPostResponse> groupByCreateDate(long memberId, LocalDate firstDate, LocalDate lastDate, Pageable pageable);

    Slice<Post> getMemberPosts(long memberId, Pageable pageable);

//    Slice<Post> getTimelines(List<Long> memberIds, Pageable pageable);

//    void saveAllByBulk(long memberId, List<Post> posts);
}
