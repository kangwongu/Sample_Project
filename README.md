# Sample_Project

배운 것을 적용해보며 체화하기 위한 샘플 프로젝트

## 프로젝트 환경

- Java 11
- Spring Boot 2.7.14
- Spring Data JPA
- QueryDSL
- Spring Security
- MySQL 8.0.32

## 적용한 것

- 외부 연동 추상화 (JPA, Java Mail Sender, PasswordEncoder)
- 서비스 추상화
- 도메인 모델, 영속성 모델 분리
- 트랜잭션 스크립트 패턴 방지 위한 도메인 활용
- 단위 (소형) 테스트
- 동시성 제어
- 스웨거

## 마주한 문제

- N+1 문제
  - Fetch join으로 해결
- 동시성 문제
  - 비관적 락 -> 낙관적 락 -> 테이블 분리 순으로 진행하며 해결