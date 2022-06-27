package com.jungeunhong.datajpa.member.query.domain.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberQueryRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

}
