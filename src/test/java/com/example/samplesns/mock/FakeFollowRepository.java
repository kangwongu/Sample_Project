package com.example.samplesns.mock;

import com.example.samplesns.follow.domain.Follow;
import com.example.samplesns.follow.service.port.FollowRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeFollowRepository implements FollowRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Follow> data = new ArrayList<>();

    @Override
    public Follow save(Follow follow) {
        if (follow.getId() == null || follow.getId() == 0) {
            Follow newFollow = Follow.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .fromMember(follow.getFromMember())
                    .toMember(follow.getToMember())
                    .createDate(follow.getCreateDate())
                    .build();
            data.add(newFollow);
            return newFollow;
        }
        data.removeIf(d -> Objects.equals(d.getId(), follow.getId()));
        data.add(follow);
        return follow;
    }

    public Optional<Follow> findById(long followId) {
        return data.stream().filter(d -> d.getId().equals(followId)).findAny();
    }

    @Override
    public List<Follow> findAllByfromMemberId(long fromMemberId) {
        return data.stream().filter(d -> d.getFromMember().getId().equals(fromMemberId)).collect(Collectors.toList());
    }
}
