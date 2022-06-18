package com.jungeunhong.datajpa.member.command.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCommandRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsername(String username);
}
