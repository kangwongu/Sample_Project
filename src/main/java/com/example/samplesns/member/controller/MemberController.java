package com.example.samplesns.member.controller;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.dto.RegisterRequest;
import com.example.samplesns.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Member> register(@RequestBody RegisterRequest request) {
        Member member = memberService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

}
