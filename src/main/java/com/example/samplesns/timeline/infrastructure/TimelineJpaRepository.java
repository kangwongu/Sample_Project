package com.example.samplesns.timeline.infrastructure;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineJpaRepository extends JpaRepository<TimelineEntity, Long> {
    Slice<TimelineEntity> findAllByMemberIdOrderByCreateDateDesc(long memberId, Pageable pageable);

}
