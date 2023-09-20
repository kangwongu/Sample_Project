package com.example.samplesns.post.controller;

import com.example.samplesns.common.exception.response.ExceptionResponse;
import com.example.samplesns.common.security.userdetails.UserDetailsImpl;
import com.example.samplesns.post.dto.PostCreateRequest;
import com.example.samplesns.post.service.PostService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Post", description = "Post API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

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

}
