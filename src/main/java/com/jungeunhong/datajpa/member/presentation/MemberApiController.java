package com.jungeunhong.datajpa.member.presentation;

import com.jungeunhong.datajpa.common.dto.Result;
import com.jungeunhong.datajpa.member.command.application.MemberCommandService;
import com.jungeunhong.datajpa.member.command.domain.dto.MemberDto;
import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberCommandService memberCommandService;

    @PostMapping("/api/v1/members")
    public Result<Long> registerMember(
            @RequestParam("username") String username,
            @RequestParam("age") int age) {
        Member member = Member.createMember(username, age, null);
        Long savedMemberId = memberCommandService.saveMember(member);

        return new Result<>(1, savedMemberId);
    }

    @GetMapping("/api/v1/members/{id}")
    public Result<MemberDto> findMember(@PathVariable("id") Long id) {
        Member memberById = memberCommandService.findMemberById(id);
        return new Result<>(0, MemberDto.fromMemberEntity(memberById));
    }

    @GetMapping("/api/v1.1/members/{id}")
    public Result<MemberDto> findMember2(@PathVariable("id") Member member) {
        return new Result<>(0, MemberDto.fromMemberEntity(member));
    }

    @PostConstruct
    public void init() {
        memberCommandService.saveMember(Member.createMember("hong", 12, null));
    }

}
