package com.jungeunhong.datajpa.member.command.application;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import com.jungeunhong.datajpa.member.command.repository.MemberCommandJpaRepository;
import com.jungeunhong.datajpa.member.command.repository.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCommandService {

//    private final MemberCommandJpaRepository memberCommandJpaRepository;
    private final MemberCommandRepository memberCommandRepository;

    @Transactional
    public Long saveMember(Member member){
        List<Member> findMembers = memberCommandRepository.findByUsername(member.getUsername());
        if(findMembers.size()>0){
            throw new IllegalStateException("이미 가입한 유저입니다.");
        }
        return memberCommandRepository.save(member).getId();
    }


    public Member findMemberById(Long memberId){
        return memberCommandRepository.findById(memberId).get();
    }

}
