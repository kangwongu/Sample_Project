package com.example.samplesns.follow.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {
    @Query("select f from FollowEntity f " +
            "join fetch f.fromMember fm " +
            "join fetch f.toMember tm " +
            "where fm.id = :fromMemberId")
    List<FollowEntity> findAllByFromMemberId(@Param("fromMemberId") long fromMemberId);

    @Query("select f from FollowEntity f " +
            "join fetch f.fromMember fm " +
            "join fetch f.toMember tm " +
            "where f.toMember.id = :toMemberId")
    List<FollowEntity> findAllByToMemberId(@Param("toMemberId") long toMemberId);
}
