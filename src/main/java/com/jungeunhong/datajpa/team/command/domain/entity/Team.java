package com.jungeunhong.datajpa.team.command.domain.entity;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","teamName"})
public class Team {
    @Id
    @Column(name = "team_id")
    @GeneratedValue
    private Long id;
    private String teamName;

    @OneToMany
    @JoinColumn
    private List<Member> members= new ArrayList<>();
    private LocalDateTime createDate;

    public Team(String teamName, LocalDateTime createDate) {
        this.teamName=teamName;
        this.createDate=createDate;
    }

    public static Team createTeam(String teamName, LocalDateTime createDate){
        return new Team(teamName, createDate);
    }

}
