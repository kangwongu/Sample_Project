package com.example.samplesns.member.infrastructure;

import com.example.samplesns.member.domain.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);

    Optional<MemberEntity> findByEmailAndStatus(String email, MemberStatus status);
}
