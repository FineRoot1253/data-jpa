package com.jungeunhong.datajpa.member.query.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import com.jungeunhong.datajpa.member.query.domain.dto.MemberProjection;
import com.jungeunhong.datajpa.member.query.domain.dto.UserNameOnlyDto;
import com.jungeunhong.datajpa.member.query.domain.repository.MemberQueryRepository;
import com.jungeunhong.datajpa.member.query.domain.repository.MemberQuerySpecs;
import com.jungeunhong.datajpa.member.query.domain.repository.NestedClosedProjections;
import com.jungeunhong.datajpa.member.query.domain.repository.UserNameOnly;
import com.jungeunhong.datajpa.team.command.domain.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberQueryRepositoryTest {

    @Autowired
    MemberQueryRepository memberQueryRepository;
    @PersistenceContext
    EntityManager em;

    // criteria를 사용하기 때문에 결국은 QueryDSL을 추천하기 위한 예제이다.
    // criteria는 유지보수성이 떨어지는 단점이 크다. 다만 JPA에서 공식으로 지원하는 동적 쿼리를 위한 구현체이기 때문에
    // 확장성 자체는 좋긴하지만 유지보수성이 너무 현저하게 떨어진다. 이것 때문에 QueryDSL을 추천하는 것이다.
    @Test
    void specTest(){
        // given
        Team teamA = Team.createTeam("team_a");
        em.persist(teamA);

        Member member1 = Member.createMember("hong_1", 10, teamA);
        Member member2 = Member.createMember("hong_2", 12, teamA);
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();
        // when
        Specification<Member> specification = MemberQuerySpecs.withUsername("hong_1").and(MemberQuerySpecs.withTeamName("team_a"));
        List<Member> members = memberQueryRepository.findAll(specification);

        // then
        assertThat(members.size()).isEqualTo(1);

    }

    // QueryByExample은 도메인 객체를 그대로 검색 조건에 사용하는 방식이다.
    // 다만 left outer join이 지원 되지 않아 제한적이라 결국은 QueryDSL을 사용해야한다.
    // outer join이 하나라도 넣어야하는 상황이오면 처음부터 다시 jpql을 쓰든가 해서 바꿔야한다.
    // 이게 결국은 답이 없어서 QueryDSL을 계속 추천하는 것이다.
    @Test
    void queryByExampleTest(){
        // given
        Team teamA = Team.createTeam("team_a");
        em.persist(teamA);

        Member member1 = Member.createMember("hong_1", 10, teamA);
        Member member2 = Member.createMember("hong_2", 12, teamA);
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        // when
        //Probe
        Member exMember = Member.createMember("hong_1", 10, teamA);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("age");

        Example<Member> memberExample = Example.of(exMember,matcher);
        List<Member> members = memberQueryRepository.findAll(memberExample);

        // then
        assertThat(members.get(0).getUsername()).isEqualTo("hong_1");

    }

    @Test
    void projectionsTest(){
        // given
        Team teamA = Team.createTeam("team_a");
        em.persist(teamA);

        Member member1 = Member.createMember("hong_1", 10, teamA);
        Member member2 = Member.createMember("hong_2", 12, teamA);
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        // when
        //Probe
        List<NestedClosedProjections> hong1 = memberQueryRepository.findProjectionsByUsername("hong_1", NestedClosedProjections.class);

        // then
        assertThat(hong1.get(0).getUsername()).isEqualTo("hong_1");

    }

    @Test
    void nativeQueryTest(){
        // given
        Team teamA = Team.createTeam("team_a");
        em.persist(teamA);

        Member member1 = Member.createMember("hong_1", 10, teamA);
        Member member2 = Member.createMember("hong_2", 12, teamA);
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        // when
        //Probe
        Member hong1 = memberQueryRepository.findByNativeQuery("hong_1");
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<MemberProjection> byProjection = memberQueryRepository.findByProjection(pageRequest);

        // then
        assertThat(hong1.getUsername()).isEqualTo("hong_1");
        


    }
}