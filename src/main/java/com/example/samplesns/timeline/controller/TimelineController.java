package com.example.samplesns.timeline.controller;

import com.example.samplesns.common.security.userdetails.UserDetailsImpl;
import com.example.samplesns.post.dto.PostResponse;
import com.example.samplesns.timeline.service.TimelineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timeline")
public class TimelineController {
    private final TimelineService timelineService;

    @GetMapping("")
    @Operation(summary = "타임라인(홈) 조회", description = "자신이 팔로우한 회원들의 게시글 시간순 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PostResponse.class)))})
    })
    public ResponseEntity<Slice<PostResponse>> getTimelines(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            Pageable pageable) {
        Slice<PostResponse> response = timelineService.getTimelinesByPushModel(userDetails.getMember(), pageable);

        return ResponseEntity.ok().body(response);
    }
}
