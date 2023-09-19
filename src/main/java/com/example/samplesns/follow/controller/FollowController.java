package com.example.samplesns.follow.controller;

import com.example.samplesns.common.exception.response.ExceptionResponse;
import com.example.samplesns.common.security.userdetails.UserDetailsImpl;
import com.example.samplesns.follow.dto.FollowRequest;
import com.example.samplesns.follow.service.FollowService;
import com.example.samplesns.member.dto.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Follow", description = "Follow API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("")
    @Operation(summary = "팔로우 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로우 성공"),
            @ApiResponse(responseCode = "404", description = "팔로우 하려는 Id의 회원 존재 x",
                    content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
    })
    public ResponseEntity<Void> follow(@RequestBody @Valid  FollowRequest request,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        followService.follow(userDetails.getMember(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<MemberResponse>> getFollowingMembers(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<MemberResponse> response = followService.getFollowingMembers(userDetails.getMember());

        return ResponseEntity.ok().body(response);
    }
}
