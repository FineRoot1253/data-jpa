package com.jungeunhong.datajpa.member.command.domain.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;

import java.util.List;

public interface MemberCustomRepository {
    List<Member> findMemberCustom();
}
