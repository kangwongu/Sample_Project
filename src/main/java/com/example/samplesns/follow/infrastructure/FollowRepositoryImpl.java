package com.example.samplesns.follow.infrastructure;

import com.example.samplesns.follow.domain.Follow;
import com.example.samplesns.follow.service.port.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepository {

    private final FollowJpaRepository followJpaRepository;

    @Override
    public Follow save(Follow follow) {
        return followJpaRepository.save(FollowEntity.from(follow)).toModel();
    }
}
