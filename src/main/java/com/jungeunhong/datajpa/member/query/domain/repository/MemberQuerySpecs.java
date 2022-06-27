package com.jungeunhong.datajpa.member.query.domain.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import com.jungeunhong.datajpa.team.command.domain.entity.Team;
import org.springframework.data.jpa.domain.Specification;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class MemberQuerySpecs {
    public static Specification<Member> withTeamName(final String teamName){
        return (root, query, criteriaBuilder) -> {
            if(StringUtils.isEmpty(teamName)){
                return null;
            }
            Join<Member, Team> team = root.join("team", JoinType.INNER);// 회원과 조인
            return criteriaBuilder.equal(team.get("teamName"),teamName);
        };
    }

    public static Specification<Member> withUsername(final String username){
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("username"),username);
    }
}
