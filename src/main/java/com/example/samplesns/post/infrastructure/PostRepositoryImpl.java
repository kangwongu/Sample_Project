package com.example.samplesns.post.infrastructure;

import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.DailyPostResponse;
import com.example.samplesns.post.exception.PostException;
import com.example.samplesns.post.exception.status.PostStatus;
import com.example.samplesns.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;
//    private final JdbcTemplate jdbcTemplate;

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toModel();
    }

    @Override
    public Post getById(long postId) {
        return postJpaRepository.findById(postId)
                .map(postEntity -> postEntity.toModel())
                .orElseThrow(() -> new PostException(PostStatus.NOT_EXIST_POST));
    }

    @Override
    public Slice<Post> findAllByIds(List<Long> postIds, Pageable pageable) {
        return postJpaRepository.findAllByIdIn(postIds, pageable)
                .map(postEntity -> postEntity.toModel());
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

//    @Override
//    public Slice<Post> getTimelines(List<Long> memberIds, Pageable pageable) {
//        return postJpaRepository.findAllByMemberIdInOrderByCreateDateDesc(memberIds, pageable)
//                .map(postEntity -> postEntity.toModel());
//    }

//    @Override
//    public void saveAllByBulk(long memberId, List<Post> posts) {
//        String sql = "INSERT INTO post (member_id, title, contents, create_date, modify_date) " +
//                "VALUES (?, ?, ?, ?, ?)";
//
//        jdbcTemplate.batchUpdate(sql,
//                posts,
//                posts.size(),
//                (PreparedStatement ps, Post p) -> {
//                    ps.setLong(1, memberId);
//                    ps.setString(2, p.getTitle());
//                    ps.setString(3, p.getContents());
//                    ps.setTimestamp(4, Timestamp.valueOf(p.getCreateDate()));
//                    ps.setTimestamp(5, Timestamp.valueOf(p.getCreateDate()));
//                });
//    }

}
