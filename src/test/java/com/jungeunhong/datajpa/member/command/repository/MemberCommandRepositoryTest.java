package com.jungeunhong.datajpa.member.command.repository;

import com.jungeunhong.datajpa.member.command.domain.dto.MemberDto;
import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberCommandRepositoryTest {

    @Autowired
    MemberCommandRepository memberCommandRepository;

    // DATA JPA의 페이징은 0부터 시작한다!!!
    @Test
    void paging(){
        //given
        int age = 10;

        for (int i = 0; i < 10; i++) {
            memberCommandRepository.save(new Member("member"+i,10));
        }

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> memberPage = memberCommandRepository.findByAge(age, pageRequest);
        Slice<Member> memberSlice = memberCommandRepository.findToSliceByAge(age, pageRequest);
        Page<Member> memberPage2 = memberCommandRepository.findByAgeWithCustomCount(age, pageRequest);

        //then
        List<Member> content = memberPage.getContent();
        long totalElements = memberPage.getTotalElements();

        List<Member> content2 = memberPage2.getContent();
        long totalElements2 = memberPage2.getTotalElements();

        for (Member member : content) {
            System.out.println("member = " + member);
        }

        assertThat(content.size()).isEqualTo(3);
        assertThat(memberPage.getTotalElements()).isEqualTo(10);
        assertThat(memberPage.getNumber()).isEqualTo(0);
        assertThat(memberPage.getTotalPages()).isEqualTo(4);
        assertThat(memberPage.isFirst()).isTrue();
        assertThat(memberPage.hasNext()).isTrue();

        assertThat(content.size()).isEqualTo(3);
        assertThat(memberSlice.getNumber()).isEqualTo(0);
        assertThat(memberSlice.isFirst()).isTrue();
        assertThat(memberSlice.hasNext()).isTrue();

    }

    // 당연하지만 늘 반환은 DTO에 담고 공통 Result 모델에 담아 반환해야한다.
    // 이때, 페이지의 경우엔 map을 쓰면 간단하게 해결가능하다.
    @Test
    void pagingAndTransToDTO(){
        //given
        int age = 10;

        for (int i = 0; i < 10; i++) {
            memberCommandRepository.save(new Member("member"+i,10));
        }

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> memberPage = memberCommandRepository.findByAge(age, pageRequest);

        Page<MemberDto> memberDtos = memberPage.map(member ->
                new MemberDto(member.getId(), member.getUsername(), null));
        //then
        List<Member> content = memberPage.getContent();
        long totalElements = memberPage.getTotalElements();

        for (Member member : content) {
            System.out.println("member = " + member);
        }

        assertThat(content.size()).isEqualTo(3);
        assertThat(memberPage.getTotalElements()).isEqualTo(10);
        assertThat(memberPage.getNumber()).isEqualTo(0);
        assertThat(memberPage.getTotalPages()).isEqualTo(4);
        assertThat(memberPage.isFirst()).isTrue();
        assertThat(memberPage.hasNext()).isTrue();

        assertThat(memberDtos.getTotalElements()).isEqualTo(10);
        assertThat(memberDtos.getNumber()).isEqualTo(0);
        assertThat(memberDtos.getTotalPages()).isEqualTo(4);
        assertThat(memberDtos.isFirst()).isTrue();
        assertThat(memberDtos.hasNext()).isTrue();


    }




}