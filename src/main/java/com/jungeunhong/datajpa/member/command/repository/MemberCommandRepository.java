package com.jungeunhong.datajpa.member.command.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberCommandRepository extends JpaRepository<Member, Long> {

    // 메소드 쿼리 생성 기본 방법
    // 메소드 쿼리 기본 문법
    // find...By~~ ...에는 아무거나 적어도 괜찮다 다만 몇몇 쿼리 키워드를 주의해야한다.
    // 이건 공홈가서 공부하면 된다.
    // 메소드 시그니쳐중 메소드 이름에 관련 쿼리, 관계연산, 조건등을 넣을 수 있다.
    // 이 방식은 정말 간단한 쿼리에만 사용해야한다.
    List<Member> findByUsernameAndAgeGreaterThan(String name, int age);

    // 메소드 쿼리 생성 기본 방법중 하나
    // 저런식의 관례상의 키워드를 넣으면 페이징도 처리해준다.
    // 난 걍 다 맘에 안든다.
    // 그나마 @Query를 통한 쿼리 삽입이 낫다고 생각한다.
    List<Member> findTop3HelloBy();

    // 네임드쿼리 사용중 @Query를 생략한 방식
    // 원래는 항상 엔티티의 프로퍼티를 먼저 체크를 하기 때문에 가능하다.
    List<Member> findByUsername(String username);

    // @Query 방식
    // 이게 제일 괜찮게 생각하는 방식이다.
    @Query("select  m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // @Query 방식
    // 이게 제일 괜찮게 생각하는 방식이다.
    @Query("select  m.username from Member m")
    List<Member> findUsernameList();

    // @Query에 DTO로 긁어오는 방식
    // 'new' 연산자를 써야하는 방식이라 매우 귀찮은 방식이다.
    // DTO로 긁는다 = queryDSL임을 명심하자
    @Query("select new com.jungeunhong.datajpa.member.command.domain.dto.MemberDto(m.id,m.username,t.teamName) from Member m join m.team t")
    List<Member> findMemberDto();

    // @Query에 DTO로 긁어오는 방식
    // @Param에 컬렉션도 들어간다. 아래 예시는 컬렉션을 in절에 넣는 예시다.
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    // List 리턴 타입은 무조건 널이 아니다.
    // 안심하고 널체크 안해도 된다.
    List<Member> findListByUsername(String username);
    // 단건 조회는 널 체크를 해야한다!!!
    // 옵셔널을 쓰면 또 괜찮다
    // 다양한 리턴 타입이 존재하는데 이것도 공홈가면 잘 알려준다.
    Member findMemberByUsername(String username);
    Optional<Member> findMemberOptionalByUsername(String username);

}
