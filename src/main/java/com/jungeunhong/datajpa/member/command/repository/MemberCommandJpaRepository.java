package com.jungeunhong.datajpa.member.command.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberCommandJpaRepository {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return em.find(Member.class, member.getId()).getId();
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m " +
                "from Member m " +
                "where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

}
