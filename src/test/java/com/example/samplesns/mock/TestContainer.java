package com.example.samplesns.mock;

import com.example.samplesns.common.service.port.JwtManager;
import com.example.samplesns.follow.controller.port.FollowService;
import com.example.samplesns.follow.service.FollowServiceImpl;
import com.example.samplesns.follow.service.port.FollowRepository;
import com.example.samplesns.member.controller.port.MemberService;
import com.example.samplesns.member.service.CertificationService;
import com.example.samplesns.member.service.MemberServiceImpl;
import com.example.samplesns.member.service.PasswordService;
import com.example.samplesns.member.service.port.MailSender;
import com.example.samplesns.member.service.port.MemberRepository;
import com.example.samplesns.member.service.port.MyPasswordEncoder;
import com.example.samplesns.post.controller.port.PostLikeService;
import com.example.samplesns.post.controller.port.PostService;
import com.example.samplesns.post.service.PostLikeServiceImpl;
import com.example.samplesns.post.service.PostServiceImpl;
import com.example.samplesns.post.service.port.PostLikeRepository;
import com.example.samplesns.post.service.port.PostRepository;
import com.example.samplesns.timeline.controller.port.TimelineService;
import com.example.samplesns.timeline.service.TimelineServiceImpl;
import com.example.samplesns.timeline.service.port.TimelineRepository;
import lombok.Builder;

public class TestContainer {

    public MemberService memberService;
    public PasswordService passwordService;
    public CertificationService certificationService;
    public MemberRepository memberRepository;
    public MailSender mailSender;
    public FollowService followService;
    public FollowRepository followRepository;
    public PostService postService;
    public PostRepository postRepository;
    public TimelineService timelineService;
    public TimelineRepository timelineRepository;
    public PostLikeService postLikeService;
    public PostLikeRepository postLikeRepository;


    @Builder
    public TestContainer(MyPasswordEncoder passwordEncoder, JwtManager jwtManager) {
        this.mailSender = new FakeMailSender();
        this.memberRepository = new FakeMemberRepository();
        this.certificationService = new CertificationService(mailSender);
        this.passwordService = new PasswordService(passwordEncoder);
        this.followRepository = new FakeFollowRepository();
        this.postRepository = new FakePostRepository();
        this.timelineRepository = new FakeTimelineRepository();
        this.postLikeRepository = new FakePostLikeRepository();

        this.memberService = new MemberServiceImpl(passwordService, certificationService, memberRepository, jwtManager);
        this.followService = new FollowServiceImpl(memberRepository, followRepository);
        this.timelineService = new TimelineServiceImpl(postRepository, memberRepository, timelineRepository, postLikeRepository);
        this.postService = new PostServiceImpl(timelineService, postRepository, memberRepository, followRepository, postLikeRepository);
        this.postLikeService = new PostLikeServiceImpl(postRepository, postLikeRepository);
    }

}
