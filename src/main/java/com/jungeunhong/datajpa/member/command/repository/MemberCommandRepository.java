package com.jungeunhong.datajpa.member.command.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    Page<Member> findByAge(int age, Pageable pageable);
    Slice<Member> findToSliceByAge(int age, Pageable pageable);

    // 이런 left join 같은 1:1 조인의 경우,
    // countQuery는 조인을 할 필요가 없는데 불구하고 조인을 넣는다.
    // 이런 복잡해지는 테이블의 케이스의 경우 이런식으로 직접 카운팅 기준이 되는 로우의 카운트 쿼리를 직접 짜서 넣으면 되고
    // 만약 많이 복잡해서 기존 PageRequest의 sort 기준이 복잡해진다면,(예를 들면 최신순 + 가나다순 복합 오더링)
    // 이 Query에 직접 넣으면 된다.
    // 결국 복잡한 쿼리는 이 @Query를 써서 JPQL을 넣거나 QueryDSL을 쓰면 되므로
    // 일단 적용을 하고 복잡하면 이 방식들을 쓰면 된다.
    // 이 방식이 훨씬 높은 생산성을 가져온다.
    @Query(value = "select m from Member m left join m.team",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAgeWithCustomCount(int age, Pageable pageable);

    // 벌크 연산 이후에는 영속성 컨텍스트를 비워주는게 좋다.
    // 이 연산 이후 조회하는 연산이 추가될 경우가 있기 때문이다.
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age +1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // 특정 엔티티와 연관된 엔티티를 들고올때는 패치조인이 필수다.
    // N + 1 문제를 방지하기 위함인데
    // JPQL를 수동으로 적지 않고 바로바로 연관 관계인 엔티티가 필요하겠다 싶으면
    // @EntityGraph를 바로 넣어서 패치조인을 적용해줄수 있다.
    // 솔직히 수동으로 일일히 적는 것보단 저 어노테이션을 패치조인이 필요한 순간에만 넣어주는 것이 생산성에 훨씬 도움된다.
    // 다만 많이 복잡한 쿼리일경우에는 JPQL을 수동으로 적는게 훨씬 낫다

    // 패치조인 JPQL only
    @Query("select m from Member m join fetch m.team t")
    List<Member> findMemberAllFetchJoin();

    // 패치조인 findAll + EntityGraph
    @Override
    @EntityGraph(attributePaths = "team")
    List<Member> findAll();

    // 패치조인 JPQL + EntityGraph
    @EntityGraph(attributePaths = "team")
    @Query("select m from Member m")
    List<Member> findMemberAll();

    // 패치조인 네임드 쿼리 + EntityGraph
    @EntityGraph(attributePaths = "team")
    List<Member> findMemberAndTeamByUsername(String username);
}
