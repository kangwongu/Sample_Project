package com.example.samplesns.member.controller;

import com.example.samplesns.common.exception.response.ExceptionResponse;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.dto.LoginRequest;
import com.example.samplesns.member.dto.RegisterRequest;
import com.example.samplesns.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@Tag(name = "Member", description = "Member API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                content = {@Content(schema = @Schema(implementation = Member.class))}),
            @ApiResponse(responseCode = "400", description = "비밀번호 불일치",
                content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "409", description = "중복된 이메일 존재")
    })
    public ResponseEntity<Member> register(@RequestBody @Valid RegisterRequest request) {
        Member member = memberService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/{id}/verify")
    @Operation(summary = "이메일 인증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증성공"),
            @ApiResponse(responseCode = "404", description = "해당 Id의 회원 존재 x")
    })
    public ResponseEntity<Void> verifyEmail(
            @Valid
            @Parameter(description = "Member Id", required = true)
            @PathVariable
            @Positive
            long id,

            @Parameter(description = "회원가입 시, 부여된 코드", required = true)
            @RequestParam
            String certificationCode
    ) {
        memberService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000"))  // 가상의 프론트 서버
                .build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호 불일치"),
            @ApiResponse(responseCode = "404", description = "해당 email의 회원 존재 x")
    })
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request) {
        HttpHeaders response = memberService.login(request);

        return ResponseEntity.ok().headers(response).build();
    }
}
