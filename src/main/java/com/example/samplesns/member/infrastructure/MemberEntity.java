package com.example.samplesns.member.infrastructure;

import com.example.samplesns.common.Timestamp;
import com.example.samplesns.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nickname;
    private LocalDate birthday;

    public Member toModel() {
        return Member.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .birthday(birthday)
                .createDate(getCreateDate())
                .modifyDate(getModifyDate())
                .build();
    }
}
