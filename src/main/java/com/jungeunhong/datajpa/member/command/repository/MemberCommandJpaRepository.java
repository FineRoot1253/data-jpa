package com.jungeunhong.datajpa.member.command.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
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

    // 전통적인 페이징 계산 방법
    // totalPage = totalCount / size
    // 마지막 페이지 인지판단 로직, 최초 페이지 판단로직 등에 사용된다.

    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m " +
                        "from Member m " +
                        "where m.age = :age " +
                        "order by m.username desc", Member.class)
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long totalCount(int age) {
        return em.createQuery("select count(m) " +
                        "from Member m " +
                        "where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

}
