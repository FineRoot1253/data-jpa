package com.jungeunhong.datajpa.common.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * 일반적으로 BaseEntity를 사용해야하는 테이블이 대부분이다.
 * 그러나 테이블 특성상 특정옵션은 필요가 없는 순간이 온다.
 * 그때는 이렇게 반드시 공통으로 쓰이는 옵션은 상위에 항상 두고
 * 그 친구만 때다가 붙이는 식으로 해결한다.
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(updatable=false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

}
