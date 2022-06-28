package com.jungeunhong.datajpa.member.query.domain.repository;

// 중첩구조에 대해 closed projection을 구현해도
// 실제 쿼리는 연관 테이블 컬럼 전부 다 긁어와버린다.
// 즉, 연관된 엔티티는 closed하게 최적화 되지가 않는다.
public interface NestedClosedProjections {
    String getUsername();
    TeamInfo getTeam();

    interface TeamInfo {
        String getTeamName();
    }
}
