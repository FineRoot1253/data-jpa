package com.jungeunhong.datajpa.member.command.domain.dto;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public static MemberDto fromMemberEntity(Member member) {
        if (member.getTeam() != null) {
            return new MemberDto(member.getId(), member.getUsername(), member.getTeam().getTeamName());
        }
        return new MemberDto(member.getId(), member.getUsername(), null);
    }
}
