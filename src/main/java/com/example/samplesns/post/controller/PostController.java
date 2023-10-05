package com.example.samplesns.post.controller;

import com.example.samplesns.common.exception.response.ExceptionResponse;
import com.example.samplesns.common.security.userdetails.UserDetailsImpl;
import com.example.samplesns.post.dto.*;
import com.example.samplesns.post.service.PostService;
import com.example.samplesns.post.service.TimelineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Post", description = "Post API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final TimelineService timelineService;

    @PostMapping("")
    @Operation(summary = "게시글 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류",
                    content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
    })
    public ResponseEntity<Void> createPost(@RequestBody @Valid PostCreateRequest request,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(userDetails.getMember(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/daily-list")
    @Operation(summary = "일자별 게시글 조회", description = "지정된 일자에 회원별 작성한 게시글의 개수 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = DailyPostResponse.class)))})
    })
    public ResponseEntity<Slice<DailyPostResponse>> getDailyPosts(@ModelAttribute @Valid DailyPostRequest request,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  Pageable pageable) {
        Slice<DailyPostResponse> response = postService.getDailyPosts(userDetails.getMember(), request, pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/my-list")
    @Operation(summary = "자신이 작성한 게시글 목록 조회", description = "현재 로그인한 회원이 작성한 게시글 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PostResponse.class)))})
    })
    public ResponseEntity<Slice<PostResponse>> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          Pageable pageable) {
        Slice<PostResponse> response = postService.getMyPosts(userDetails.getMember(), pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    @Operation(summary = "다른 회원이 작성한 게시글 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PostResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "해당 회원이 존재하지 않음",
                    content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
    })
    public ResponseEntity<Slice<PostResponse>> getPosts(@RequestParam @Parameter(description = "조회하려는 회원의 이메일", required = true) String email,
                                                        Pageable pageable) {
        Slice<PostResponse> response = postService.getPosts(email, pageable);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{postId}")
    @Operation(summary = "게시글 수정", description = "자신의 게시글만 수정할 수 있다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 상태가 아님",
                    content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "403", description = "본인의 게시글이 아닌 게시글을 수정 시도함",
                    content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음",
                    content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
    })
    public ResponseEntity<Void> updatePosts(@PathVariable long postId,
                                            @RequestBody @Valid PostUpdateRequest request,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.updatePost(postId, userDetails.getMember(), request);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{postId}/delete")
    @Operation(summary = "게시글 삭제", description = "자신의 게시글만 삭제할 수 있다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 상태가 아님",
                    content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "403", description = "본인의 게시글이 아닌 게시글을 삭제 시도함",
                    content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음",
                    content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
    })
    public ResponseEntity<Void> deletePosts(@PathVariable long postId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getMember());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    @Operation(summary = "타임라인(홈) 조회", description = "자신이 팔로우한 회원들의 게시글 시간순 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PostResponse.class)))})
    })
    public ResponseEntity<Slice<PostResponse>> getTimelines(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          Pageable pageable) {
        Slice<PostResponse> response = timelineService.getTimelines(userDetails.getMember(), pageable);

        return ResponseEntity.ok().body(response);
    }
}
