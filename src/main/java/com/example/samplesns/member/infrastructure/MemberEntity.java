package com.example.samplesns.member.infrastructure;

import com.example.samplesns.common.Timestamp;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private LocalDate birthday;
    private String certificationCode;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public static MemberEntity from(Member member) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.id = member.getId();
        memberEntity.email = member.getEmail();
        memberEntity.password = member.getPassword();
        memberEntity.nickname = member.getNickname();
        memberEntity.birthday = member.getBirthday();
        memberEntity.certificationCode = member.getCertificationCode();
        memberEntity.status = member.getStatus();

        return memberEntity;
    }

    public Member toModel() {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .birthday(birthday)
                .certificationCode(certificationCode)
                .status(status)
                .createDate(getCreateDate())
                .modifyDate(getModifyDate())
                .build();
    }
}
