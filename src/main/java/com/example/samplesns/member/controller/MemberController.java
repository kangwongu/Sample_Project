package com.example.samplesns.member.controller;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.dto.RegisterRequest;
import com.example.samplesns.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(@PathVariable long id,
                                            @RequestParam String certificationCode) {
        memberService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000"))  // 가상의 프론트 서버
                .build();
    }

}
