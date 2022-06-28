package com.jungeunhong.datajpa.member.query.domain.repository;

import com.jungeunhong.datajpa.member.command.domain.entity.Member;
import com.jungeunhong.datajpa.member.query.domain.dto.MemberProjection;
import com.jungeunhong.datajpa.member.query.domain.dto.UserNameOnlyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberQueryRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    // 정적 프로젝션
    // List<UserNameOnlyDto> findProjectionsByUsername(@Param("username") String username);

    // 동적 프로젝션
    <T> List<T> findProjectionsByUsername(@Param("username") String username, Class<T> type);

    @Query(value = "select * from member where member.username = ? ",nativeQuery = true)
    Member findByNativeQuery(String username);

    @Query(value = "select m.member_id as id, m.username, t.name as teamName from member m left join team t ",countQuery = "select count(*) from member",nativeQuery = true)
    Page<MemberProjection> findByProjection(Pageable pageable);

}
