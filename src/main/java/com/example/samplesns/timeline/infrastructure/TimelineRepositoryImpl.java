package com.example.samplesns.timeline.infrastructure;

import com.example.samplesns.timeline.domain.Timeline;
import com.example.samplesns.timeline.service.port.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimelineRepositoryImpl implements TimelineRepository {

    private final TimelineJpaRepository timelineJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Timeline save(Timeline timeline) {
        return timelineJpaRepository.save(TimelineEntity.from(timeline)).toModel();
    }

    @Override
    public Slice<Timeline> getTimelines(long memberId, Pageable pageable) {
        return timelineJpaRepository.findAllByMemberIdOrderByCreateDateDesc(memberId, pageable)
                .map(timelineEntity -> timelineEntity.toModel());
    }

    @Override
    public void saveAllByBulk(List<Timeline> timelines) {
        String sql = "INSERT INTO timeline (member_id, post_id) " +
                "VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql,
                timelines,
                timelines.size(),
                (PreparedStatement ps, Timeline t) -> {
                    ps.setLong(1, t.getMember().getId());
                    ps.setLong(2, t.getPost().getId());
                });
    }
}
