package com.jungeunhong.datajpa.member.command.domain.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberCommandRepositoryImpl implements MemberCustomRepository{

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m").getResultList();
    }
}
