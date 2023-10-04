//package com.example.samplesns.post.utils;
//
//import com.example.samplesns.post.domain.Post;
//import com.example.samplesns.post.infrastructure.PostEntity;
//import org.jeasy.random.EasyRandom;
//
//import org.jeasy.random.EasyRandom;
//import org.jeasy.random.EasyRandomParameters;
//
//import java.lang.reflect.Field;
//import java.time.LocalDate;
//import java.util.function.Predicate;
//
//import static org.jeasy.random.FieldPredicates.*;
//
//public class PostFixtureFactory {
//
//    public static EasyRandom get(Long memberId, LocalDate firstDate, LocalDate lastDate) {
//        Predicate<Field> idPredicate = named("id")
//                .and(ofType(Long.class))
//                .and(inClass(Post.class));
//
//        Predicate<Field> memberIdPredicate = named("member_id")
//                .and(ofType(Long.class))
//                .and(inClass(Post.class));
//
//        EasyRandomParameters param = new EasyRandomParameters()
//                .excludeField(idPredicate)
//                .dateRange(firstDate, lastDate)
//                .randomize(memberIdPredicate, () -> memberId);
//        return new EasyRandom(param);
//    }
//
//}
