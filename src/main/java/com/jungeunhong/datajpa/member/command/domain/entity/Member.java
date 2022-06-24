package com.jungeunhong.datajpa.member.command.domain.entity;

import com.jungeunhong.datajpa.team.command.domain.entity.Team;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"})
public class Member {

    @Id
    @Column(name = "member_id") @GeneratedValue
    private Long id;

    private String username;

    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username, int age){
        this.username=username;
        this.age=age;
    }

    public static Member createMember(String username, int age, Team team){
        Member member = new Member(username, age);
        if(team != null){
            member.changeTeam(team);
        }
        return member;
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

    public void changeAge(int age){
        this.age=age;
    }

}
