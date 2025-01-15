package com.jiinfotech.restomanager.db.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByTableId(long tableId);
}

