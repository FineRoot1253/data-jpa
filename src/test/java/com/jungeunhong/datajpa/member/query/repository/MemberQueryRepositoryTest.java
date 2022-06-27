package com.jungeunhong.datajpa.member.query.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import com.jungeunhong.datajpa.member.query.domain.repository.MemberQueryRepository;
import com.jungeunhong.datajpa.member.query.domain.repository.MemberQuerySpecs;
import com.jungeunhong.datajpa.team.command.domain.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

        // than
        assertThat(members.size()).isEqualTo(1);

    }
}