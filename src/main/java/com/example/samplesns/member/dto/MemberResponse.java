package com.example.samplesns.member.dto;

import com.example.samplesns.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class MemberResponse {
    private String email;
    private String nickname;
    private LocalDate birthday;

    public static MemberResponse from(Member member) {
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setEmail(member.getEmail());
        memberResponse.setNickname(member.getNickname());
        memberResponse.setBirthday(member.getBirthday());

        return memberResponse;
    }
}
