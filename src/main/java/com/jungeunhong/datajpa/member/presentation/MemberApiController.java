package com.jungeunhong.datajpa.member.presentation;

import com.jungeunhong.datajpa.common.dto.Result;
import com.jungeunhong.datajpa.member.command.application.MemberCommandService;
import com.jungeunhong.datajpa.member.command.domain.dto.MemberDto;
import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import com.jungeunhong.datajpa.member.command.repository.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberCommandService memberCommandService;
    private final MemberCommandRepository memberCommandRepository;

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

    /**
     * 페이징 확장 기능 예시, ex 1)/api/v1/members?page=10&size=5&sort=id,desc&sort=age,desc
     * 디폴트 size는 20이다. 글로벌 설정은 properties에 해준다.
     * data.web.pageable.default-page-size로 설정한다.
     * ex 1) 의 예시를 보면, page로 가져올 페이지를 설정하며,  size로 직접 넣을 수 있다.
     * sort같은 경우, 여러 조건을 넣을 수도 있다.
     * page index는 기본적으로 0부터 사용한다. 만약 이게 좀 불편하다면 해결방법은 2가지이다.
     * 1) 커스텀 Pageable 사용하기, 2) 직접 여기서 PageRequest of 정적펙토리로 만들어서 조회하기
     * 3) spring.data.web.pageable.one-indexed-parameters를 true로 두기, 등등이 있지만 제일 나은건 그냥 0으로 쓰는게 깔끔하다.
     * 더 자세한 기능은 Spring Data JPA Official Documents를 참조할 것
     * @param pageable
     * @return
     */
    @GetMapping("/api/v1/members")
    public Result<Page<MemberDto>> list(Pageable pageable) {
        Page<Member> memberPage = memberCommandRepository.findAll(pageable);
        return new Result<>(0, memberPage.map(MemberDto::fromMemberEntity));
    }

    /**
     * 페이징 확장 기능 예시 1.1, 각 디폴트 옵션을 각 api endpoint 별로 줄려면 이렇게 어노테이션을 파라미터 레벨에 넣어주면된다.
     * "@Qualifier" 사용을 사용해 각각 다른 옵션을 넣어 다음과 같이 사용 가능하다
     * @param pageable
     * @return
     */
    @GetMapping("/api/v1.1/members")
    public Result<Page<MemberDto>> list1_1(@PageableDefault(size = 15) Pageable pageable) {
        Page<Member> memberPage = memberCommandRepository.findAll(pageable);
        return new Result<>(0, memberPage.map(MemberDto::fromMemberEntity));
    }

    /**
     * 페이징 확장 기능 예시 1.1, 각 디폴트 옵션을 각 api endpoint 별로 줄려면 이렇게 어노테이션을 파라미터 레벨에 넣어주면된다.
     * "@Qualifier" 사용을 사용해 각각 다른 옵션을 넣어 다음과 같이 사용 가능하다
     * @params memberPageable, orderPageable
     * @return
     */
    @GetMapping("/api/v1.2/members")
    public Result<Page<MemberDto>> list1_2(@Qualifier("member") Pageable memberPageable, @Qualifier("order") Pageable orderPageable) {
        Page<Member> memberPage = memberCommandRepository.findAll(memberPageable);
        return new Result<>(0, memberPage.map(MemberDto::fromMemberEntity));
    }

//    @PostConstruct
    public void init1() {
//        memberCommandService.saveMember(Member.createMember("hong", 12, null));
    }

    @PostConstruct
    public void init2(){
        StringBuilder builder = new StringBuilder("Hong_");
        for (int i = 0; i < 100; i++) {
            memberCommandService.saveMember(Member.createMember(builder.append(i).toString(),10 +i,null));
            builder.delete(5,builder.length());
        }
    }

}
