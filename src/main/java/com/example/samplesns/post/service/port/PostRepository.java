package com.example.samplesns.post.service.port;

import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.DailyPostResponse;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository {

    Post save(Post post);

    Post getById(long postId);

    List<DailyPostResponse> groupByCreateDate(long memberId, LocalDate firstDate, LocalDate lastDate);
}
