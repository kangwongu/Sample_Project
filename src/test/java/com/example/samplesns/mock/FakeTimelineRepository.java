package com.example.samplesns.mock;

import com.example.samplesns.timeline.domain.Timeline;
import com.example.samplesns.timeline.service.port.TimelineRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public class FakeTimelineRepository implements TimelineRepository {
    @Override
    public Timeline save(Timeline timeline) {
        return null;
    }

    @Override
    public Slice<Timeline> getTimelines(long memberId, Pageable pageable) {
        return null;
    }

    @Override
    public void saveAllByBulk(List<Timeline> timelines) {

    }
}
