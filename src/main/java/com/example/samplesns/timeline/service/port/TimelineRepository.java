package com.example.samplesns.timeline.service.port;

import com.example.samplesns.timeline.domain.Timeline;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimelineRepository {
    Timeline save(Timeline timeline);

    @Query("select t from TimelineEntity t " +
            "join fetch t.member m " +
            "join fetch t.post p " +
            "where t.member.id = :memberId")
    Slice<Timeline> getTimelines(@Param("memberId") long memberId,
                                 Pageable pageable);

    void saveAllByBulk(List<Timeline> timelines);
}
