package com.example.samplesns.post.infrastructure;

import com.example.samplesns.post.dto.DailyPostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

    @Query("select p from PostEntity p " +
            "where p.id in :postIds")
    Slice<PostEntity> findAllByIdIn(@Param("postIds") List<Long> postIds,
                              Pageable pageable);

    @Query("select new com.example.samplesns.post.dto.DailyPostResponse(p.member.email, p.member.nickname, DATE(p.createDate), count(p.id)) " +
                "from PostEntity p " +
                "where p.member.id = :memberId " +
                "and DATE(p.createDate) between :firstDate and :lastDate " +
                "group by p.member.email, p.member.nickname, DATE(p.createDate)")
    Slice<DailyPostResponse> groupByCreateDate(@Param("memberId") long memberId,
                                               @Param("firstDate") Date firstDate,
                                               @Param("lastDate") Date lastDate,
                                               Pageable pageable);

    Slice<PostEntity> findAllByMemberIdOrderByCreateDateDesc(long memberId, Pageable pageable);

    Slice<PostEntity> findAllByMemberIdInOrderByCreateDateDesc(List<Long> memberIds, Pageable pageable);

    @Lock(LockModeType.OPTIMISTIC)
    Optional<PostEntity> findById(long postId);
}
