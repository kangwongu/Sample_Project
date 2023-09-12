package com.example.samplesns.follow.controller;

import com.example.samplesns.common.security.userdetails.UserDetailsImpl;
import com.example.samplesns.follow.dto.FollowRequest;
import com.example.samplesns.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("")
    public ResponseEntity<Void> follow(@RequestBody FollowRequest request,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        followService.follow(userDetails.getMember(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
