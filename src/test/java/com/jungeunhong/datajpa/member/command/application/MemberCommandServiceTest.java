package com.jungeunhong.datajpa.member.command.application;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberCommandServiceTest {

    @Autowired
    MemberCommandService memberCommandService;

    @Test
    void saveMember() {
        //given
        Member member = Member.createMember("Hong_TEST2",11,null);

        // when
        Long saveMemberId = memberCommandService.saveMember(member);
        Member saveMember = memberCommandService.findMemberById(saveMemberId);

        //than
        assertThat(saveMemberId).isEqualTo(saveMember.getId());

    }


}