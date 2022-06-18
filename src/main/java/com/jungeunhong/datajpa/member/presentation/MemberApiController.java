package com.jungeunhong.datajpa.member.presentation;

import com.jungeunhong.datajpa.common.dto.Result;
import com.jungeunhong.datajpa.member.command.application.MemberCommandService;
import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberCommandService memberCommandService;

    @PostMapping("/api/v1/members")
    public Result<Long> registerMember(
            @RequestParam("username") String username,
            @RequestParam("age") int age) {
        Member member = Member.createMember(username,age,null);
        Long savedMemberId = memberCommandService.saveMember(member);

        return new Result<>(1,savedMemberId);
    }

}
