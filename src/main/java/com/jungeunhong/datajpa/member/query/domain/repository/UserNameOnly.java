package com.jungeunhong.datajpa.member.query.domain.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UserNameOnly {
    // 이 value는 open projection이라고 한다. 모든 엔티티필드를 다긁어 오기 때문에 별 사용 의미가 없다.
//    @Value("#{target.username + ' ' +  target.age}")
    String getUsername();
}
