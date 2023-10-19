package com.example.samplesns.post.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeJpaRepository extends JpaRepository<PostLikeEntity, Long> {

    Long countByPostId(long postId);
}
