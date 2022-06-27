package com.jungeunhong.datajpa.item.repository;

import com.jungeunhong.datajpa.item.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
