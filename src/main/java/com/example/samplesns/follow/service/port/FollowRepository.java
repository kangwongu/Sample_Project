package com.example.samplesns.follow.service.port;

import com.example.samplesns.follow.domain.Follow;

import java.util.Optional;

public interface FollowRepository {

    Follow save(Follow follow);

    Optional<Follow> findById(long followId);
}
