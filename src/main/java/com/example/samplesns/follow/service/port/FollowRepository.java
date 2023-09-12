package com.example.samplesns.follow.service.port;

import com.example.samplesns.follow.domain.Follow;
import com.example.samplesns.follow.infrastructure.FollowEntity;

public interface FollowRepository {

    Follow save(Follow follow);
}
