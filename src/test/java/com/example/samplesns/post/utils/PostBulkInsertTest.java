//package com.example.samplesns.post.utils;
//
//import com.example.samplesns.post.domain.Post;
//import com.example.samplesns.post.infrastructure.PostJpaRepository;
//import com.example.samplesns.post.service.port.PostRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.util.StopWatch;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//public class PostBulkInsertTest {
//
//    @Autowired
//    private PostRepository postRepository;
//
//
//    @Test
//    public void bulkInsert() {
//        var easyRandom = PostFixtureFactory.get(
//                14L,
//                LocalDate.of(2020, 1, 1),
//                LocalDate.of(2023,9,1)
//        );
//
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        int _1만 = 10000;
//        List<Post> posts = IntStream.range(0, _1만 * 50)
//                .mapToObj(i -> easyRandom.nextObject(Post.class))
//                .collect(Collectors.toList());
//
//        stopWatch.stop();
//        System.out.println("객체 생성 시간 = " + stopWatch.getTotalTimeSeconds());
//
//        StopWatch queryStopWatch = new StopWatch();
//        queryStopWatch.start();
//        postRepository.saveAllByBulk(14L, posts);
//
//        queryStopWatch.stop();
//        System.out.println("쿼리 실행 시간 = " + queryStopWatch.getTotalTimeSeconds());
//
//    }
//}
